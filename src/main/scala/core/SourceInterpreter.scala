package core

import scala.collection.mutable.{Map, HashMap}

class SourceInterpreter(acc : SourceAccessor, useCache : Boolean = false) extends Interpreter {
  val cache : Map[CrawlerVariable, CrawlerVariable] = new HashMap[CrawlerVariable, CrawlerVariable]()

  def resolve = new PartialFunction[CrawlerVariable,CrawlerVariable] {
    def apply(in:CrawlerVariable) : CrawlerVariable = {
      if (useCache && cache.contains(in)) {
        cache.get(in) match {
          case Some(out) => out
          case _ => EmptyVariable
        }
      } else {
        val out : CrawlerVariable = acc.access(in)
        if (useCache) {
          cache.put(in, out)
        }
        out
      }
    }

    def isDefinedAt(in: CrawlerVariable): Boolean = {
      if (useCache && cache.contains(in)) {
        true
      } else {
        acc.access.isDefinedAt(in)
      }
    }
  }
}

object SourceInterpreter {
  def apply(acc : SourceAccessor) = new SourceInterpreter(acc)
  def apply(acc : SourceAccessor, useCache : Boolean) = new SourceInterpreter(acc, useCache)
}