package ext.Interpreters

import scala.util.matching.Regex
import core._

class RegexInterpreter(pattern: Regex, matchAll: Boolean, groupNum: Int = 0) extends Interpreter {
  def resolve = {
    case CrawlerString(out) => {
      if(matchAll) {
        val maxGroupNum = (pattern findAllIn out).groupCount
        val data = (pattern findAllIn out).matchData
        var ret = List[CrawlerVariable]()
        for(i <- data) {
          println(i group Math.max(groupNum, maxGroupNum))
          ret = CrawlerString(i group Math.max(groupNum, maxGroupNum)) :: ret
        }
        CrawlerList(ret)
      }
      else {
        val res = pattern findFirstMatchIn out
        res match {
          case Some(s) => CrawlerString(s group Math.max(groupNum, s.groupCount))
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