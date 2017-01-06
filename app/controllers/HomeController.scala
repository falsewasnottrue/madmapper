package controllers

import javax.inject._

import domain.Schema
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.SchemaService

@Singleton
class HomeController @Inject() extends Controller {

  val schemaService = new SchemaService

  def index = Action {
    val schema: Schema = schemaService.loadSchema

    // anreichern mit spez
    // val fields = schema.fields.map(_.\\("name").toString).mkString(",")

    Ok(views.html.schema(schema))
  }

}
