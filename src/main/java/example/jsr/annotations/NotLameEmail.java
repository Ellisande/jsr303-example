package example.jsr.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Enforces that the current field is not a lame email by ensuring that it ends
 * in Gmail.com.
 * 
 * @author m91s
 * 
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@NotEmpty
@Pattern(regexp = ".*@gmail.com")
@Email
@ReportAsSingleViolation
public @interface NotLameEmail {
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
