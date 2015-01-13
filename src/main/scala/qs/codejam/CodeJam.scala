package qs.codejam

import java.io.InputStream
import qj.util.{LanguageUtil, LangUtil4}
import scala.io.Source
import scala.collection.mutable.ListBuffer

/**
* Created by quan on 1/10/15.
*/
object CodeJam {
  def solver(resName: String, readAndSolve: ( () => String ) => ( () => String )) = {
    val callerClass: Class[_] = LangUtil4.getTraceClass(1)

    Solver(resName, callerClass, null, normalLines(readAndSolve))

  }

//    : (() => String) => Tuple2[Int, ( (() => String) => (() => String) )]

  def normalLines(readAndSolve: ( () => String ) => ( () => String )): (() => String) => (Int, ((() => String) => (() => String))) = {
    (lineF) => {
      val caseCount: Int = Integer.parseInt(lineF())
      (caseCount, readAndSolve)
    }
  }

}

case class Solver(resName: String, mainClass: Class[_], in: InputStream, preReadReadAndSolve: (() => String) => (Int, ((() => String) => (() => String)))) {


  def solve(caseNum: Int) = {

    var caseCounter: Int = 0

    eachCase((inputLines, solver) => {
      caseCounter+=1
      if (caseCounter != caseNum) {
        false
      } else {
        println("Input of case #" + caseCounter + ": ")
        for (inputLine <- inputLines) {
          println(inputLine)
        }

        println("Output of case #" + caseCounter + ": " + solver())

        true
      }

    })
  }

  case class Cache(f: () => String) {
    var list = new ListBuffer[String]
    val stub: () => String = () => {
      val ret = f()
      list += ret
      ret
    }

    def lines = list.result()
  }

  def eachCase( checker: (List[String], () => String) => Boolean ): Unit = {
    if (resName != null) {
      println("Reading input: " + resName)
    }

    val br = Source.fromInputStream( if (in != null) in else mainClass.getResourceAsStream(resName) ).bufferedReader()

    try {
      val lineF = () => br.readLine()

      val starting = preReadReadAndSolve(lineF)

      val caseNumTotal = starting._1
      val readAndSolve = starting._2

      for ( i <- 1 to caseNumTotal) {
        val cachedLineF = Cache(lineF)
        val caseSolver: () => String = readAndSolve(cachedLineF.stub)

        if (caseSolver == null || checker(cachedLineF.lines, caseSolver)) {
          return
        }

      }
    }
    finally {
      br.close()
    }


  }

  def solve() = {
    val start = System.currentTimeMillis()
    val output = (obj: String) => println(obj)

    var caseNum = 0
    eachCase((inputLines, solveF) => {
      caseNum += 1

      val result = solveF()

      output("Case #" + caseNum + ":" + (if (result != null && result.startsWith("\n")) result else " " + result))

      false
    })

    println("Done in " + LanguageUtil.translateMillis(System.currentTimeMillis - start))
  }
}