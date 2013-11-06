package example.jsr.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import example.jsr.validators.PasswordAndEmailDoNotMatchValidator;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PasswordAndEmailDoNotMatchValidator.class})
public @interface PasswordAndEmailDoNotMatch {
    String message() default "Your email address is lame";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String path();
}
