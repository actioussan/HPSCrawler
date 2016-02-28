package org.lmu.GenCrawler.core

import scala.collection.mutable

/**
  * Describes an Interpreter that can pull data from any data source. the acces to the data source is given through a
  * SourceAccessor object.
  *
  * @param accessor The accessor to use for communication.
  * @param useCache Whether to use a default memory caching method.
  */
class SourceInterpreter(accessor: SourceAccessor, useCache: Boolean = false) extends Interpreter {
  val cache = new mutable.HashMap[CrawlerVariable, CrawlerVariable]()

  /**
    * Gets the data from the SourceAccessor associated with the input variable.
    *
    * @return A partial function to process CrawlerVariables
    */
  def resolve = new PartialFunction[CrawlerVariable,CrawlerVariable] {
    def apply(input:CrawlerVariable): CrawlerVariable = {
      if (useCache && cache.contains(input)) {
        cache.get(input) match {
          case Some(output) => output
          case _ => EmptyVariable
        }
      } else {
        val output: CrawlerVariable = accessor.access(input)
        if (useCache) {
          cache.put(input, output)
        }
        output
      }
    }

    def isDefinedAt(input: CrawlerVariable): Boolean = {
      if (useCache && cache.contains(input)) {
        true
      } else {
        accessor.access.isDefinedAt(input)
      }
    }
  }
}

/**
  * Companion object for short object creation.
  */
object SourceInterpreter {
  def apply(accessor: SourceAccessor) = new SourceInterpreter(accessor)
  def apply(accessor: SourceAccessor, useCache : Boolean) = new SourceInterpreter(accessor, useCache)
}