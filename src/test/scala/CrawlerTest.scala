import core._
import ext.Interpreters._
import ext.SourceAccessors.{FileSourceAccessor, HttpSourceAccessor}
import org.scalatest._

class CrawlerTest  extends FlatSpec with Matchers {

  val crawler = GenCrawler("UltimateCrawler")

  val url = CrawlerString("http://mtptt.netau.net/")
  val httpSourceCrawler = SourceInterpreter(HttpSourceAccessor())
  val httpCallback = crawler.addInterpreter(httpSourceCrawler, url)

  "HTML Callback " should "get content as String" in {
    httpCallback.onSuccess({
      case CrawlerString(s) => s should be("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">\n<html>\n <head>\n  <title>Index of /</title>\n </head>\n <body>\n<h1>Index of /</h1>\n<ul><li><a href=\"mtg_tt_deckbuilder/\"> mtg_tt_deckbuilder/</a></li>\n</ul>\n</body></html>")
    })
  }

  val xmlInterpreter = new XmlInterpreter
  val xmlCallback = crawler.addInterpreter(xmlInterpreter, httpCallback)

  "XML Callback " should "get content with XmlInterpreter" in {
    xmlCallback.onSuccess({
      case CrawlerXmlValue(n) => n should be("html"); n.getAllChildren should be("[head, body]")
    })
  }

  val regexInterpreter = RegexInterpreter("href=\"(.*?)\"".r, true, 1)
  val regexCallback = crawler.addInterpreter(regexInterpreter, httpCallback)

  "Regex Callback " should "get URL with RegexInterpreter" in {
    regexCallback.onSuccess({
      case CrawlerString(s) => s should be("mtg_tt_deckbuilder/")
    })
  }

  /* TODO: Content aus FolgeURL...
  val innerHttpCallback = crawler.addInterpreter(httpSourceCrawler, linkCallback)

  "Inner callback" should "..." in {
    val text = innerHttpCallback.onSuccess({
      case CrawlerString(s) =>
      case _ => "no result"
    })
  }*/

  val path = CrawlerString("C:\\Users\\Jenna\\Desktop\\akkaGraph-skeleton\\HPSCrawler\\src\\test\\scala\\packagestructure.json")
  val fileSourceCrawler = SourceInterpreter(FileSourceAccessor())
  val fileCallback = crawler.addInterpreter(fileSourceCrawler, path)
  val jsonInterpreter = new JsonInterpreter
  val jsonCallback = crawler.addInterpreter(jsonInterpreter, fileCallback)

  "JSON Callback " should "get file content with JsonInterpreter" in {
    jsonCallback.onSuccess({
      case CrawlerJsonValue(j) => j should be("JArray(List(JObject(List((package,JString(core)), (children,JArray(List())))), JObject(List((package,JString(ext)), (children,JArray(List(JObject(List((package,JString(Interpreters)), (children,JArray(List())))), JObject(List((package,JString(SourceAccessors)), (children,JArray(List())))))))))))")
    })
  }

  crawler.run()

}
