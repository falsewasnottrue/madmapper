package controllers

import javax.inject._

import domain.{Schema, Spec, SpecField}
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.SchemaService

@Singleton
class SchemaController @Inject() extends Controller {

  val schemaService = new SchemaService
  val schema: Schema = schemaService.loadSchema

  def index = Action {
    // TODO some mock spec
    val spec = Spec(Seq(
      SpecField("gxl_agegroup_1", "Alter_Usage_IPAN_2008",
        direct = false,
        mapping = Map(
        " 0 bis 2 Jahre" -> "01",
        " 3 bis 5 Jahre" -> "02"
      ))
    ))

    Ok(views.html.schema(schema, spec))
  }

  def generate = Action { implicit request =>
    val spec = Spec.fromRequest(request)
    Ok(views.txt.dep_schema_mapping(spec))
  }
}
