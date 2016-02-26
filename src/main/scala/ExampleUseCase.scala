import core._
import ext.SourceAccessors._
import ext.Interpreters.{CrawlerJsonValue, JsonInterpreter, RegexInterpreter}
import org.json4s.JsonAST.{JString, JArray, JObject}

object ExampleUseCase {
  def main(args: Array[String]): Unit = {
    // in the use case we want to find tracks on spotify that share an album with a given track (only the 20 most relevant albums do matter)
    val songName = "Fortuna imperatrix mundi"

    val crawler = GenCrawler("SpotifyCrawler")

    val initialHttpCallback = crawler.addInterpreter(
      SourceInterpreter(HttpSourceAccessor()),
      CrawlerString("https://api.spotify.com/v1/search?q=" + java.net.URLEncoder.encode(songName, "UTF-8") + "&type=track")
    )
    val initialJsonCallback = crawler.addInterpreter(JsonInterpreter(), initialHttpCallback)
    val secondaryHttpCallback = crawler.addInterpreter(new SourceInterpreter(HttpSourceAccessor()) with VariableTransformation {
      def transform = {
        case CrawlerJsonValue(json) =>
          var ret = List[CrawlerVariable]()
          json \ "tracks" \ "items" match {
            case JArray(list) =>
              for (i <- list) {
                i \ "album" \ "href" match {
                  case JString(str) => ret = CrawlerString(str) :: ret
                  case _ => println("Could not find href for an album")
                }
              }
            case _ => println("Could not find data in json")
          }
          CrawlerList(ret)
      }
    }, initialJsonCallback)
    val secondaryJsonCallback = crawler.addInterpreter(new JsonInterpreter() {
      override def run(in: CrawlerVariable) = {
        in match {
          case CrawlerList(list) =>
            var ret = List[CrawlerVariable]()
            for(i <- list) ret = super.run(i) :: ret
            CrawlerList(ret)
          case _ => super.run(in)
        }
      }
    }, secondaryHttpCallback)

    secondaryJsonCallback.onSuccess({
      case CrawlerList(it) =>
        for (i <- it) {
          i match {
            case CrawlerJsonValue(json) =>
              json \ "name" match {
                case JString(albumName) =>
                  json \ "tracks" \ "items" match {
                    case JArray(list) =>
                      for(track <- list) {
                        track \ "name" match {
                          case JString(trackName) =>
                            println(s"Found title on album '$albumName': $trackName")
                          case _ => println("Could not find name of track")
                        }
                      }
                    case _ => println("Could not find data in album json")
                  }
                case _ => println("Could not find name of album")
              }
            case a: CrawlerVariable => println(s"Got non-json data: $a")
          }
        }
      case a: CrawlerVariable => println(s"error, only got: $a")
    })

    crawler.run()
    crawler.await()
  }
}
