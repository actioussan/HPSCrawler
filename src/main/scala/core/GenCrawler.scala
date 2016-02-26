package core

import akka.actor.{Actor, ActorRef, Props, ActorSystem}
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Promise, Future}
import scala.util.Try
import scala.collection.mutable.{Map, HashMap}

class CrawlerActor(interpreter: Interpreter) extends Actor {
  def receive = {
    case Run(crawlerVariable, promise) =>
      try {
        val res = interpreter.run(crawlerVariable)
        promise.success(res)
      } catch {
        case e: Exception => promise.failure(e)
      } finally {
        sender ! FinishedInterpretation()
      }
    case _ => println("unknown message")
  }
}

class GenCrawler(crawlerName : String) {
  val system = ActorSystem(crawlerName)
  val masterActorRef = system.actorOf(Props(new MasterActor), name = "MasterActor")
  var openMessages: Int = 0
  var actorNameCount: Int = 0

  var immediateActorList : List[(ActorRef, CrawlerVariable, Promise[CrawlerVariable])] = List[(ActorRef, CrawlerVariable, Promise[CrawlerVariable])]()

  def addInterpreter(interpreter: Interpreter, v : CrawlerVariable) : FutureWrapper[CrawlerVariable] = {
    // MAYBE: check if list already contains element
    val prom = Promise[CrawlerVariable]
    actorNameCount += 1
    val curActor = system.actorOf(Props(new CrawlerActor(interpreter)), name = s"CrawlerActor$actorNameCount")
    immediateActorList = immediateActorList.::((curActor, v, prom))
    new FutureWrapper[CrawlerVariable](prom.future)
  }

  def addInterpreter(interpreter: Interpreter, v : FutureWrapper[CrawlerVariable]) : FutureWrapper[CrawlerVariable] = {
    // MAYBE: check if list already contains element
    val prom = Promise[CrawlerVariable]
    actorNameCount += 1
    val curActor = system.actorOf(Props(new CrawlerActor(interpreter)), name = s"CrawlerActor$actorNameCount")
    v.onSuccess({
      case v: CrawlerVariable =>
        openMessages += 1
        masterActorRef ! Forward(curActor, v, prom)
    })
    v.onFailure({
      case t:Throwable => prom.failure(t)
    })
    new FutureWrapper[CrawlerVariable](prom.future)
  }

  val defaultTimeout = new Timeout(5.seconds)
  def run(implicit timeout: Timeout = defaultTimeout) =  {
    for(element <- immediateActorList) {
      openMessages += 1
      masterActorRef ! Forward(element._1, element._2, element._3)
    }
  }

  def stop() = {
    system.terminate()
  }

  def await(time: Int = 1000) = {
    //Thread sleep 1
    while(openMessages > 0) {
      Thread sleep time
    }
    stop()
  }

  class FutureWrapper[+T](future: Future[T]) {
    def onComplete[U](@deprecatedName('func) f: Try[T] => U): Unit = {
      future.onComplete(f)(system.dispatcher)
    }
    def onSuccess[U](pf: PartialFunction[T, U]): Unit = {
      future.onSuccess(pf)(system.dispatcher)
    }
    def onFailure[U](@deprecatedName('callback) pf: PartialFunction[Throwable, U]): Unit = {
      future.onFailure(pf)(system.dispatcher)
    }
  }

  class MasterActor extends Actor {
    def receive = {
      case Forward(ref, crawlerVariable, promise) => ref ! Run(crawlerVariable, promise)
      case FinishedInterpretation() => openMessages -= 1
      case _ => println("unknown message")
    }
  }
}

object GenCrawler {
  def apply(crawlerName : String) = new GenCrawler(crawlerName)
}