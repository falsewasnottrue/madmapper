package services

import java.io.{File, FileWriter}

import domain.Spec
import play.api.libs.json.Json

class SpecService {

  private val location = "/Users/rasmus/Documents/development/madmapperfiles"

  def list: Seq[String] = new File(location).listFiles.toSeq.map(_.getName)

  def load(name: String): Spec = ???

  def save(name: String, spec: Spec): Unit = {
    val rawData = Json.toJson(spec).toString()
    new FileWriter(filename(name), false).write(rawData)
  }

  private def filename(name: String) = s"$location/$name"
}
