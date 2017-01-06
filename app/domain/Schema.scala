package domain

import play.api.libs.json.JsObject

case class Schema(raw: JsObject) {

  override def toString = raw.toString
}
