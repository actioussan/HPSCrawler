package ext.SourceAccessors

import core.{CrawlerString, CrawlerVariable, SourceAccessor}

case class HttpSourceAccessor() extends SourceAccessor {
  def access(in:CrawlerVariable) = in match {
    case CrawlerString(url) => CrawlerString(scala.io.Source.fromURL(url).mkString)
  }
}