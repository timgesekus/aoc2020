package day3

import scala.io.Source.fromFile
import scala.collection.immutable.Map

object Puzzle7 {
  case class Rule(color: String, containedBags: Map[String, Int])
  type RuleBook = Map[String, Rule]
  object Rule {
    def apply(rule: String): Rule = {
      val color = "(.*) bags contain".r.findFirstMatchIn(rule).map(_.group(1)).get
      val containedColors = "(\\d+) (\\w+ \\w+) bag".r
        .findAllMatchIn(rule)
        .map(matches => (matches.group(2), matches.group(1).toInt))
      Rule(color, Map.from(containedColors))
    }
  }

  def canContainShinyGold(color: String, rules: RuleBook): Boolean =
    if (rules(color).containedBags.contains("shiny gold")) {
      return true
    } else {
      rules(color).containedBags.exists(bag => canContainShinyGold(bag._1, rules))
    }

  def solution1(rules: RuleBook): Int =
    rules.keySet
      .filter(color => canContainShinyGold(color, rules))
      .size

  def solution2(rules: RuleBook, color: String): Int =
    if (rules(color).containedBags.isEmpty) {
      1
    } else {
      rules(color).containedBags.foldLeft(1)((a, b) => a + solution2(rules, b._1) * b._2)
    }

  def main(args: Array[String]): Unit = {
    val rules = fromFile("inputs/puzzle7.input")
      .getLines()
      .map(Rule(_))
      .map(r => (r.color -> r))
    val rulesMap = Map.from(rules)
    println(solution1(rulesMap))
    println(solution2(rulesMap, "shiny gold") - 1)

  }
}
