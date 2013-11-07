package example.jsr.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import example.jsr.annotations.PasswordAndEmailDoNotMatch;
import example.jsr.signup.SignupForm;

@RunWith(MockitoJUnitRunner.class)
public class PasswordAndEmailDoNotMatchValidatorTest {
	
	@Spy
	PasswordAndEmailDoNotMatchValidator validator = new PasswordAndEmailDoNotMatchValidator();
	
	@Mock
	PasswordAndEmailDoNotMatch annotation;
	
	@Mock
	ConstraintValidatorContext context;
	
	@Mock
	ConstraintViolationBuilder violationBuilder;
	
	@Mock
	NodeBuilderDefinedContext nodeBuilder;
	
	@Before
	public void stubAnnotation(){
		Mockito.when(annotation.message()).thenReturn("default.message");
		Mockito.when(annotation.path()).thenReturn("default.path");
	}
	
	@Before
	public void stubBuilders(){
		Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.isA(String.class))).thenReturn(violationBuilder);
		Mockito.when(violationBuilder.addNode(Mockito.isA(String.class))).thenReturn(nodeBuilder);
		Mockito.when(nodeBuilder.addConstraintViolation()).thenReturn(context);
	}

	@Test
	public void testInitialize() {
		validator.initialize(annotation);
		assertEquals(annotation.path(), validator.path);
		assertEquals(annotation.message(), validator.message);
	}

	@Test
	public void testIsValid() {
		SignupForm validForm = new SignupForm();
		validForm.setEmail("non matching string");
		validForm.setPassword("another not matching string");
		validator.initialize(annotation);
		assertTrue(validator.isValid(validForm, context));
	}
	@Test
	public void testIsInvalid() {
		SignupForm validForm = new SignupForm();
		validForm.setEmail("matching string");
		validForm.setPassword("matching string");
		validator.initialize(annotation);
		assertFalse(validator.isValid(validForm, context));
		Mockito.verify(context).disableDefaultConstraintViolation();
		Mockito.verify(context).buildConstraintViolationWithTemplate(annotation.message());
		Mockito.verify(violationBuilder).addNode(annotation.path());
		Mockito.verify(nodeBuilder).addConstraintViolation();
	}

}
