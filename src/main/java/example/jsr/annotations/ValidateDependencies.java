package example.jsr.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import example.jsr.validators.ValidateDependenciesValidator;

/**
 * Scan through the current class and enforces cross-field dependencies based on
 * the @{@link DependencyWith} and the @{@link DependentField} annotations.
 * 
 * @author m91s
 * 
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { ValidateDependenciesValidator.class })
public @interface ValidateDependencies {
	/**
	 * Message to return if this constraint is violated
	 * 
	 * @return
	 */
	String message() default "Your email address is lame";

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
