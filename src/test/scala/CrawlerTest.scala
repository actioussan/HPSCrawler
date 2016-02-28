import org.lmu.GenCrawler.core._
import org.lmu.GenCrawler.ext.Interpreters._
import org.lmu.GenCrawler.ext.SourceAccessors.{FileSourceAccessor, HttpSourceAccessor}
import org.scalatest._

class CrawlerTest  extends FlatSpec with Matchers {

  val testUrl = "http://mtptt.netau.net/"
  val crawlerUrl = CrawlerString(testUrl)
  val testPath = "src\\test\\scala\\packagestructure.json"
  val crawlerPath = CrawlerString(testPath)

  val crawlerHtmlContent = CrawlerString("\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">\n<html>\n <head>\n  <title>Index of /</title>\n </head>\n <body>\n<h1>Index of /</h1>\n<ul><li><a href=\"mtg_tt_deckbuilder/\"> mtg_tt_deckbuilder/</a></li>\n</ul>\n</body></html>")
  val crawlerJsonContent = CrawlerString("{\n  \"album_type\" : \"album\",\n  \"artists\" : [ {\n    \"external_urls\" : {\n      \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n    },\n    \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n    \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n    \"name\" : \"Cyndi Lauper\",\n    \"type\" : \"artist\",\n    \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n  } ],\n  \"available_markets\" : [ ],\n  \"copyrights\" : [ {\n    \"text\" : \"(P) 2000 Sony Music Entertainment Inc.\",\n    \"type\" : \"P\"\n  } ],\n  \"external_ids\" : {\n    \"upc\" : \"5099749994324\"\n  },\n  \"external_urls\" : {\n    \"spotify\" : \"https://open.spotify.com/album/0sNOF9WDwhWunNAHPD3Baj\"\n  },\n  \"genres\" : [ ],\n  \"href\" : \"https://api.spotify.com/v1/albums/0sNOF9WDwhWunNAHPD3Baj\",\n  \"id\" : \"0sNOF9WDwhWunNAHPD3Baj\",\n  \"images\" : [ {\n    \"height\" : 640,\n    \"url\" : \"https://i.scdn.co/image/07c323340e03e25a8e5dd5b9a8ec72b69c50089d\",\n    \"width\" : 640\n  }, {\n    \"height\" : 300,\n    \"url\" : \"https://i.scdn.co/image/8b662d81966a0ec40dc10563807696a8479cd48b\",\n    \"width\" : 300\n  }, {\n    \"height\" : 64,\n    \"url\" : \"https://i.scdn.co/image/54b3222c8aaa77890d1ac37b3aaaa1fc9ba630ae\",\n    \"width\" : 64\n  } ],\n  \"name\" : \"She's So Unusual\",\n  \"popularity\" : 1,\n  \"release_date\" : \"1983\",\n  \"release_date_precision\" : \"year\",\n  \"tracks\" : {\n    \"href\" : \"https://api.spotify.com/v1/albums/0sNOF9WDwhWunNAHPD3Baj/tracks?offset=0&limit=50\",\n    \"items\" : [ {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 305560,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/3f9zqUnrnIq0LANhmnaF0V\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/3f9zqUnrnIq0LANhmnaF0V\",\n      \"id\" : \"3f9zqUnrnIq0LANhmnaF0V\",\n      \"name\" : \"Money Changes Everything\",\n      \"preview_url\" : null,\n      \"track_number\" : 1,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:3f9zqUnrnIq0LANhmnaF0V\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 238266,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/2joHDtKFVDDyWDHnOxZMAX\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/2joHDtKFVDDyWDHnOxZMAX\",\n      \"id\" : \"2joHDtKFVDDyWDHnOxZMAX\",\n      \"name\" : \"Girls Just Want to Have Fun\",\n      \"preview_url\" : null,\n      \"track_number\" : 2,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:2joHDtKFVDDyWDHnOxZMAX\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 306706,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/6ClztHzretmPHCeiNqR5wD\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/6ClztHzretmPHCeiNqR5wD\",\n      \"id\" : \"6ClztHzretmPHCeiNqR5wD\",\n      \"name\" : \"When You Were Mine\",\n      \"preview_url\" : null,\n      \"track_number\" : 3,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:6ClztHzretmPHCeiNqR5wD\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 241333,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/2tVHvZK4YYzTloSCBPm2tg\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/2tVHvZK4YYzTloSCBPm2tg\",\n      \"id\" : \"2tVHvZK4YYzTloSCBPm2tg\",\n      \"name\" : \"Time After Time\",\n      \"preview_url\" : null,\n      \"track_number\" : 4,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:2tVHvZK4YYzTloSCBPm2tg\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 229266,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/6iLhMDtOr52OVXaZdha5M6\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/6iLhMDtOr52OVXaZdha5M6\",\n      \"id\" : \"6iLhMDtOr52OVXaZdha5M6\",\n      \"name\" : \"She Bop\",\n      \"preview_url\" : null,\n      \"track_number\" : 5,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:6iLhMDtOr52OVXaZdha5M6\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 272840,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/3csiLr2B2wRj4lsExn6jLf\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/3csiLr2B2wRj4lsExn6jLf\",\n      \"id\" : \"3csiLr2B2wRj4lsExn6jLf\",\n      \"name\" : \"All Through the Night\",\n      \"preview_url\" : null,\n      \"track_number\" : 6,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:3csiLr2B2wRj4lsExn6jLf\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 220333,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/4mRAnuBGYsW4WGbpW0QUkp\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/4mRAnuBGYsW4WGbpW0QUkp\",\n      \"id\" : \"4mRAnuBGYsW4WGbpW0QUkp\",\n      \"name\" : \"Witness\",\n      \"preview_url\" : null,\n      \"track_number\" : 7,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:4mRAnuBGYsW4WGbpW0QUkp\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 252626,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/3AIeUnffkLQaUaX1pkHyeD\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/3AIeUnffkLQaUaX1pkHyeD\",\n      \"id\" : \"3AIeUnffkLQaUaX1pkHyeD\",\n      \"name\" : \"I'll Kiss You\",\n      \"preview_url\" : null,\n      \"track_number\" : 8,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:3AIeUnffkLQaUaX1pkHyeD\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 45933,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/53eCpAFNbA9MQNfLilN3CH\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/53eCpAFNbA9MQNfLilN3CH\",\n      \"id\" : \"53eCpAFNbA9MQNfLilN3CH\",\n      \"name\" : \"He's so Unusual\",\n      \"preview_url\" : null,\n      \"track_number\" : 9,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:53eCpAFNbA9MQNfLilN3CH\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 196373,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/51JS0KXziu9U1T8EBdRTUF\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/51JS0KXziu9U1T8EBdRTUF\",\n      \"id\" : \"51JS0KXziu9U1T8EBdRTUF\",\n      \"name\" : \"Yeah Yeah\",\n      \"preview_url\" : null,\n      \"track_number\" : 10,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:51JS0KXziu9U1T8EBdRTUF\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 275560,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/2BGJvRarwOa2kiIGpLjIXT\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/2BGJvRarwOa2kiIGpLjIXT\",\n      \"id\" : \"2BGJvRarwOa2kiIGpLjIXT\",\n      \"name\" : \"Money Changes Everything\",\n      \"preview_url\" : null,\n      \"track_number\" : 11,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:2BGJvRarwOa2kiIGpLjIXT\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 320400,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/5ggatiDTbCIJsUAa7IUP65\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/5ggatiDTbCIJsUAa7IUP65\",\n      \"id\" : \"5ggatiDTbCIJsUAa7IUP65\",\n      \"name\" : \"She Bop - Live\",\n      \"preview_url\" : null,\n      \"track_number\" : 12,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:5ggatiDTbCIJsUAa7IUP65\"\n    }, {\n      \"artists\" : [ {\n        \"external_urls\" : {\n          \"spotify\" : \"https://open.spotify.com/artist/2BTZIqw0ntH9MvilQ3ewNY\"\n        },\n        \"href\" : \"https://api.spotify.com/v1/artists/2BTZIqw0ntH9MvilQ3ewNY\",\n        \"id\" : \"2BTZIqw0ntH9MvilQ3ewNY\",\n        \"name\" : \"Cyndi Lauper\",\n        \"type\" : \"artist\",\n        \"uri\" : \"spotify:artist:2BTZIqw0ntH9MvilQ3ewNY\"\n      } ],\n      \"available_markets\" : [ ],\n      \"disc_number\" : 1,\n      \"duration_ms\" : 288240,\n      \"explicit\" : false,\n      \"external_urls\" : {\n        \"spotify\" : \"https://open.spotify.com/track/5ZBxoa2kBrBah3qNIV4rm7\"\n      },\n      \"href\" : \"https://api.spotify.com/v1/tracks/5ZBxoa2kBrBah3qNIV4rm7\",\n      \"id\" : \"5ZBxoa2kBrBah3qNIV4rm7\",\n      \"name\" : \"All Through The Night - Live\",\n      \"preview_url\" : null,\n      \"track_number\" : 13,\n      \"type\" : \"track\",\n      \"uri\" : \"spotify:track:5ZBxoa2kBrBah3qNIV4rm7\"\n    } ],\n    \"limit\" : 50,\n    \"next\" : null,\n    \"offset\" : 0,\n    \"previous\" : null,\n    \"total\" : 13\n  },\n  \"type\" : \"album\",\n  \"uri\" : \"spotify:album:0sNOF9WDwhWunNAHPD3Baj\"\n}")

