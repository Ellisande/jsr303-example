package example.sf.jsr.jsr_example.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.NotBlank;

import example.sf.jsr.jsr_example.validators.AnagramIntegerValidator;
import example.sf.jsr.jsr_example.validators.AnagramStringValidator;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AnagramStringValidator.class, AnagramIntegerValidator.class})
@NotBlank
public @interface Anagram {
    String message() default "Not a ton";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
