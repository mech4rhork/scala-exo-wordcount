package testutil

object TimeHelper {

  // Source : http://biercoff.com/easily-measuring-code-execution-time-in-scala/
  def time[R](label: String, block: => R): R = {
    val t0 = System.nanoTime()
    val result = block // call-by-name
    val t1 = System.nanoTime()
    println(s"[ $label ] Elapsed time: ${t1 - t0}ns")
    result
  }
}
