package com.exo

import com.exo.util.RegexUtils
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import testutil.{IOHelper, TimeHelper}

class WordCountTest extends AnyFlatSpec with Matchers {

  "doWordcount" should "count words" in {

    // GIVEN
    val inputTextLines =
      """
        |Scala source code can be compiled to Java bytecode
        |and run on a Java virtual machine (JVM).
        |Scala can also be compiled to JavaScript
        |to run in a browser,
        |or directly to a native executable.
        |""".stripMargin

    // WHEN
    val wcBasic = new WordCountBasic {}
    val wcParallel = new WordCountParallel {}
    val actualResBasic = wcBasic.doWordcount(inputTextLines)
//    val actualResParallel = wcParallel.doWordcount(inputTextLines)
    val expectedRes: Map[String, Int] = Map(
      "a" -> 3,
      "also" -> 1,
      "and" -> 1,
      "be" -> 2,
      "browser" -> 1,
      "bytecode" -> 1,
      "can" -> 2,
      "code" -> 1,
      "compiled" -> 2,
      "directly" -> 1,
      "executable" -> 1,
      "in" -> 1,
      "java" -> 2,
      "javascript" -> 1,
      "jvm" -> 1,
      "machine" -> 1,
      "native" -> 1,
      "on" -> 1,
      "or" -> 1,
      "run" -> 2,
      "scala" -> 2,
      "source" -> 1,
      "to" -> 4,
      "virtual" -> 1
    )

    actualResBasic shouldBe expectedRes
//    actualResParallel shouldBe expectedRes
  }

  "doWordcount" should "count words from book" in {

    // GIVEN
    val inputTextLines = IOHelper.readResourceAsString("/books/33-0.txt")

    // WHEN
    val wcBasic = new WordCountBasic {}
    val wcParallel = new WordCountParallel {}
    val actualResBasic = TimeHelper.time("basic", wcBasic.doWordcount(inputTextLines))
//    val actualResParallel = TimeHelper.time("parallel", wcParallel.doWordcount(inputTextLines))
    val expectedRes: Map[String, Int] = IOHelper.readResourceAsString("/count/33-0.count")
      .split(RegexUtils.regexSentenceEndLineBreak)
      .map { x =>
        val pair = x.split(";")
        pair(0) -> pair(1).toInt
      }.toList.toMap

    actualResBasic shouldBe expectedRes
//    actualResParallel shouldBe expectedRes
  }
}
