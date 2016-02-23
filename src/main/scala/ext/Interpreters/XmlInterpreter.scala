package ext.Interpreters

import core._
import scala.xml._

case class CrawlerXmlValue(xml: Elem) extends CrawlerVariable

class XmlInterpreter extends Interpreter {
  def run(in : CrawlerVariable) = {
    in match {
        case CrawlerString(out) => CrawlerXmlValue(XML.loadString(out))
        case _ => EmptyVariable
    }

  }
}