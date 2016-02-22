package core

class GenCrawler() {
  var interpreters : List[(Interpreter, CrawlerVariable)] = List[(Interpreter, CrawlerVariable)]()
  def addInterpreter(in: Interpreter, v : CrawlerVariable) = {
    // MAYBE: check if list already contains element
    interpreters = interpreters.::((in, v))
  }
  def run() : List[CrawlerVariable] = {
    var out = List[CrawlerVariable]()
    for(interpreter <- interpreters) {
      println(interpreter)
      out = out.::(interpreter._1.run(interpreter._2))
    }
    out
  }
}