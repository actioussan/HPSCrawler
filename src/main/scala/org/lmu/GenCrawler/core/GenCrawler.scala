package org.lmu.GenCrawler.core

import akka.actor.{Actor, ActorRef, Props, ActorSystem}
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.{Promise, Future}
import scala.util.Try

/**
  * Wrapper class for Interpreters to provide Actors informing Promise objects upon Interpreter action.
  *
  * @param interpreter The Interpreter which should be called by the actor.
  */
protected class CrawlerActor(interpreter: Interpreter) extends Actor {
  /**
    * Akka's actor require receive function. Here the Interpreter is called when a Run message is received. Also a
    * promise Object is issued.
    */
  def receive = {
    case Run(crawlerVariable, promise) =>
      try {
        val output = interpreter.run(crawlerVariable)
        promise.success(output)
      } catch {
        case reason: Exception => promise.failure(reason)
      } finally {
        sender ! FinishedInterpretation()
      }
    case _ => println("unknown message")
  }
}

/**
  * The general class for handling a crawling process with multiple Interpreters and their dependencies.
  *
  * @param crawlerName A name to give Akka's concurrency system.
  */
class GenCrawler(crawlerName : String) {
  val system = ActorSystem(crawlerName)
  val masterActorRef = system.actorOf(Props(new MasterActor), name = "MasterActor")
  var openMessages: Int = 0
  var actorNameCount: Int = 0

  var immediateActorRefList: List[(ActorRef, CrawlerVariable, Promise[CrawlerVariable])] =
    List[(ActorRef, CrawlerVariable, Promise[CrawlerVariable])]()

  /**
    * Adds an interpreter to the current crawling process with a static input variable.
    *
    * @param interpreter The Interpreter to be called.
    * @param initialInput A variable which should be provided as input to the given interpreter during crawling process.
    * @return A Future object to provide callbacks upon Interpreter call during crawling process.
    */
  def addInterpreter(interpreter: Interpreter, initialInput: CrawlerVariable): FutureWrapper[CrawlerVariable] = {
    val promise = Promise[CrawlerVariable]
    actorNameCount += 1
    val newActorRef = system.actorOf(Props(new CrawlerActor(interpreter)), name = s"CrawlerActor$actorNameCount")
    immediateActorRefList = immediateActorRefList.::((newActorRef, initialInput, promise))
    new FutureWrapper[CrawlerVariable](promise.future)
  }

  /**
    * Adds an interpreter to the current crawling process depending on another interpreter.
    *
    * @param interpreter The Interpreter to be called.
    * @param interpreterOutputFuture A Future object whose output should be provided as input to the given interpreter
    *                                during crawling process.
    * @return A Future object to provide callbacks upon Interpreter call during crawling process.
    */
  def addInterpreter(interpreter: Interpreter, interpreterOutputFuture: FutureWrapper[CrawlerVariable]):
  FutureWrapper[CrawlerVariable] = {
    val promise = Promise[CrawlerVariable]
    actorNameCount += 1
    val newActorRef = system.actorOf(Props(new CrawlerActor(interpreter)), name = s"CrawlerActor$actorNameCount")
    interpreterOutputFuture.onSuccess({
      case othersInterpreterOutput: CrawlerVariable =>
        openMessages += 1
        masterActorRef ! Forward(newActorRef, othersInterpreterOutput, promise)
    })
    interpreterOutputFuture.onFailure({
      case reason: Throwable => promise.failure(reason)
    })
    new FutureWrapper[CrawlerVariable](promise.future)
  }

  val defaultTimeout = new Timeout(5.seconds)

  /**
    * Starts the crawling process described with this GenCrawler object.
    * @param timeout The timeout for Akka's actor messaging.
    */
  def run(implicit timeout: Timeout = defaultTimeout) =  {
    for(element <- immediateActorRefList) {
      openMessages += 1
      masterActorRef ! Forward(element._1, element._2, element._3)
    }
  }

  /**
    * Stops the current crawling process in whichever state it is.
    */
  def stop() = {
    system.terminate()
  }

  /**
    * Let the current thread wait until the crawling process has finished.
    * WARNING: If there goes something wrong during interpretation this can cause a deadlock.
    *
    * @param time The time to wait between two checks whether the process has finished in Milliseconds.
    */
  def await(time: Int = 1000) = {
    while(openMessages > 0) {
      Thread sleep time
    }
    stop()
  }

  /**
    * A wrapper class for Future objects. Provides callbacks like a Future object would. Just the context of these
    * objects is given the GenCrawlers system crawler.
    *
    * @param future The future object to respond to.
    */
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

  /**
    * An Actor class to receive sender information upon finished CrawlerActor calls.
    */
  private class MasterActor extends Actor {
    def receive = {
      case Forward(ref, crawlerVariable, promise) => ref ! Run(crawlerVariable, promise)
      case FinishedInterpretation() => openMessages -= 1
      case _ => println("unknown message")
    }
  }
}

/**
  * Companion object for short object creation.
  */
object GenCrawler {
  def apply(crawlerName: String) = new GenCrawler(crawlerName)
}