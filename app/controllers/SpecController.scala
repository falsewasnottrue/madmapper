package controllers

import javax.inject._

import domain.{Spec, SpecField}
import play.api.mvc._
import services.{SchemaService, SpecService}

@Singleton
class SpecController @Inject() extends Controller {

  private val schemaService = new SchemaService
  private val schema = schemaService.loadSchema

  private val specService = new SpecService

  def list = Action {
    Ok(views.html.specs(specService.list))
  }

  def load(specName: String) = Action {
    // TODO load spec, mock data for now
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
