package example.jsr.validators;

import static org.junit.Assert.*;

import javax.validation.ConstraintValidatorContext;

import org.junit.Test;

import example.jsr.annotations.Anagram;

public class AnagramValidatorTest {

	AnagramValidator<String> validator = new AnagramValidator<String>() {

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return false;
		}

		@Override
		public void initialize(Anagram constraintAnnotation) {
		}
	};

	@Test
	public void testIsAnagramTrueEven() {
		String anagramString = "a man a plan a canal panama";
		assertTrue(validator.isAnagram(anagramString));
	}

	@Test
	public void testIsAnagramTrueOdd() {
		String anagramString = "racecar";
		assertTrue(validator.isAnagram(anagramString));
	}

	@Test
	public void testIsAnagramFalse() {
		String nonAnagramString = "what does the fox say?";
		assertFalse(validator.isAnagram(nonAnagramString));
	}
	
	@Test
	public void testIsAnagramEmpty() {
		String nonAnagramString = "";
		assertFalse(validator.isAnagram(nonAnagramString));
	}
	
	@Test
	public void testIsAnagramNull() {
		String nonAnagramString = null;
		assertFalse(validator.isAnagram(nonAnagramString));
	}

}
