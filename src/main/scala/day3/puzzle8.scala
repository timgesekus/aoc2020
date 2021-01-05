package day3

import scala.io.Source.fromFile
import scala.collection.immutable.Set
import scala.annotation.tailrec

object Puzzle8 {
  type Programm = Array[Instruction]
  case class Instruction(op: String, argument: Int)
  case class State(acc: Int, pc: Int, visitedInstructions: Set[Int])
  object Instruction {
    def apply(str: String): Instruction = {
      val statementPattern = "^(\\S{3}) ([+-]\\d{1,})".r
      str match {
        case statementPattern(operation, argument) => Instruction(operation, argument.toInt)
        case _                                     => Instruction("", 0)
      }
    }
  }

  def executeInstruction(instruction: Instruction, oldState: State): State = {
    val state = oldState.copy(visitedInstructions = oldState.visitedInstructions + oldState.pc)
    instruction.op match {
      case "nop" => state.copy(pc = state.pc + 1)
      case "acc" => state.copy(acc = state.acc + instruction.argument, pc = state.pc + 1)
      case "jmp" => state.copy(pc = state.pc + instruction.argument)
    }
  }

  @tailrec
  def run(programm: Programm, state: State): (Boolean, Int) =
    if (state.visitedInstructions.contains(state.pc)) {
      (false, state.acc)
    } else if (state.pc == programm.size) {
      (true, state.acc)
    } else {
      val newState = executeInstruction(programm(state.pc), state)
      run(programm, newState)
    }

  def solution1(programm: Programm): Int =
    run(programm, State(0, 0, Set()))._2

  def solution2(programm: Programm): Int = LazyList.range(0,programm.size - 1)
    .map(programm.updated(_, Instruction("nop", 0)))
    .map(run(_, State(0, 0, Set())))
    .dropWhile( ! _._1)
    .head
    ._2

  def main(args: Array[String]): Unit = {
    val programm = fromFile("inputs/puzzle8.input")
      .getLines()
      .map(Instruction(_))
      .toArray

    val sol1 = solution1(programm)
    val sol2 = solution2(programm)

    println(s"Solution $sol1")
    println(s"Solution $sol2")

  }
}
