package controllers

import javax.inject._

import domain.{Spec}
import play.api.mvc._
import services.{SchemaService, SpecService}

@Singleton
class SpecController @Inject() extends Controller {

  private val schemaService = new SchemaService
  private val schema = schemaService.loadSchema

  private val specService = new SpecService

  def list = Action { implicit request =>
    Ok(views.html.specs(specService.list)).flashing()
  }

  def newSpec = Action { implicit request =>
    val name = request.body.asFormUrlEncoded.getOrElse(Map[String, Seq[String]]())("specName").head
    Ok(views.html.spec(name, schema, Spec.emptySpec))
  }

  def load(specName: String) = Action { implicit request =>
    val spec = specService.load(specName)
    Ok(views.html.spec(specName, schema, spec))
  }

  def save(specName: String) = Action { implicit request =>
    val spec = Spec.fromRequest(request)
    specService.save(specName, spec)

    Ok(views.html.specs(specService.list)).flashing("message" -> s"$specName erfolgreich gesichert")
  }

  def generate(specName: String) = Action { implicit request =>
    val spec = specService.load(specName)
    Ok(views.txt.dep_schema_mapping(spec))
  }

  def validate(specName: String) = Action {
    NotImplemented
  }
}
