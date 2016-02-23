package core

trait CrawlerVariable

case class CrawlerString(s: String) extends CrawlerVariable
case class CrawlerIterator(i: Iterator[CrawlerVariable]) extends CrawlerVariable
case object EmptyVariable extends CrawlerVariable