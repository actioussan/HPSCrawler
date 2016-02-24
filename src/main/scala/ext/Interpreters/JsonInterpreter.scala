package ext.Interpreters

import org.json4s._
import org.json4s.native.JsonMethods._

import core.{EmptyVariable, CrawlerString, CrawlerVariable, Interpreter}

case class CrawlerJsonValue(json : JValue) extends CrawlerVariable

class JsonInterpreter extends Interpreter {
  def resolve = {
    case CrawlerString(s) => CrawlerJsonValue(parse(s))
    case _ => EmptyVariable
  }
}

object JsonInterpreter {
  def apply = new JsonInterpreter
}