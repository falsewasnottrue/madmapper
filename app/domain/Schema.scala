package domain

import play.api.libs.json.{JsArray, JsObject, JsValue}

trait FieldType
case object StringFieldType extends FieldType
case object IntFieldType extends FieldType

case class Field(name: String, typ: String) {
  def required: Boolean = !typ.contains("null")
}

case class Schema(raw: JsValue) {

  override def toString = raw.toString

  def fields: Seq[Field] = (raw \\ "fields").flatMap {
    case fs: JsArray => fs.value
  } map {
    case obj: JsObject => Field(
      (obj \ "name").asOpt[String].getOrElse(""),
      (obj \ "type").asOpt[String].getOrElse("")
    )
  }
}
