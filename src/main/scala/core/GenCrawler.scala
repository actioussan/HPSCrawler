package core

import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging

class GenCrawler() {
  var interpreters : List[(Interpreter, CrawlerVariable)] = List[(Interpreter, CrawlerVariable)]()

  def addInterpreter(in: Interpreter, v : CrawlerVariable) = {
    // MAYBE: check if list already contains element
    interpreters = interpreters.::((in, v))
  }

  def run() : List[CrawlerVariable] = {
    var out = List[CrawlerVariable]()
    for(interpreter <- interpreters) {
      out = out.::(interpreter._1.run(interpreter._2))
    }
    out
  }
}