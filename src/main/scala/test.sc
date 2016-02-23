import core.{CrawlerString, CrawlerVariable}

import core._
import ext.Interpreters.XmlInterpreter
import ext.SourceAccessors._
var test = new GenCrawler
case class BigDocument(s : String) extends CrawlerVariable
var myInterpreter = new SourceInterpreter(new FileSourceAccessor)
test.addInterpreter(new SourceInterpreter(new HttpSourceAccessor), CrawlerString("http://www.google.com/"))
//test.addInterpreter(myInterpreter, CrawlerString("..\\README.txt"))
var code = test.run().head
var xml = new XmlInterpreter
var t2 = new GenCrawler()
t2.addInterpreter(xml, code)
println(t2.run())