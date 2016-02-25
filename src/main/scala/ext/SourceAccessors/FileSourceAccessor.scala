package ext.SourceAccessors

import core.{CrawlerList, CrawlerString, SourceAccessor}

class FileSourceAccessor extends SourceAccessor {
  def access = {
    case CrawlerString(url) => CrawlerString(scala.io.Source.fromFile(url).mkString)

    case CrawlerList(it) => CrawlerList(for(i <- it) yield i match {
      case CrawlerString(url) => CrawlerString(scala.io.Source.fromFile(url).mkString)
    })
  }
}

object FileSourceAccessor {
  def apply() = new FileSourceAccessor
}