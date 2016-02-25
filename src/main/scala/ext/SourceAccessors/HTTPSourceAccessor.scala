package ext.SourceAccessors

import core.{CrawlerString, SourceAccessor}

case class HttpSourceAccessor() extends SourceAccessor {
  def access = {
    case CrawlerString(url) => CrawlerString(scala.io.Source.fromURL(url)("UTF-8").mkString)
  }
}