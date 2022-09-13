package com.exo

import com.exo.util.IOUtils

import java.io.{File, FileNotFoundException}
import scala.collection.immutable.ListMap
import scala.io.Source
import scala.util._

object Main extends WordCountBasic {

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
          IOUtils.printError(t)
      }
    }

    // Downloading book
    val bookId: Int = Random.between(11, 40)
    val dlBookTry: Try[Unit] = downloadBook(bookId)
    dlBookTry match {
      case Success(_) =>
        println(s"INFO: Successfully downloaded book #$bookId")
        // Loading book
        val bookLines: String = loadDownloadedBook(bookId)
        // Computing word count
        val wordcount: Map[String, Int] = doWordcount(bookLines)
        // Writing word count
        val outputFile = s"$countFolder/$bookId-0.count"
        val wordcount_sorted = ListMap(wordcount.toSeq.sortWith(_._2 > _._2): _*)
        println(s"INFO: Saving the count to $outputFile)")
        IOUtils.writeFile(outputFile, wordcount_sorted.map(x => x._1 + ";" + x._2 + "\n").toList)
      case Failure(t: FileNotFoundException) =>
        println(s"ERROR: Failed to download book to local data-warehouse. CAUSE=[${t.getClass}: ${t.getMessage}]")
      case Failure(t) =>
        IOUtils.printError(t)
    }
  }

  def downloadBook(bookId: Int): Try[Unit] = Try {
    val bookTxtUrl: String = s"https://www.gutenberg.org/files/$bookId/$bookId-0.txt"
    println(s"INFO: Trying to download book from $bookTxtUrl")
    val out = new java.io.FileWriter(s"$booksFolder/$bookId-0.txt")
    val textData = IOUtils.readDataFromUrl(bookTxtUrl)
    out.write(textData)
    out.close()
  }

  def loadDownloadedBook(bookId: Int): String = {
    val localTxtFile = s"$booksFolder/$bookId-0.txt"
    val lines = Using(Source.fromFile(localTxtFile)) { source => source.getLines().toList } getOrElse Nil
    println(s"INFO: Loaded ${lines.length} lines from book #$bookId ($localTxtFile)")
    lines.mkString("\n")
  }

}
