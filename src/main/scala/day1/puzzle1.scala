package day1

import scala.io.Source.fromFile

object Puzzle1 {
  def main(args: Array[String]): Unit = {
    println("Puzzle 1")
    val inputFile = fromFile("inputs/puzzle1.input")

    if (inputFile != null) {
      val values = inputFile.getLines().toArray.map(_.toInt)
      val results = for {
        x <- values
        y <- values
        z <- values
        if (x + y + z == 2020)
      } yield (x * y * z) 
     
      println (results.headOption)
    } else {
      println("File was null")
    }
  }
}
