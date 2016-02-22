var test = new GenCrawler

class URLInterpreter extends Interpreter {
  def run(in: CrawlerVariable) = in match {
    case CrawlerString(url) => CrawlerString(scala.io.Source.fromURL(url).mkString)
    case _ => None
  }
}

var myInterpreter = new URLInterpreter
myInterpreter.run(CrawlerString("http://www.spiegel.de/"))