package domain

class ValidationResult(errors: Map[String, String]) {

  def valid: Boolean = errors.isEmpty

  def forField(fieldId: String): Option[String] = errors.get(fieldId)
}
