package core

import akka.actor.{Actor, ActorRef, Props, ActorSystem}
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Promise, Future}
import scala.util.Try
import scala.collection.mutable.{Map, HashMap}

class CrawlerActor(interpreter: Interpreter) extends Actor {
  def receive = {
    case Run(crawlerVariable, promise) => {
      println("gotta run")
      try {
        val res = interpreter.run(crawlerVariable)
        promise.success(res)
      } catch {
        case e: Exception => promise.failure(e)
      }
    }
    case _ => println("unknown message")
  }
}

class GenCrawler(crawlerName : String) {
  val system = ActorSystem(crawlerName)

  var imidiateActorList : List[(ActorRef, CrawlerVariable, Promise[CrawlerVariable])] = List[(ActorRef, CrawlerVariable, Promise[CrawlerVariable])]()

  def addInterpreter(interpreter: Interpreter, v : CrawlerVariable) : FutureWrapper[CrawlerVariable] = {
    // MAYBE: check if list already contains element
    val prom = Promise[CrawlerVariable]
    val curActor = system.actorOf(Props(new CrawlerActor(interpreter))/*, name = interpreter.toString*/)
    imidiateActorList = imidiateActorList.::((curActor, v, prom))
    new FutureWrapper[CrawlerVariable](prom.future)
  }

  def addInterpreter(interpreter: Interpreter, v : FutureWrapper[CrawlerVariable]) : FutureWrapper[CrawlerVariable] = {
    // MAYBE: check if list already contains element
    val prom = Promise[CrawlerVariable]
    val curActor = system.actorOf(Props(new CrawlerActor(interpreter))/*, name = interpreter.toString*/)
    v.onSuccess({
      case v: CrawlerVariable => curActor ! Run(v, prom)
    })
    v.onFailure({
      case t:Throwable => prom.failure(t)
    })
    new FutureWrapper[CrawlerVariable](prom.future)
  }

  val defaultTimeout = new Timeout(5 seconds)
  def run(implicit timeout: Timeout = defaultTimeout) =  {
    for(element <- imidiateActorList) {
      element._1 ! Run(element._2, element._3)
    }
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
}

object GenCrawler {
  def apply(crawlerName : String) = new GenCrawler(crawlerName)
}