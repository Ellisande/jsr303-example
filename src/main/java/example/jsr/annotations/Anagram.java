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
 * Validates that the current field is an anagram (the same forwards and
 * backwards). White space and capitalization do count.
 * <p>
 * Supported Types:
 * <ul>
 * <li>String</li>
 * <li>Integer</li>
 * </ul>
 * 
 * @author m91s
 * 
 */
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { AnagramStringValidator.class, AnagramIntegerValidator.class })
@NotBlank
public @interface Anagram {
	/**
	 * Message to return if this constraint is violated
	 * 
	 * @return
	 */
	String message() default "Not a ton";

	/**
	 * Set of marker classes which represent the validation groups of which this
	 * validation is a part.
	 * 
	 * @return
	 */
	Class<?>[] groups() default {};

	/**
	 * Optional payload to provide metadata to the validation.
	 * 
	 * @return
	 */
	Class<? extends Payload>[] payload() default {};
}