  "HTML Callback " should "get content of website as String" in {
    val crawler = GenCrawler("HTMLTestCrawler")
    val httpCallback = crawler.addInterpreter(SourceInterpreter(HttpSourceAccessor()), crawlerUrl)
    httpCallback.onSuccess({
      case CrawlerString(s) => s should be(scala.io.Source.fromURL(testUrl).mkString)
      case _ => fail
    })
    httpCallback.onFailure({
      case e: Throwable => fail(e)
    })
    crawler.run()
    crawler.await()
  }

  "File Callback " should "get content of file as String" in {
    val crawler = GenCrawler("HTMLTestCrawler")
    val fileCallback = crawler.addInterpreter(SourceInterpreter(FileSourceAccessor()), crawlerPath)
    fileCallback.onSuccess({
      case CrawlerString(s) => s should be(scala.io.Source.fromFile(testPath).mkString)
      case _ => fail
    })
    fileCallback.onFailure({
      case e: Throwable => fail(e)
    })
    crawler.run()
    crawler.await()
  }

  "XML Callback " should "interpret with XmlInterpreter" in {
    val crawler = GenCrawler("XMLTestCrawler")
    val xmlCallback = crawler.addInterpreter(
      XmlInterpreter(),
      crawlerHtmlContent
    )
    xmlCallback.onSuccess({
      case CrawlerXmlValue(n) => n.getName should be("html"); n.getAllChildren.toString should be("[head, body]")
      case _ => fail
    })
    xmlCallback.onFailure({
      case e: Throwable => fail(e)
    })
    crawler.run()
    crawler.await()
  }

