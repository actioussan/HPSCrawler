import core._
import ext.SourceAccessors._
import ext.Interpreters.RegexInterpreter

/**
  * Created by Rene on 24.02.2016.
  */
object TestMain {
  def main(args: Array[String]): Unit = {
    val url = CrawlerString("http://mtptt.netau.net/")

    val crawler = GenCrawler("UltimateCrawler")
    val httpSourceCrawler = SourceInterpreter(HttpSourceAccessor())
    val linkInterpreter = RegexInterpreter("href=\"(.*?)\"".r, true, 1)

    val initialHttpCallback = crawler.addInterpreter(httpSourceCrawler, url)
    val linkCallback = crawler.addInterpreter(linkInterpreter, initialHttpCallback)
    val secondaryHttpCallback = crawler.addInterpreter(httpSourceCrawler, linkCallback)

    secondaryHttpCallback.onSuccess({
      case CrawlerIterator(it) => for (i2 <- it) println(i2)
      case _ => println("test")
    })
    linkCallback.onSuccess({
      case _ => println("test")
    })

    crawler.run()
  }
}
