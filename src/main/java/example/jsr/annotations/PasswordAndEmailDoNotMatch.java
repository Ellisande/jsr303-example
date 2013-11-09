package example.jsr.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import example.jsr.validators.PasswordAndEmailDoNotMatchValidator;

/**
 * Enforces that the password and email address do not match for the SignupForm
 * bean.
 * 
 * @author m91s
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { PasswordAndEmailDoNotMatchValidator.class })
public @interface PasswordAndEmailDoNotMatch {
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

	/**
	 * Path to map the resulting error to on the rendered HTML page.
	 * 
	 * @return
	 */
	String path();
}