  "Regex(multi) Callback " should "interpret with RegexInterpreter" in {
    val crawler = GenCrawler("RegExMultiTestCrawler")
    val regexCallback = crawler.addInterpreter(
      RegexInterpreter("href=\"(.*?)\"".r, true, 1),
      crawlerHtmlContent
    )
    regexCallback.onSuccess({
      case CrawlerList(li) => li should not be empty; li.head should be(CrawlerString("mtg_tt_deckbuilder/"))
      case _ => fail
    })
    regexCallback.onFailure({
      case e: Throwable => fail(e)
    })
    crawler.run()
    crawler.await()
  }

  "Regex(single) Callback " should "interpret with RegexInterpreter" in {
    val crawler = GenCrawler("RegExSingleTestCrawler")
    val regexCallback = crawler.addInterpreter(
      RegexInterpreter("href=\"(.*?)\"".r, false, 1),
      crawlerHtmlContent
    )
    regexCallback.onSuccess({
      case CrawlerString(s) => s should be("mtg_tt_deckbuilder/")
      case _ => fail
    })
    regexCallback.onFailure({
      case e: Throwable => fail(e)
    })
    crawler.run()
    crawler.await()
  }

  "JSON Callback " should "interpret with JsonInterpreter" in {
    val crawler = GenCrawler("JsonTestCrawler")
    val jsonCallback = crawler.addInterpreter(JsonInterpreter(), crawlerJsonContent)
    jsonCallback.onSuccess({
      case CrawlerJsonValue(j) => j.children should not be empty; j.children.head.toString should be("JString(album)")
      case _ => fail
    })
    jsonCallback.onFailure({
      case e: Throwable => fail(e)
    })
    crawler.run()
    crawler.await()
  }

  "Variable Transformation callback" should "tranform variable" in {
    val crawler = GenCrawler("TransformTestCrawler")
    class ReturnInterpreter extends Interpreter {
      def resolve = { case any: CrawlerVariable => any }
    }
    val myInterpreter = new ReturnInterpreter() with VariableTransformation {
      def transform = {
        case CrawlerList(list) => if(list.isEmpty) EmptyVariable else list.head
      }
    }
    val transformationCallback = crawler.addInterpreter(myInterpreter, CrawlerList(List[CrawlerVariable]().::(CrawlerString("test"))))
    transformationCallback.onSuccess({
      case CrawlerString(s) => s should be("test")
      case _ => fail
    })
    transformationCallback.onFailure({
      case e: Throwable => fail(e)
    })
    crawler.run()
    crawler.await()
  }


}
