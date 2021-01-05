package day3

import scala.io.Source.fromFile

object Puzzle3 {
  def countTrees(right: Int, down: Int): Int = {
    val inputFile = fromFile("inputs/puzzle3.input")
    recurse(inputFile.getLines(), right, down, 0, 0)
  }
  def recurse(lines: Iterator[String], right: Int, down: Int, x: Int, y: Int): Int =
    if (lines.isEmpty) {
      0
    } else {
      val line = (lines.next())

      if (y % down == 0) {
        line.charAt(x % line.length) match {
          case '.' => recurse(lines, right, down, x + right, y + 1)
          case '#' => recurse(lines, right, down, x + right, y + 1) + 1
        }
      } else {
        recurse(lines, right, down, x, y + 1)
      }
    }

  def main(args: Array[String]): Unit = {
    println("Puzzle 3")

    val slopes     = (1, 1) :: (3, 1) :: (5, 1) :: (7, 1) :: (1, 2) :: Nil
    val slopeTrees = slopes.map(s => countTrees (s._1, s._2))
    val prod = slopeTrees.foldLeft(1L)(_ * _)
    //val slopeTrees = slopes.map(s => countTrees (s._1, s._2))
    println(s"Number of trees: $slopeTrees, $prod")

  }
}
