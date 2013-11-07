package example.jsr.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DependsOn {
	String key();
	String violationMesssage() default "Required when %s is present";
	DependencyRules rule() default DependencyRules.REQUIRED_WHEN_PROVIDER_PRESENT;
	
	public enum DependencyRules{
		REQUIRED_WHEN_PROVIDER_PRESENT,OPTIONAL_WHEN_PROVIDER_NOT_PRESENT,REJECT_IF_PROVIDER_PRESENT;
	}
}
