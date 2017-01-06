package services

import domain.Schema
import play.api.libs.json.{JsObject, JsValue, Json}

import scala.io.Source

class SchemaService {

  def loadSchema(): Schema =
    Schema(Json.parse(Source.fromFile("conf/master_data_v01.avsc").mkString).asInstanceOf[JsObject])
}
