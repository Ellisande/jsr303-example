package example.jsr.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.NotBlank;

import example.jsr.validators.AnagramIntegerValidator;
import example.jsr.validators.AnagramStringValidator;
/**
* Anagram is reponsible for validating that the annotated field is an anagram. In order to comply the given field must be the same forwards and backwards. Captialization and 
* whitespace does matter.
* <p> Supports:
* <ul>
*  <li>String</li>
*  <li>Integer</li>
* </ul>
*  
*
**/
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AnagramStringValidator.class, AnagramIntegerValidator.class})
@NotBlank
public @interface Anagram {
    /**
    * Message to be displayed if this field is in violation of the rule.
    **/
    String message() default "Not a ton";

    /**
    * Array of marker classes used to demark which group of validations in which this constraint should be included.
    **/
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
