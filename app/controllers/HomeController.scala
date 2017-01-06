package controllers

import javax.inject._

import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.SchemaService

@Singleton
class HomeController @Inject() extends Controller {

  val schemaService = new SchemaService

  def index = Action {
    val schema = schemaService.loadSchema
    Ok(views.html.index(schema.toString))
  }

}
