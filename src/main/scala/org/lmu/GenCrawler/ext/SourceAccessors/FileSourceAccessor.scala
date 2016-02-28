package org.lmu.GenCrawler.ext.SourceAccessors

import org.lmu.GenCrawler.core.{EmptyVariable, CrawlerList, CrawlerString, SourceAccessor}

/**
  * A SourceAccessor to access files on the hard drive.
  */
class FileSourceAccessor extends SourceAccessor {
  /**
    * Accesses a file.
    *
    * @return A partial function to process CrawlerVariables
    */
  def access = {
    case CrawlerString(url) => CrawlerString(scala.io.Source.fromFile(url).mkString)

    case CrawlerList(urlList) => CrawlerList(for(singleUrl <- urlList) yield singleUrl match {
      case CrawlerString(url) => CrawlerString(scala.io.Source.fromFile(url).mkString)
      case _ => EmptyVariable
    })
  }
}

/**
  * Companion object for short object creation.
  */
object FileSourceAccessor {
  def apply() = new FileSourceAccessor
}