package org.lmu.GenCrawler.core

import akka.actor.ActorRef

import scala.concurrent.Promise

/**
  * A parent abstract class to provide Messages for Akka actors.
  */
protected sealed abstract class InterpreterMessages

/**
  * Describes an Akka actor message to run an Interpreter with a given object and callback a given Promise.
  *
  * @param crawlerVariable The input for the Interpreter.
  * @param promise The promise to respond to.
  */
protected case class Run(crawlerVariable: CrawlerVariable, promise: Promise[CrawlerVariable])
  extends InterpreterMessages

/**
  * Describes an Akka actor message to forward a Run message to an Interpreter.
  *
  * @param actorRefToForwardTo The actor to send the Run message to.
  * @param crawlerVariable The input for the Interpreter.
  * @param promise The promise to respond to.
  */
protected case class Forward(actorRefToForwardTo: ActorRef, crawlerVariable: CrawlerVariable,
                             promise: Promise[CrawlerVariable]) extends InterpreterMessages

/**
  * Describes an Akka actor message that an InterpreterActor finished processing.
  */
protected case class FinishedInterpretation() extends InterpreterMessages
