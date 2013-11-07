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
public class AnagramIntegerValidatorTest {
	@Spy
	AnagramIntegerValidator validator = new AnagramIntegerValidator();

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
		assertTrue(validator.isValid(1001, context));
		Mockito.verifyZeroInteractions(context);
		Mockito.verify(validator).isAnagram("1001");
	}

	@Test
	public void testIsInvalid() {
		assertFalse(validator.isValid(11001, context));
		Mockito.verifyZeroInteractions(context);
		Mockito.verify(validator).isAnagram("11001");
	}

	@Test
	public void testIs0() {
		assertTrue(validator.isValid(0, context));
		Mockito.verifyZeroInteractions(context);
		Mockito.verify(validator).isAnagram("0");
	}
	
	@Test
	public void testNumberConvertedToStringCorrectly(){
		for(Integer i = 0; i < 5000; i += 3){
			validator.isValid(i, context);
			Mockito.verifyZeroInteractions(context);
			Mockito.verify(validator).isAnagram(i.toString());
		}
	}

}
