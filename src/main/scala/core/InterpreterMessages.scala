package core

import akka.actor.ActorRef

import scala.concurrent.Promise

sealed abstract class InterpreterMessages

case class Run(crawlerVariable: CrawlerVariable, promise: Promise[CrawlerVariable]) extends InterpreterMessages
case class Forward(ref: ActorRef, crawlerVariable: CrawlerVariable, promise: Promise[CrawlerVariable]) extends InterpreterMessages
case class FinishedInterpretation() extends InterpreterMessages
