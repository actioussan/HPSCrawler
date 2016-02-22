package core

abstract class CrawlerVariable {}

case class CrawlerString(s: String) extends CrawlerVariable {}
case object EmptyVariable extends CrawlerVariable {}