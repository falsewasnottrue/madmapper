package services

import java.io.File

import domain.Spec

class SpecService {

  private val location = "/Users/rasmus/Documents/development/madmapperfiles"

  def list: Seq[String] = new File(location).listFiles.toSeq.map(_.getName)

  def load(name: String): Spec = ???

  def save(spec: Spec): Unit = ???

}
