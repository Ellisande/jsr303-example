package example.jsr.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that represents a field that has a field which is dependent on it.
 * <p>
 * <strong>Note:</strong> This is for independent fields, use @DependentField
 * for fields which have a dependency on another field.
 * 
 * @author m91s
 * 
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DependencyWith {
	/**
	 * Key which correlates between this field and its dependencies.
	 * 
	 * @return
	 */
	String key();
}
