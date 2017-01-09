package services

import domain._

class ValidationService {

  def validate(schema: Schema, spec: Spec): ValidationResult = {
    findRequiredMissing(schema, spec)
  }

  private def findRequiredMissing(schema: Schema, spec: Spec): ValidationResult = {
    val requiredMissing = for {
      field <- schema.fields if field.required
      specField = spec.forField(field)
      if specField.isEmpty || specField.get.source == ""
    } yield field.name

    val errors = requiredMissing.map(fieldName => (fieldName, ValidationResult.Required)).toMap
    ValidationResult(errors)
  }

}
