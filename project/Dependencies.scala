import sbt.*

object Dependencies {

  lazy val commonDeps = List(
    "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
  )
  lazy val testDeps = List(
    "org.scalatest" %% "scalatest" % "3.2.15"
  )


}
