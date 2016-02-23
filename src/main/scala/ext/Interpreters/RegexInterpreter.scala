package ext.Interpreters

import scala.util.matching.Regex
import core._

class RegexInterpreter(pattern: Regex, matchAll: Boolean) extends Interpreter {
  def resolve = {
    case CrawlerString(out) => {
      if(matchAll) {
        CrawlerIterator(for(i <- pattern findAllIn out) yield CrawlerString(i))
      }
      else {
        val res = pattern findFirstIn out
        res match {
          case Some(s) => CrawlerString(s)
          case None => EmptyVariable
        }
      }
    }
  }
}