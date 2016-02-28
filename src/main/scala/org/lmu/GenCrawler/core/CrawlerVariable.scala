package org.lmu.GenCrawler.core

/**
  * CrawlerVariable defines a trait that needs to be included in classes which should be used as variables between
  * interpreter calls.
  */
trait CrawlerVariable

/**
  * CrawlerString represents a single string which can be shared between Interpreters.
  *
  * @param content The string to be shared.
  */
case class CrawlerString(content: String) extends CrawlerVariable

/**
  * CrawlerList represents a collection of other CrawlerVariables which can be shared between Interpreters.
  *
  * @param content The list to be shared.
  */
case class CrawlerList(content: List[CrawlerVariable]) extends CrawlerVariable

/**
  * EmptyVariable represents an empty input or output for Interpreters.
  */
case object EmptyVariable extends CrawlerVariable