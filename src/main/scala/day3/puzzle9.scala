package day3

import scala.io.Source.fromFile

object Puzzle9 {
  val preampleLength = 25
  def checkIfValid(index: Int, code: Array[Long]): Boolean = {

    val validCodes = for {
      i <- index - preampleLength to index - 1 
      j <- index - preampleLength to index - 1
      if i != j
    } yield (code(i) + code(j))

    validCodes.toSet
      .contains(code(index))
  }

  def sumsTo (values: Array[Long], sum : Long) : Boolean = {
    values.sum == sum
  }
  def solution1(code: Array[Long]) : Long = {
    val badIndex = for {
      i <- preampleLength  to code.size-1
      if !checkIfValid(i, code)
    } yield (i)
    code(badIndex.head)
  }

  def solution2(weakness: Long, code: Array[Long]) : Long = {
    (2 to code.size)  
    .flatMap (code.sliding(_))
    .find(_.sum == weakness)
    .map(a => a.min + a.max)
    .get
  }

  def main(args: Array[String]): Unit = {
    val code = fromFile("inputs/puzzle9.input")
      .getLines()
      .map(s => s.toLong)
      .toArray

    val weaknes = solution1(code)
    println(solution1(code))
    println(solution2(weaknes, code))

  }
}
