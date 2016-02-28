package org.lmu.GenCrawler.ext.SourceAccessors

import org.lmu.GenCrawler.core.{EmptyVariable, CrawlerList, CrawlerString, SourceAccessor}

/**
  * A SourceAccessor to access websites on the network.
  */
class HttpSourceAccessor extends SourceAccessor {
  /**
    * Accesses a website.
    *
    * @return A partial function to process CrawlerVariables
    */
  def access = {
    case CrawlerString(url) => CrawlerString(scala.io.Source.fromURL(url)("UTF-8").mkString)

    case CrawlerList(urlList) => CrawlerList(for(singleUrl <- urlList) yield singleUrl match {
      case CrawlerString(url) => CrawlerString(scala.io.Source.fromURL(url)("UTF-8").mkString)
      case _ => EmptyVariable
    })
  }
}

/**
  * Companion object for short object creation.
  */
object HttpSourceAccessor {
  def apply() = new HttpSourceAccessor
}