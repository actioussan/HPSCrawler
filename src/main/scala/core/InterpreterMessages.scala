package core

import scala.concurrent.Promise

sealed abstract class InterpreterMessages

case class Run(crawlerVariable: CrawlerVariable, promise: Promise[CrawlerVariable]) extends InterpreterMessages
