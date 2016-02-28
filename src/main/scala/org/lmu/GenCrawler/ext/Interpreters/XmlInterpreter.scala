package org.lmu.GenCrawler.ext.Interpreters

import org.lmu.GenCrawler.core._
import org.htmlcleaner.{TagNode, HtmlCleaner}

/**
  * CrawlerJsonValue represents a xml object which can be shared between Interpreters.
  *
  * @param content The xml object to be shared.
  */
case class CrawlerXmlValue(content: TagNode) extends CrawlerVariable

/**
  * An Interpreter for interpreting data as a xml object.
  */
class XmlInterpreter extends Interpreter {
  val cleaner = new HtmlCleaner
  val props = cleaner.getProperties

  /**
    * Resolves a String as xml object.
    *
    * @return A partial function to process CrawlerVariables
    */
  def resolve = {
    case CrawlerString(out) => CrawlerXmlValue(cleaner.clean(out))
  }
}

/**
  * Companion object for short object creation.
  */
object XmlInterpreter {
  def apply() = new XmlInterpreter
}