package ext.Interpreters

import core._
import org.htmlcleaner.{TagNode, HtmlCleaner}

case class CrawlerXmlValue(node: TagNode) extends CrawlerVariable

class XmlInterpreter extends Interpreter {
  val cleaner = new HtmlCleaner
  val props = cleaner.getProperties


  def resolve = {
    case CrawlerString(out) => CrawlerXmlValue(cleaner.clean(out))
  }
}

object XmlInterpreter {
  def apply() = new XmlInterpreter
}