package example.jsr.validators;

import javax.validation.ConstraintValidatorContext;

import example.jsr.annotations.Anagram;



public class AnagramIntegerValidator extends AnagramValidator<Integer> {

	@Override
	public void initialize(Anagram constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		String integer = value.toString();
		return isAnagram(integer);
	}
}
