package domain

case class SpecField(
                      target: String, source: String = "",
                      direct: Boolean = true,
                      mapping: Map[String, String] = Map[String, String](),
                      individual: Boolean = true, household: Boolean = false,
                      yearly: Boolean = true, variable: Boolean = false,
                      atr: Boolean = true, atlas: Boolean = false,
                      evogenius: Boolean = false, survey: Boolean = false)

case class Spec(specFields: Seq[SpecField]) {

  def validate(schema: Schema): Boolean = ???

  def forField(field: Field): SpecField =
    specFields.
      find(_.target == field.name).
      getOrElse(SpecField(field.name))
}

object Spec {
  val emptySpec = Spec(Nil)
}