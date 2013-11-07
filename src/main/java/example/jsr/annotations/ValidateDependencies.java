package example.jsr.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import example.jsr.validators.ValidateDependenciesValidator;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ValidateDependenciesValidator.class})
public @interface ValidateDependencies {
    String message() default "Your email address is lame";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
