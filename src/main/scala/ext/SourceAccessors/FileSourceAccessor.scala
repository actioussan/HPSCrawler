package ext.SourceAccessors

import core.{CrawlerString, SourceAccessor}

case class FileSourceAccessor() extends SourceAccessor {
  def access = {
    case CrawlerString(url) => CrawlerString(scala.io.Source.fromFile(url).mkString)
  }
}
