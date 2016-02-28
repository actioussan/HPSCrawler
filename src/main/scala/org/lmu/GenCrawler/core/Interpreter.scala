package org.lmu.GenCrawler.core

/**
  * Default behaviour for Interpreters during crawling processes.
  */
trait Interpreter {
  var resolvers: List[PartialFunction[CrawlerVariable, CrawlerVariable]] =
    List[PartialFunction[CrawlerVariable, CrawlerVariable]]()

  /**
    * Executes the interpreters logic.
    * @param input The CrawlerVariable provided as input.
    * @return An CrawlerVariable as output from this Interpreter.
    */
  def run(input: CrawlerVariable): CrawlerVariable = {
    recursiveCombineResolvers(resolvers).apply(input)
  }

  /**
    * Combines resolvers from a list recursively to one single partial function.
    * @param resolverList The list of remaining resolvers to combine.
    * @return A combined partial function.
    */
  private def recursiveCombineResolvers(resolverList: List[PartialFunction[CrawlerVariable, CrawlerVariable]]):
  PartialFunction[CrawlerVariable, CrawlerVariable] = {
    if (resolverList.isEmpty) {
      resolve.orElse({
        case _ => EmptyVariable
      })
    } else {
      resolverList.head.orElse(recursiveCombineResolvers(resolverList.tail))
    }
  }

  /**
    * Resolve function to be implemented by actual Interpreters.
    *
    * @return A partial function to process CrawlerVariables
    */
  def resolve: PartialFunction[CrawlerVariable, CrawlerVariable]

  /**
    * Adds additional resolvers to an existing interpreter instance to support more CrawlerVariables or overwrite
    * behaviour.
    *
    * @param newResolver The new partial function for this Interpreter.
    */
  def addResolver(newResolver: PartialFunction[CrawlerVariable, CrawlerVariable]) = {
    resolvers = resolvers.::(newResolver)
  }
}

/**
  * An Interpreter extending class to provide an additional input transformation before an Interpreters logic is called.
  */
trait VariableTransformation extends Interpreter {
  def transform: PartialFunction[CrawlerVariable, CrawlerVariable]

  abstract override def run(in: CrawlerVariable): CrawlerVariable = {
    val temp = transform.orElse({
      case a: CrawlerVariable => a
    }: PartialFunction[CrawlerVariable, CrawlerVariable])
    super.run(transform.applyOrElse(in, temp))
  }
}