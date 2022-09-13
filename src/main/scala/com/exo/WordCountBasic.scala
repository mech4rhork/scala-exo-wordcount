package com.exo

import com.exo.util.RegexUtils

trait WordCountBasic extends WordCount {

  def doWordcount(text: String): Map[String, Int] = {
    println(s"INFO: Doing the counting...")
    // TODO
    Map("toto" -> 1)
  }
}
