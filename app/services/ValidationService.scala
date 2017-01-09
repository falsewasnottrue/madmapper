package services

import domain._

class ValidationService {

  // FIXME implement
  def validate(schema: Schema, spec: Spec): ValidationResult =
    new ValidationResult(Map[String, String]())
}
