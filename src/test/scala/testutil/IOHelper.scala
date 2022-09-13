package testutil

import java.nio.file.{Files, Paths}
import java.util.Objects

object IOHelper {

  def readResourceAsString(resourcePath: String): String = {
    val url = getClass.getResource(resourcePath)
    val uri = Objects.requireNonNull(url).toURI
    val path = Paths.get(uri)
    Files.readString(path)
  }
}
