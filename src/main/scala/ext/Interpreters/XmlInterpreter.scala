package ext.Interpreters

import core._
import scala.xml._

case class CrawlerXmlValue(xml: Elem) extends CrawlerVariable

class XmlInterpreter extends Interpreter {
  def resolve = {
    case CrawlerString(out) => CrawlerXmlValue(XML.loadString(out))
  }
}