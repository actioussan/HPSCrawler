package ext.SourceAccessors

import core.{CrawlerString, CrawlerVariable, SourceAccessor}

case class FileSourceAccessor() extends SourceAccessor {
  def access(in:CrawlerVariable) = in match {
    case CrawlerString(url) => CrawlerString(scala.io.Source.fromFile(url).mkString)
  }
}
