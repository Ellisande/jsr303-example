package example.sf.jsr.jsr_example.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import example.sf.jsr.jsr_example.annotations.PasswordAndEmailDoNotMatch;

import example.sf.jsr.jsr_example.signup.SignupForm;

public class PasswordAndEmailDoNotMatchValidator implements ConstraintValidator<PasswordAndEmailDoNotMatch, SignupForm>{

	private String message;
	private String path;
	
	@Override
	public void initialize(PasswordAndEmailDoNotMatch constraintAnnotation) {
		message = constraintAnnotation.message();
		path = constraintAnnotation.path();
		
	}

	@Override
	public boolean isValid(SignupForm signupForm, ConstraintValidatorContext context) {
		
		ConstraintViolationBuilder a = context.buildConstraintViolationWithTemplate(message);
		a.addNode(path).addConstraintViolation();
		context.disableDefaultConstraintViolation();
		
		return !signupForm.getEmail().equalsIgnoreCase(signupForm.getPassword());
	}

}
