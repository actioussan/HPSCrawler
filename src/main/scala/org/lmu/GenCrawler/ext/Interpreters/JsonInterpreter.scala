package org.lmu.GenCrawler.ext.Interpreters

import org.json4s._
import org.json4s.native.JsonMethods._

import org.lmu.GenCrawler.core.{EmptyVariable, CrawlerString, CrawlerVariable, Interpreter}

/**
  * CrawlerJsonValue represents a json object which can be shared between Interpreters.
  *
  * @param content The json object to be shared.
  */
case class CrawlerJsonValue(content: JValue) extends CrawlerVariable

/**
  * An Interpreter for interpreting data as a json object.
  */
class JsonInterpreter extends Interpreter {

  /**
    * Resolves a String as json object.
    *
    * @return A partial function to process CrawlerVariables
    */
  def resolve = {
    case CrawlerString(jsonString) => CrawlerJsonValue(parse(jsonString))
    case _ => EmptyVariable
  }
}

/**
  * Companion object for short object creation.
  */
object JsonInterpreter {
  def apply() = new JsonInterpreter
}