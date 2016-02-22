package core

trait Interpreter {
  def run(in: CrawlerVariable): CrawlerVariable
}
