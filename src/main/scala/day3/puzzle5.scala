package day3

import scala.io.Source.fromFile

object Puzzle5 {
  case class Seat(row: Int, column: Int, id: Int) {}

  object Seat {
    val lower = """[FL]""".r
    val upper = """[BR]""".r
    def apply(scan: String): Seat = {
      val splittedScan = scan.splitAt(7)
      //  println(splittedScan._2)
      val row    = walkTree(splittedScan._1.toList, 1, 128) - 1
      val column = walkTree(splittedScan._2.toList, 1, 8) - 1
      Seat(row, column, row * 8 + column)

    }
    def walkTree(scan: List[Char], lowerBound: Int, upperBound: Int): Int =
      //println(s"$lowerBound:$upperBound")
      scan match {
        case lower() :: xs => walkTree(xs, lowerBound, lowerBound + ((upperBound - lowerBound) / 2))
        case upper() :: xs => walkTree(xs, ((upperBound - lowerBound) / 2) + lowerBound + 1, upperBound)
        case Nil           => lowerBound
        case _             => 0
      }
  }

  def solution1(seats: List[Seat]): Seat =
    seats.foldLeft(Seat(0, 0, 0))((maxSeat, newSeat) => if (maxSeat.id > newSeat.id) maxSeat else newSeat)
  
    def solution2(seats: List[Seat]) : Int = {
    val surroundingSeats = seats.map(_.id).sorted.sliding(2).filter(s => (s(0) + 2== s(1)) )
    surroundingSeats.next()(0)+1
  } 
  def main(args: Array[String]): Unit = {
    val inputFile = fromFile("inputs/puzzle5.input")
    val seats = inputFile
      .getLines()
      .toList
      .map(Seat(_))

    println(solution1(seats))
    println(solution2(seats))

  }
}
