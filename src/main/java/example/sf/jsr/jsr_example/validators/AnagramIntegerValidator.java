package example.sf.jsr.jsr_example.validators;

import javax.validation.ConstraintValidatorContext;

import example.sf.jsr.jsr_example.annotations.Anagram;

public class AnagramIntegerValidator extends AnagramValidator<Integer> {

	@Override
	public void initialize(Anagram constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		System.out.println(this.getClass().getName()+" hit");
		String integer = value.toString();
		return isAnagram(integer);
	}
}
