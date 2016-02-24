import core._
import ext.Interpreters.XmlInterpreter
import ext.SourceAccessors._

val url = CrawlerString("http://www.google.com/")
var test = new GenCrawler
case class BigDocument(s : String) extends CrawlerVariable
var myInterpreter1 = new SourceInterpreter(new FileSourceAccessor)
var myInterpreter2 = new SourceInterpreter(new HttpSourceAccessor)
//test.addInterpreter(myInterpreter2, url)
test.addInterpreter(myInterpreter1, CrawlerString("..\\README.txt"))

test.interpreters.head._1.run(CrawlerString("..\\README.txt"))
val int = test.interpreters.head._1
val call = test.interpreters.head._2
val part = int.recursiveCombineResolvers(int.resolvers)
//var opf = part.apply(call)
//var temp : CrawlerVariable = int.run(call)
println(part)
println(int)
println(call)
println(test.interpreters)
List(test.interpreters.head._1.run(test.interpreters.head._2))
test.run()
println(test.run())
//var abc : CrawlerVariable = test.run()
//println("test")
/*var code = res.head
var xml = new XmlInterpreter
var t2 = new GenCrawler()
t2.addInterpreter(xml, code)
println(t2.run())*/