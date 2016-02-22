package core

abstract class SourceAccessor {
  def access(in: CrawlerVariable): CrawlerVariable
}
