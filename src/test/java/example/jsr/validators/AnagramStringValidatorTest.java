package example.jsr.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import example.jsr.annotations.Anagram;

@RunWith(MockitoJUnitRunner.class)
public class AnagramStringValidatorTest {
	@Spy
	AnagramStringValidator validator = new AnagramStringValidator();
	
	@Mock
	Anagram annotation;
	
	@Mock
	ConstraintValidatorContext context;

	@Test
	public void testInitialize() {
		validator.initialize(annotation);
		Mockito.verify(validator).initialize(annotation);
		Mockito.verifyZeroInteractions(annotation);
	}

	@Test
	public void testIsValid() {
		assertTrue(validator.isValid("racecar", context));
		Mockito.verifyZeroInteractions(context);
		Mockito.verify(validator).isAnagram("racecar");
	}
	@Test
	public void testIsInvalid() {
		assertFalse(validator.isValid("crazy stupid love", context));
		Mockito.verifyZeroInteractions(context);
		Mockito.verify(validator).isAnagram("crazy stupid love");
	}

}
