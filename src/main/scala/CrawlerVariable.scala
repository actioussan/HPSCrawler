/**
 * Created by test on 22.02.2016.
 */
abstract class CrawlerVariable {}

case class CrawlerString(s: String) extends CrawlerVariable {}
case object None extends CrawlerVariable {}