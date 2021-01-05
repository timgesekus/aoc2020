package day2

import scala.io.Source.fromFile

object Puzzle2 {
  def main(args: Array[String]): Unit = {
    case class Entry(min: Int, max: Int, char: Char, password: String)
    def toEntry(entryString: String): Entry = {
      val pattern = "(\\d*)-(\\d*) (\\w): (\\w*)".r
      entryString match {
        case pattern(min, max, char, password) => Entry(min.toInt, max.toInt, char.head, password)
      }
    }
    def isValid(entry: Entry): Boolean = {
      val occurencies = entry.password.count(entry.char == _)
      occurencies >= entry.min && occurencies <= entry.max
    }

    def isValid2(entry: Entry): Boolean = {
      val char     = entry.char
      val atFirst  = char == entry.password.charAt(entry.min - 1)
      val atSecond = char== entry.password.charAt(entry.max - 1)
      (atFirst || atSecond) && (!(atFirst && atSecond))
    }

    println("Puzzle 1")
    val inputFile = fromFile("inputs/puzzle2.input")

    if (inputFile != null) {
      val lines = inputFile.getLines().toList.map(toEntry)
      val sol1 =
        lines.filter(isValid).size
      val sol2 =
        lines.filter(isValid2).size
      println(s"1: $sol1, 2: $sol2")
    } else {
      println("File was null")
    }
  }
}
