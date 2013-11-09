package example.jsr.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a dependency with another field based on the key.
 * 
 * @author m91s
 * 
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DependentField {
	/**
	 * Key which correlates this dependent field with the independent field.
	 * 
	 * @return
	 */
	String key();

	/**
	 * Message to report if this dependency is violated.
	 * 
	 * @return
	 */
	String violationMesssage() default "Required when %s is present";

	/**
	 * Defines the dependency rule to use.
	 * <p>
	 * Rules:
	 * <ul>
	 * <li><strong>Required When Provider is Present:</strong> This field is
	 * required when the correlated field is present.</li>
	 * <li><strong>Cannot Be Present If Provider Not Present:</strong> This
	 * field cannot be present if the correlated field is null.</li>
	 * </ul>
	 * @return
	 */
	DependencyRules rule() default DependencyRules.REQUIRED_WHEN_PROVIDER_IS_PRESENT;

	public enum DependencyRules {
		REQUIRED_WHEN_PROVIDER_IS_PRESENT, CANNOT_BE_PRESENT_IF_PROVIDER_NOT_PRESENT;
	}
}
