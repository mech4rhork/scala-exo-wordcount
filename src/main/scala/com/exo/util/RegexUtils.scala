package com.exo.util

object RegexUtils {

  lazy val regexPunct = """[\p{Punct}”“—]"""
  lazy val regexSentenceEndLineBreak = """(\.\s+|[\r\n]+)"""
  lazy val regexSpaces = """\s+"""
}
