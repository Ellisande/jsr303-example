package example.jsr.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import example.jsr.annotations.PasswordAndEmailDoNotMatch;
import example.jsr.signup.SignupForm;

public class PasswordAndEmailDoNotMatchValidator implements ConstraintValidator<PasswordAndEmailDoNotMatch, SignupForm> {

	String message;
	String path;

	@Override
	public void initialize(PasswordAndEmailDoNotMatch constraintAnnotation) {
		message = constraintAnnotation.message();
		path = constraintAnnotation.path();

	}

	@Override
	public boolean isValid(SignupForm signupForm, ConstraintValidatorContext context) {

		ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(message);
		violationBuilder.addNode(path).addConstraintViolation();
		context.disableDefaultConstraintViolation();

		return !signupForm.getEmail().equalsIgnoreCase(signupForm.getPassword());
	}

}
