/**
  * Created by Rene on 24.02.2016.
  */
object TestMain {
  def main(args: Array[String]): Unit = {
    import core._
    import ext.Interpreters.XmlInterpreter
    import ext.SourceAccessors._

    val url = CrawlerString("http://mtptt.netau.net/")
    var test = new GenCrawler
    case class BigDocument(s : String) extends CrawlerVariable
    var myInterpreter1 = new SourceInterpreter(new FileSourceAccessor)
    var myInterpreter2 = new SourceInterpreter(new HttpSourceAccessor)
    test.addInterpreter(myInterpreter2, url)
    //test.addInterpreter(myInterpreter1, CrawlerString("build.sbt"))

    val abc = test.run()

    val code = abc.head
    val xml = new XmlInterpreter
    val t2 = new GenCrawler()
    t2.addInterpreter(xml, code)
    println(t2.run())
  }
}
