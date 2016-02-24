package ext.SourceAccessors

import core.{CrawlerIterator, CrawlerString, SourceAccessor}

class FileSourceAccessor extends SourceAccessor {
  def access = {
    case CrawlerString(url) => CrawlerString(scala.io.Source.fromFile(url).mkString)

    case CrawlerIterator(it) => CrawlerIterator(for(i <- it) yield i match {
      case CrawlerString(url) => CrawlerString(scala.io.Source.fromFile(url).mkString)
    })
  }
}

object FileSourceAccessor {
  def apply() = new FileSourceAccessor
}