package core

abstract class SourceAccessor {
  def access: PartialFunction[CrawlerVariable, CrawlerVariable]
}
