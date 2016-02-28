package org.lmu.GenCrawler.core

/**
  * Abstract class for describing access to a source.
  */
abstract class SourceAccessor {
  /**
    * Access function to be implemented by actual SourceAccessors.
    *
    * @return A partial function to process CrawlerVariables
    */
  def access: PartialFunction[CrawlerVariable, CrawlerVariable]
}
