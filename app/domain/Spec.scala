package domain

import play.api.libs.json.{Format, Json}
import play.api.mvc.{AnyContent, Request}

case class SpecField(
                      target: String, source: String = "",
                      direct: Boolean = true,
                      mapping: Map[String, String] = Map[String, String](),
                      individual: Boolean = true,
                      yearly: Boolean = true,
                      atr: Boolean = true, atlas: Boolean = false,
                      evogenius: Boolean = false, survey: Boolean = false) {

  val origin: String = if (individual) "P" else "HH"

  val household: Boolean = !individual
  val variable: Boolean = !yearly
}

object SpecField {
  implicit val specFieldFormat: Format[SpecField] = Json.format[SpecField]
}

case class Spec(specFields: Seq[SpecField]) {

  def validate(schema: Schema): Boolean = ???

  def forField(field: Field): SpecField =
    specFields.
      find(_.target == field.name).
      getOrElse(SpecField(field.name))

  def featureSelect: String = specFields.map(specField =>
      s"featurename == '${specField.source}' AND origin == '${specField.origin}'"
    ).mkString(" OR ")
}

object Spec {
  implicit val specFormat: Format[Spec] = Json.format[Spec]

  val emptySpec = Spec(Nil)

  def fromRequest(request: Request[AnyContent]): Spec = {
    val rawData = request.body.asFormUrlEncoded.getOrElse(Map[String, Seq[String]]())

    val fieldNames: Seq[String] = rawData.keySet.
      filter(_.startsWith("origin_")).
      map(_.substring("origin_".length)).toSeq

    val specFields = fieldNames.map(fieldName => {
      def extract(prefix: String): String = rawData.get(prefix + "_" + fieldName).flatMap(_.headOption).getOrElse("")

      SpecField(
        target = fieldName,
        source = extract("source"),
        direct = extract("direct").equalsIgnoreCase("on"),
        // FIXME implement

        // mapping: Map[String, String] = Map[String, String](),
        individual = extract("origin").equalsIgnoreCase("Individual"),
        yearly = extract("frequency").equalsIgnoreCase("yearly"),
        atr = extract("src").equalsIgnoreCase("atr"),
        atlas = extract("src").equalsIgnoreCase("atlas"),
        evogenius = extract("src").equalsIgnoreCase("evogenius"),
        survey = extract("src").equalsIgnoreCase("survey")
      )
    })

    Spec(specFields)
  }
}