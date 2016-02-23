package ext.Interpreters

import scala.util.matching.Regex
import core._

class RegexInterpreter(pattern: Regex, matchAll: Boolean) extends Interpreter {
  def run(in : CrawlerVariable) = {
    in match {
        case CrawlerString(out) => var x = {
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
        case _ => EmptyVariable
    }

  }
}