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

  val isMapped: Boolean = !direct && mapping.keySet.size > 0
  val household: Boolean = !individual
  val variable: Boolean = !yearly
}

object SpecField {
  implicit val specFieldFormat: Format[SpecField] = Json.format[SpecField]
}

case class Spec(specFields: Seq[SpecField]) {

  def forField(field: Field): Option[SpecField] = specFields.find(_.target == field.name)

  def forFieldM(field: Field): SpecField =
    specFields.
      find(_.target == field.name).
      getOrElse(SpecField(field.name))

  def featureSelect: String = specFields.map(specField =>
      s"featurename == '${specField.source}' AND origin == '${specField.origin}'"
    ).mkString(" OR ")

  def renamedFields: Map[String, String] =
    specFields.map(field => (field.source, field.target)).toMap

  def mappedFields: Map[String, Map[String, String]] = {
    specFields.filter(_.isMapped).map(field => (field.source, field.mapping)).toMap
  }

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

      def extractMapping: Map[String, String] = {
        val prefix = "mapping_" + fieldName + "_"
        val keys = rawData.toList.
          filter { case (k, _) => k.startsWith(prefix + "key_")}.
          map { case (k, _) => k.substring((prefix + "key_").length)}

        keys.map(key =>
          (rawData(prefix + "key_" + key).headOption.get,
            rawData(prefix + "val_" + key).headOption.get)
        ).toMap
      }

      SpecField(
        target = fieldName,
        source = extract("source"),
        direct = extract("direct").equalsIgnoreCase("on"),
        mapping = extractMapping,
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