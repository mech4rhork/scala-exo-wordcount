package com.exo

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WordcountSpec extends AnyFlatSpec with Matchers {

  "doWordcount" should "count words" in {

    // GIVEN
    val inputTextLines: String = List(
      "one two, one two.",
      "just three.",
      "one mic check."
    ).mkString

    // WHEN
    val actualRes = Wordcount.doWordcount(inputTextLines)
    val expectedRes: Map[String, Int] = Map(
      "one" -> 3,
      "two" -> 2,
      "three" -> 1,
      "mic" -> 1,
      "check" -> 1,
      "just" -> 1
    )

    actualRes shouldBe expectedRes
  }
}
