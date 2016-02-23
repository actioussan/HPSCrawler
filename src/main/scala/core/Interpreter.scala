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
