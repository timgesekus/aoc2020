package day3

import scala.io.Source.fromFile

object Puzzle4 {
  def main(args: Array[String]): Unit = {

    def joinLines(lines: List[String]) : (String, List[Map[String,String]]) = {
      lines match {
        case head :: next => head match {
          case ""  => {
            val rest =  joinLines(next)
            val map = (parseKV (rest._1) :: rest._2)
            ("", map)
          }
            case line =>{ 
            val rest = joinLines(next)
            (line + " " + rest._1, rest._2)
          }
        }
        case Nil => ("", Nil)
      }      
    }
    def toPair(pair: String) : (String, String) = {
        val elements = pair.split(":")
        elements(0) -> elements(1)
    }
    
    def parseKV(line: String) : Map[String, String] = {
      line.trim.split(" ").map(toPair).toMap
    } 
 
    def isValidPassport(passport: Map[String,String]) : Boolean = {
      val requiredKeys = "ecl":: "pid":: "eyr":: "hcl":: "byr":: "iyr":: "hgt" ::  Nil
      val hasRequiredKeys = requiredKeys.foldLeft(true) { (a,b) => 
        a && isValidKey(b,passport.getOrElse(b,"no"))
      }
      if (hasRequiredKeys) {
        passport.size match {
          case 7 => true
          case 8 => passport.contains("cid")
          case _ => false
        }
      } else {
        false
      }
    }

    def isValidKey (key: String, value: String) : Boolean = {
      key match {
        case "byr" if ("^\\d{4}$".r matches value) => inRange(value.toInt, 1920, 2002)
        case "iyr" if ("^\\d{4}$".r matches value) => inRange(value.toInt, 2010, 2020)
        case "eyr" if ("^\\d{4}$".r matches value) => inRange(value.toInt, 2020, 2030)
        case "hgt" if ("^\\d+cm$".r matches value) => inRange(value.split("cm")(0).toInt, 150, 193)
        case "hgt" if ("^\\d+in$".r matches value) => inRange(value.split("in")(0).toInt, 59, 76)
        case "hcl" if ("^#[0-9a-f]{6}$").r matches value => true
        case "ecl" if ("^amb|blu|brn|gry|grn|hzl|oth$").r matches value => true
        case "pid" if "^\\d{9}$".r matches value => true
        case "cid" => true
        case _ => false
      }
    }

    def inRange(value: Int, lower: Int, upper: Int) : Boolean = {
      value >= lower && value <= upper
    }
    println("Puzzle 4")
    val inputFile = fromFile("inputs/puzzle4.input")
    val joinedLines = joinLines(inputFile.getLines().toList)
    val passports = parseKV (joinedLines._1) :: joinedLines._2 
    val validLines = passports.filter(isValidPassport)
    //validLines.foreach(println)
    println (validLines.size)
  }
}
