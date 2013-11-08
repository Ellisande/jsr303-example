package example.jsr.signup;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.junit.Before;
import org.junit.Test;

import example.jsr.validation.groups.AdminAccount;
import example.jsr.validation.groups.SuperstarAccount;

public class SignupFormValidationTest {

	Validator validator;

	@Before
	public void setupValidator() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	public void testEmailNotBlankInvalid() {
		SignupForm blankEmail = new SignupForm();
		blankEmail.setEmail("");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(blankEmail, Default.class);
		assertTrue(isViolationWithMessageAndPathPresent(result, "email", "{notBlank.message}"));
	}

	@Test
	public void testEmailAnagramInvalid() {
		SignupForm nonAnagram = new SignupForm();
		nonAnagram.setEmail("not an anagram");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(nonAnagram, SuperstarAccount.class);
		assertTrue(isViolationWithMessageAndPathPresent(result, "email", "{email.anagram}"));
	}

	@Test
	public void testEmailNotLameInvalid() {
		SignupForm lameEmail = new SignupForm();
		lameEmail.setEmail("something@geocities.com");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(lameEmail, SuperstarAccount.class);
		assertTrue(isViolationWithMessageAndPathPresent(result, "email", "{email.lame}"));
	}

	@Test
	public void testEmailDefaultValid() {
		SignupForm validEmail = new SignupForm();
		validEmail.setEmail("someone@gmail.com");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(validEmail, Default.class);
		assertNoViolationsForField(result, "email");
	}

	@Test
	public void testEmailSuperstarValid() {
		SignupForm validEmail = new SignupForm();
		validEmail.setEmail("moc.liamg@gmail.com");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(validEmail, SuperstarAccount.class);
		assertNoViolationsForField(result, "email");
	}

	@Test
	public void testPasswordNotBlankViolation() {
		SignupForm noPassword = new SignupForm();
		noPassword.setEmail("");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(noPassword, Default.class);
		assertTrue(isViolationWithMessageAndPathPresent(result, "password", "{notBlank.message}"));
	}

	@Test
	public void testPasswordTooShortViolation() {
		SignupForm shortPassword = new SignupForm();
		shortPassword.setEmail("");
		shortPassword.setPassword("shortit");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(shortPassword, Default.class);
		assertTrue(isViolationWithMessageAndPathPresent(result, "password", "{password.min_length}"));
	}

	@Test
	public void testPasswordTooShortViolationAdmin() {
		SignupForm shortPassword = new SignupForm();
		shortPassword.setPassword("justshort");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(shortPassword, AdminAccount.class);
		assertTrue(isViolationWithMessageAndPathPresent(result, "password", "{password.min_length}"));
	}

	@Test
	public void testPasswordDefaultValid() {
		SignupForm validPassword = new SignupForm();
		validPassword.setEmail("");
		validPassword.setPassword("eightis!");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(validPassword, Default.class);
		assertNoViolationsForField(result, "password");
	}

	@Test
	public void testPasswordAdminValid() {
		SignupForm validPassword = new SignupForm();
		validPassword.setEmail("");
		validPassword.setPassword("123456789!");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(validPassword, AdminAccount.class);
		assertNoViolationsForField(result, "password");
	}

	@Test
	public void testPasswordNoSpecialCharacterViolation() {
		SignupForm notSpecialPassword = new SignupForm();
		notSpecialPassword.setPassword("justshort");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(notSpecialPassword, AdminAccount.class);
		assertTrue(isViolationWithMessageAndPathPresent(result, "password", "{password.contain_special}"));
	}

	@Test
	public void testValid() {
		SignupForm validBean = new SignupForm();
		validBean.setPassword("eightisgr");
		validBean.setEmail("liamg@gmail.com");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(validBean, Default.class);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testValidAdmin() {
		SignupForm validBean = new SignupForm();
		validBean.setPassword("eightisgreat!");
		validBean.setEmail("liamg@gmail.com");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(validBean, AdminAccount.class);
		assertTrue("Expected no results, but got: " + result, result.isEmpty());
	}

	@Test
	public void testValidSuperstar() {
		SignupForm validBean = new SignupForm();
		validBean.setPassword("eightisgrea!");
		validBean.setEmail("moc.liamg@gmail.com");
		Set<ConstraintViolation<SignupForm>> result = validator.validate(validBean, SuperstarAccount.class);
		assertTrue(result.isEmpty());
	}

	private <T> boolean isViolationWithMessageAndPathPresent(Collection<ConstraintViolation<T>> violations, String fieldPath, String message) {
		Set<ConstraintViolation<T>> allViolations = getViolationsForField(violations, fieldPath);
		return isViolationWithMessagePresent(allViolations, message);
	}

	private <T> Set<ConstraintViolation<T>> getViolationsForField(Collection<ConstraintViolation<T>> violations, String fieldPath) {
		Set<ConstraintViolation<T>> resultViolations = new HashSet<ConstraintViolation<T>>();
		for (ConstraintViolation<T> violation : violations) {
			String pathString = violation.getPropertyPath().toString();
			if (pathString.equals(fieldPath)) {
				resultViolations.add(violation);
			}
		}
		return resultViolations;
	}

	private <T> void assertNoViolationsForField(Set<ConstraintViolation<T>> violations, String fieldPath) {
		for (ConstraintViolation<T> violation : violations) {
			String pathString = violation.getPropertyPath().toString();
			if (pathString.equals(fieldPath)) {
				String failureMessage = String.format("Expected no constraint violations for %s but found %s", fieldPath, violation);
				fail(failureMessage);
			}
		}
	}

	private <T> boolean isViolationWithMessagePresent(Collection<ConstraintViolation<T>> violations, String message) {
		for (ConstraintViolation<T> violation : violations) {
			if (violation.getMessage().equals(message)) {
				return true;
			}
		}

		return false;
	}

}
