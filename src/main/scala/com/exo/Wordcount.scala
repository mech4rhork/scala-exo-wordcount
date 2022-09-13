package com.exo

import java.io.{BufferedWriter, File, FileNotFoundException, FileWriter, OutputStreamWriter}
import scala.collection.immutable.ListMap
import scala.io.Source
import scala.util.{Failure, Random, Success, Try, Using}

object Wordcount {

  val dataWarehouseFolder: String = "target" + "/" + "data-warehouse"
  val booksFolder: String = dataWarehouseFolder + "/" + "books"
  val countFolder: String = dataWarehouseFolder + "/" + "count"

  def main(args: Array[String]): Unit = {

    // Initializing local directories
    List(
      dataWarehouseFolder,
      booksFolder,
      countFolder
    ).foreach { folder =>
      Try(new File(folder).mkdirs) match {
        case Success(_) =>
        case Failure(t) =>
          println(s"WARN: local folder creation failed: $folder")
          printError(t)
      }
    }

    // Downloading book
    val bookId: Int = Random.between(11, 40)
    val dlBookTry: Try[Unit] = downloadBook(bookId)
    dlBookTry match {
      case Success(value) => println(s"INFO: Successfully downloaded book #$bookId")
      case Failure(e: FileNotFoundException) =>
        println(s"WARN: local data-warehouse and/or remote book not found")
        printError(e)
      case Failure(t) => printError(t)
    }

    // Loading book
    val bookLines: List[String] = loadDownloadedBook(bookId)

    // Computing word count
    val wordcount: Map[String, Int] = doWordcount(bookLines.mkString)

    // Writing word count
    val outputFile = s"$countFolder/$bookId-0.count"
    val wordcount_sorted = ListMap(wordcount.toSeq.sortWith(_._2 > _._2): _*)
    println(s"INFO: Saving the count to $outputFile)")
    writeFile(outputFile, wordcount_sorted.map(x => x._1 + ";" + x._2 + "\n").toList)
  }

  /**
   * Based on https://data-flair.training/forums/topic/write-a-word-count-program-using-scala-language-dont-use-spark-core-or-sql
   */
  def doWordcount(text: String): Map[String, Int] = {
    println(s"INFO: Doing the counting...")
    val wordAndOneTuples: Array[(String, Int)] = text.split("\\.").flatMap { sentence =>
      sentence
        .replaceAll("""[\p{Punct}&&[^.]]""", "") // Removing punctuation
        .trim
        .split(" ") // Splitting sentence into words
        .map(word => (word, 1))
    }
    val wordAndOneTuples_groupedByWord: Map[String, Array[(String, Int)]] = wordAndOneTuples.groupBy(x => x._1)
    val wordcount: Map[String, Int] = wordAndOneTuples_groupedByWord.view.mapValues(wordAndOneTuplesArray =>
      wordAndOneTuplesArray.map(_._2).sum
    ).toMap
    wordcount
  }

  def loadDownloadedBook(bookId: Int): List[String] = {
    val localTxtFile = s"$booksFolder/$bookId-0.txt"
    val lines = Using(Source.fromFile(localTxtFile)) { source => source.getLines().toList } getOrElse Nil
    println(s"INFO: Loaded ${lines.length} lines from book #$bookId ($localTxtFile)")
    lines
  }

  /**
   * Based on https://www.anycodings.com/1questions/4782836/how-to-download-and-save-a-file-from-the-internet-using-scala
   */
  def downloadBook(bookId: Int): Try[Unit] = Try {
    val bookTxtUrl: String = s"https://www.gutenberg.org/files/$bookId/$bookId-0.txt"
    println(s"INFO: Trying to download book from $bookTxtUrl")
    val src = scala.io.Source.fromURL(bookTxtUrl)
    val out = new java.io.FileWriter(s"$booksFolder/$bookId-0.txt")
    out.write(src.mkString)
    out.close()
  }

  def printError(t: Throwable): Unit = println("ERROR: " + t + "\n" + t.getStackTrace.map("\t" + _).mkString("\n"))

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
