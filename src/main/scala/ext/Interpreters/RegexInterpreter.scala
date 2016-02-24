package ext.Interpreters

import scala.util.matching.Regex
import core._

class RegexInterpreter(pattern: Regex, matchAll: Boolean, groupNum: Int = 0) extends Interpreter {
  def resolve = {
    case CrawlerString(out) => {
      if(matchAll) {
        val data = (pattern findAllIn out).matchData
        CrawlerIterator(for(i <- data) yield CrawlerString(i.group(Math.max(groupNum, i.groupCount))))
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

object RegexInterpreter {
  def apply(pattern: Regex, matchAll: Boolean) = new RegexInterpreter(pattern, matchAll)
  def apply(pattern: Regex, matchAll: Boolean, groupNum: Int) = new RegexInterpreter(pattern, matchAll, groupNum)
}