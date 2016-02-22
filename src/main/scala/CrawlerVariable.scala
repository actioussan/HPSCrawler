abstract class CrawlerVariable {}

case class CrawlerString(s: String) extends CrawlerVariable {}
case object None extends CrawlerVariable {}