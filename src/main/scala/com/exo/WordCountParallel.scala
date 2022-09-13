package com.exo

import com.exo.util.RegexUtils

import scala.collection.parallel.CollectionConverters._
import scala.util.Random

trait WordCountParallel extends WordCount {

  val nbPartitions: Int = 4

  def doWordcount(text: String): Map[String, Int] = {
    println(s"INFO: Doing the counting...")
    // TODO
    Map("toto" -> 1)
  }
}
