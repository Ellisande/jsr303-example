package example.sf.jsr.jsr_example.validators;

import javax.validation.ConstraintValidator;

import example.sf.jsr.jsr_example.annotations.Anagram;

public abstract class AnagramValidator<K> implements ConstraintValidator<Anagram, K>{

	protected boolean isAnagram(String value) {
		boolean hasMiddleCharacter = value.length() % 2 != 0;
		int firstHalfStopIndex = value.length() / 2;
		int secondHalfStartIndex = hasMiddleCharacter ? value.length() / 2 + 1 : value.length() / 2;
		String firstHalf = value.substring(0, firstHalfStopIndex);
		String secondHalf = value.substring(secondHalfStartIndex);
		String reversedSecondHalf = new StringBuilder(secondHalf).reverse().toString();
		return firstHalf.equals(reversedSecondHalf);
	}

}
