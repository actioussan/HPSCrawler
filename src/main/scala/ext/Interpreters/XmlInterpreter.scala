package ext.Interpreters

import core.{SourceAccessor, CrawlerVariable, Interpreter}
import scala.xml._

class XmlInterpreter(acc : SourceAccessor) extends Interpreter {
  def run(in : CrawlerVariable) = {
    var out = acc.access(in) // XMLString
    // Umwandeln zu XMLObject
    var xml = XML.loadString(out)
  }
}