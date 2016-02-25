package core

trait Interpreter {
  var resolvers : List[PartialFunction[CrawlerVariable, CrawlerVariable]] = List[PartialFunction[CrawlerVariable, CrawlerVariable]]()

  def run(in: CrawlerVariable): CrawlerVariable = {
    recursiveCombineResolvers(resolvers).apply(in)
  }

  def recursiveCombineResolvers(l : List[PartialFunction[CrawlerVariable, CrawlerVariable]]) : PartialFunction[CrawlerVariable, CrawlerVariable] = {
    if (l.isEmpty) {
      resolve.orElse({
        case _ => EmptyVariable
      })
    } else {
      l.head.orElse(recursiveCombineResolvers(l.tail))
    }
  }

  def resolve : PartialFunction[CrawlerVariable, CrawlerVariable]

  def addResolver(newResolver : PartialFunction[CrawlerVariable, CrawlerVariable]) = {
    resolvers = resolvers.::(newResolver)
  }
}

trait VariableTransformation extends Interpreter {
  def transform : PartialFunction[CrawlerVariable, CrawlerVariable]

  abstract override def run(in: CrawlerVariable): CrawlerVariable = {
    val temp = transform.orElse({
      case a: CrawlerVariable => a
    }: PartialFunction[CrawlerVariable, CrawlerVariable])
    super.run(transform.applyOrElse(in, temp))
  }
}