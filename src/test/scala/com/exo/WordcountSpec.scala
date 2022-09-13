package com.exo

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WordcountSpec extends AnyFlatSpec with Matchers {

  "doWordcount" should "count words" in {

    // GIVEN
    val inputTextLines: String = List(
      // TODO
    ).mkString

    // WHEN
    val actualRes = // TODO
    val expectedRes: Map[String, Int] = // TODO

    // THEN
    // TODO
  }
}
