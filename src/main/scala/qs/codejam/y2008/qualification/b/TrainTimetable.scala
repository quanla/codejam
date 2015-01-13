package qs.codejam.y2008.qualification.b

import qs.codejam.CodeJam
import scala.collection.mutable.ListBuffer
import java.text.SimpleDateFormat

object TrainTimetable {
  def main(args: Array[String]) {
    val resName = "sample.in"

    CodeJam.solver(resName, readAndSolve = (lineF) => {
      val turnTime: Long = lineF().toInt * 1000 * 60
      val split = lineF().split(" ")
      val na: Int = split(0).toInt
      val nb: Int = split(1).toInt

      val needAtA = new ListBuffer[Long]
      val readyAtB = new ListBuffer[Long]
      val needAtB = new ListBuffer[Long]
      val readyAtA = new ListBuffer[Long]

      val df = new SimpleDateFormat("HH:mm")

      for (i <- 1 to na) {
        val timeSplit = lineF().split(" ")

        needAtA += df.parse(timeSplit(0)).getTime
        readyAtB += df.parse(timeSplit(1)).getTime + turnTime
      }
      for (i <- 1 to nb) {
        val timeSplit = lineF().split(" ")

        needAtB += df.parse(timeSplit(0)).getTime
        readyAtA += df.parse(timeSplit(1)).getTime + turnTime
      }

      () => solve(readyAtA.result(), needAtA.result()) + " " + solve(readyAtB.result(), needAtB.result())
    })
    .solve()
  }

  private def solve(readys: List[Long], needs: List[Long]): Int = {
    if (needs.size == 0) {
      return 0
    }

    val ry = readys.sorted.reverse
    var nd = needs.sorted.reverse

    for (ready <- ry) {
      val need = nd.head
      if (ready <= need) {
        nd = nd.tail
        if (nd.size == 0) {
          return 0
        }
      }
    }
    nd.size
  }
}