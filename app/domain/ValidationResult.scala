package domain

case class ValidationResult(errors: Map[String, String]) {

  def valid: Boolean = errors.isEmpty

  def forField(fieldId: String): Option[String] = errors.get(fieldId)

  def +(other: ValidationResult): ValidationResult =
    ValidationResult(errors.++:(other.errors))
}

object ValidationResult {
  val Required = "Required field"

  // TODO
  val Ambiguous = "Ambiguous mapping"
}
