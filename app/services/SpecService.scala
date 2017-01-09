package services


import java.io.{BufferedWriter, File, FileWriter}

import domain.Spec
import play.api.libs.json.Json

import scala.io.Source

class SpecService {

  private val location = "/Users/rasmus/Documents/development/madmapperfiles"

  def list: Seq[String] = new File(location).listFiles.toSeq.map(_.getName)

  def load(name: String): Spec = {
    val rawData = Json.parse(Source.fromFile(filename(name)).mkString)
    val parsed = Json.fromJson[Spec](rawData)
    parsed.asOpt.get
  }

  def save(name: String, spec: Spec): Unit = {
    val rawData = Json.toJson(spec).toString()
    println(rawData)
    println(rawData.length)

    val file = new File(filename(name))
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(rawData)
    bw.close()
  }

  private def filename(name: String) = s"$location/$name"
}
