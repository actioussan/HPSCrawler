package org.lmu.GenCrawler.ext.Interpreters

import scala.util.matching.Regex
import org.lmu.GenCrawler.core._

/**
  * An Interpreter to extract information from data by using a RegEx.
  *
  * @param pattern A RegEx pattern to use for searches.
  * @param matchAll whether to match all occurrences of the pattern or just the first.
  * @param groupNum The number of the subgroup to return upon match.
  */
class RegexInterpreter(pattern: Regex, matchAll: Boolean, groupNum: Int = 0) extends Interpreter {
  /**
    * Extracts a string by the RegEx pattern from the given data.
    *
    * @return A partial function to process CrawlerVariables
    */
  def resolve = {
    case CrawlerString(out) =>
      if (matchAll) {
        val maxGroupNum = (pattern findAllIn out).groupCount
        val allMatches = (pattern findAllIn out).matchData
        var returnList = List[CrawlerVariable]()
        for (singleMatch <- allMatches) {
          returnList = CrawlerString(singleMatch group Math.max(groupNum, maxGroupNum)) :: returnList
        }
        CrawlerList(returnList)
      }
      else {
        val res = pattern findFirstMatchIn out
        res match {
          case Some(singleMatch) => CrawlerString(singleMatch group Math.max(groupNum, singleMatch.groupCount))
          case None => EmptyVariable
        }
      }
  }
}

/**
  * Companion object for short object creation.
  */
object RegexInterpreter {
  def apply(pattern: Regex, matchAll: Boolean) = new RegexInterpreter(pattern, matchAll)
  def apply(pattern: Regex, matchAll: Boolean, groupNum: Int) = new RegexInterpreter(pattern, matchAll, groupNum)
}