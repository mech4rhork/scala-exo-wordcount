package com.exo.util

import java.io.{BufferedWriter, File, FileWriter}

object IOUtils {

  def printError(t: Throwable): Unit = println("ERROR: " + t + "\n" + t.getStackTrace.map("\t" + _).mkString("\n"))

  def readDataFromUrl(url: String): String = {
    val src = scala.io.Source.fromURL(url)
    src.mkString
  }

  /**
   * write a `Seq[String]` to the `filename`.
   * Source : https://alvinalexander.com/scala/how-to-write-text-files-in-scala-printwriter-filewriter
   */
  def writeFile(filename: String, lines: Seq[String]): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    for (line <- lines) {
      bw.write(line)
    }
    bw.close()
  }

}
