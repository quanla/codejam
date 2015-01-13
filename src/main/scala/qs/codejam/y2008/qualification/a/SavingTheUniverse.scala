package qs.codejam.y2008.qualification.a

import qs.codejam.CodeJam
import qj.util.Cols

/**
 * Created by quan on 1/10/15.
 */
object SavingTheUniverse {

  def main(args: Array[String]) {
  //    val resName: String = "A-large-practice.in"
  //    val resName: String = "A-small-practice.in"
    val resName = "sample.in"


    CodeJam.solver(resName, lineF => {
      val seCount = lineF() toInt
      val ses = (for (i <- 1 to seCount) yield lineF()) toList

      val wCount = lineF() toInt
      val words = (for (i <- 1 to wCount) yield lineF()) toList

      () => {"" + howManySwitches(words, ses)}

    })
    .solve()

  }

  private def howFarCanGo(se: String, words: List[String]): Int = {
    val indexOf: Int = words.indexOf(se)
    if (indexOf == -1) {
      return words.size
    }
    indexOf
  }

  def howManySwitches(words: List[String], ses: List[String]): Int = {
    if (words.size == 0) {
      return 0
    }
    // ((se) => howFarCanGo(se, words))
    val goSize: Int = ses.map(se => howFarCanGo(se, words)).max
    if (goSize == words.size) {
      return 0
    }

    1 + howManySwitches(words.slice(goSize, words.size), ses)
  }

}
