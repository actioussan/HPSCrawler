import core._
import ext.{HTTPSourceAccessor, FileSourceAccessor}

var test = new GenCrawler

var myInterpreter = new SourceInterpreter(new FileSourceAccessor())
test.addInterpreter(myInterpreter, CrawlerString("C:\\LMU\\Scala\\project\\HPSCrawler\\build.sbt"))
test.addInterpreter(new SourceInterpreter(new HTTPSourceAccessor()), CrawlerString("http://www.google.com/"))

test.run()