package day3

import scala.io.Source.fromFile
import scala.collection.immutable.Set

object Puzzle6 {
  def main(args: Array[String]): Unit = {
    type groupOp = (Set[Char], Set[Char]) => Set[Char]
    def parseGroup(group: List[String], op: groupOp): Set[Char] =
      group match {
        case x :: Nil => Set.from(x.toCharArray)
        case x :: xs  => op(parseGroup(xs, op), Set.from(x.toCharArray))
        case _        => throw new IllegalStateException
      }

    def sol(groups: Array[String], op: groupOp): Int =
      groups
        .map(g => parseGroup(g.split("\n").toList, op))
        .map(_.size)
        .sum

    val inputFile = fromFile("inputs/puzzle6.input")
    val groups = inputFile
      .getLines()
      .mkString("\n")
      .split("\n\n")
    val solution1 = sol(groups, _.union(_))
    val solution2 = sol(groups, _.intersect(_))
    println(solution1)
    println(solution2)
  }
}
