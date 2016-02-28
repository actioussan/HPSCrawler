import org.lmu.GenCrawler.core._
import org.lmu.GenCrawler.ext.SourceAccessors._
import org.lmu.GenCrawler.ext.Interpreters.{CrawlerJsonValue, JsonInterpreter}
import org.json4s.JsonAST.{JString, JArray}

object ExampleUseCase {
  def main(args: Array[String]): Unit = {
    // in the use case we want to find tracks on Spotify that share an album with a given track (only the 20 most relevant albums do matter)
    val songName = "Fortuna imperatrix mundi"

    val crawler = GenCrawler("SpotifyCrawler")

    val apiUrl = "https://api.spotify.com/v1/search?q=" + java.net.URLEncoder.encode(songName, "UTF-8") + "&type=track"

    // first http request and json interpretation
    val initialHttpCallback = crawler.addInterpreter(
      SourceInterpreter(HttpSourceAccessor()),
      CrawlerString(apiUrl)
    )
    val initialJsonCallback = crawler.addInterpreter(JsonInterpreter(), initialHttpCallback)

    // extraction of links using VariableTransformation trait
    val secondaryHttpCallback = crawler.addInterpreter(new SourceInterpreter(HttpSourceAccessor())
      with VariableTransformation {
      def transform = {
        case CrawlerJsonValue(json) =>
          var returnUrlList = List[CrawlerVariable]()
          json \ "tracks" \ "items" match {
            case JArray(list) =>
              for (i <- list) {
                i \ "album" \ "href" match {
                  case JString(str) => returnUrlList = CrawlerString(str) :: returnUrlList
                  case _ => println("Could not find href for an album")
                }
              }
            case _ => println("Could not find data in json")
          }
          CrawlerList(returnUrlList)
      }
    }, initialJsonCallback)

    // extend JsonInterpreter anonymously to interpret multiple times at once
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

    // upon success match song data and print it
    secondaryJsonCallback.onSuccess({
      case CrawlerList(jsonList) =>
        for (singleObject <- jsonList) {
          singleObject match {
            case CrawlerJsonValue(jsonRootObject) =>
              jsonRootObject \ "name" match {
                case JString(albumName) =>
                  jsonRootObject \ "tracks" \ "items" match {
                    case JArray(jsonAlbumTrackList) =>
                      for(jsonTrackObject <- jsonAlbumTrackList) {
                        jsonTrackObject \ "name" match {
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

    // start the crawling process
    crawler.run()

    // make this thread wait for interpreters to finish
    crawler.await()
  }
}
