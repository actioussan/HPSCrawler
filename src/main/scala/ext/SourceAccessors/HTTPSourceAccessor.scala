package ext.SourceAccessors

import core.{CrawlerString, CrawlerVariable, SourceAccessor}

case class HTTPSourceAccessor() extends SourceAccessor {
  def access(in:CrawlerVariable) = in match {
    case CrawlerString(url) => CrawlerString(scala.io.Source.fromURL(url).mkString)
  }
}