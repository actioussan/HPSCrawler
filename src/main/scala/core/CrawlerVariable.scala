package core

trait CrawlerVariable

case class CrawlerString(s: String) extends CrawlerVariable
case class CrawlerList(i: List[CrawlerVariable]) extends CrawlerVariable
case object EmptyVariable extends CrawlerVariable