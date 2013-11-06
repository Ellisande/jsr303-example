package example.sf.jsr.jsr_example.validators;

import javax.validation.ConstraintValidatorContext;

import example.sf.jsr.jsr_example.annotations.Anagram;

public class AnagramStringValidator extends AnagramValidator<String> {

	@Override
	public void initialize(Anagram constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		System.out.println(this.getClass().getName() + " hit");
		return isAnagram(value);
	}

}
