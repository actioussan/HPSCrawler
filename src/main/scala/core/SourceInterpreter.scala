package core

import scala.collection.mutable.{Map, HashMap}

class SourceInterpreter(acc : SourceAccessor, useCache : Boolean = false) extends Interpreter {
  val cache : Map[CrawlerVariable, CrawlerVariable] = new HashMap[CrawlerVariable, CrawlerVariable]()

  def run(in: CrawlerVariable) = {
    if(useCache && cache.contains(in)) {
      cache.get(in) match {
        case Some(out) => out
        case _ => EmptyVariable
      }
    } else {
      val out : CrawlerVariable = acc.access(in)
      if(useCache) {
        cache.put(in, out)
      }
      out
    }
  }
}
