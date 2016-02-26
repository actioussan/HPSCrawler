import core._
import ext.SourceAccessors._
import ext.Interpreters.RegexInterpreter

/**
  * Created by Rene on 24.02.2016.
  */
object TestMain {
  def main(args: Array[String]): Unit = {
    val defaultUrl = "http://mtptt.netau.net/"
    val url = CrawlerString(defaultUrl)

    val crawler = GenCrawler("UltimateCrawler")
    val httpSourceCrawler = new SourceInterpreter(HttpSourceAccessor()) with VariableTransformation {
      def transform = {
        case CrawlerList(it) => {
          var ret: CrawlerVariable = EmptyVariable
          for (i <- it) i match {
            case CrawlerString(s) => if (ret == EmptyVariable) ret = CrawlerString(s)
          }
          ret
        }
      }
    }
    val linkInterpreter = RegexInterpreter("href=\"(.*?)\"".r, true, 1)

    val initialHttpCallback = crawler.addInterpreter(httpSourceCrawler, url)
    val linkCallback = crawler.addInterpreter(linkInterpreter, initialHttpCallback)
    val secondaryHttpCallback = crawler.addInterpreter(httpSourceCrawler, linkCallback)

    secondaryHttpCallback.onSuccess({
      case CrawlerList(it) => for (i <- it) println(i)
      case a: CrawlerVariable => println(s"second http $a")
    })
    linkCallback.onSuccess({
      case CrawlerList(it) => for (i <- it) println(i)
      case a: CrawlerVariable => println(s"link $a")
    })

    crawler.run()
    crawler.await()
  }
}
