import core._
import ext.SourceAccessors._

var test = new GenCrawler

var myInterpreter = new SourceInterpreter(new FileSourceAccessor())
test.addInterpreter(new SourceInterpreter(new HTTPSourceAccessor()), CrawlerString("http://www.google.com/"))
test.addInterpreter(myInterpreter, CrawlerString("..\\README.txt"))

test.run()