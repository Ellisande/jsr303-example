package example.jsr.validators;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import example.jsr.annotations.DependencyWith;
import example.jsr.annotations.DependentField;
import example.jsr.annotations.DependentField.DependencyRules;
import example.jsr.annotations.ValidateDependencies;

public class ValidateDependenciesValidator implements ConstraintValidator<ValidateDependencies, Object> {

	EnumMap<DependencyRules, DependencyRule> rules;

	@Override
	public void initialize(ValidateDependencies constraintAnnotation) {
		rules = new EnumMap<DependencyRules, DependencyRule>(DependencyRules.class);
		rules.put(DependencyRules.REQUIRED_WHEN_PROVIDER_IS_PRESENT, new RequiredWhenProviderIsPresent());
		rules.put(DependencyRules.CANNOT_BE_PRESENT_IF_PROVIDER_NOT_PRESENT, new CannotBePresentIfProviderIsNotPresent());
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean reportedViolation = false;
		Map<String, ProviderInfo> providerFields = new HashMap<String, ProviderInfo>();

		for (Field field : value.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(DependencyWith.class)) {
				DependencyWith annotation = AnnotationUtils.getAnnotation(field, DependencyWith.class);
				boolean isNull = ReflectionUtils.getField(field, value) == null ? true : false;
				providerFields.put(annotation.key(), new ProviderInfo(isNull, field));
			}
		}

		for (Field field : value.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(DependentField.class)) {
				DependentField currentAnnotation = AnnotationUtils.getAnnotation(field, DependentField.class);
				boolean isNull = ReflectionUtils.getField(field, value) == null ? true : false;
				ProviderInfo providerInfo = providerFields.get(currentAnnotation.key());
				boolean isValid = rules.get(currentAnnotation.rule()).isValid(providerInfo.isNull(), isNull);
				if (!isValid) {
					String injectFieldNamesIntoMessage = String.format(currentAnnotation.violationMesssage(), providerInfo.getField().getName());
					context.buildConstraintViolationWithTemplate(injectFieldNamesIntoMessage).addNode(field.getName()).addConstraintViolation();
					reportedViolation = true;
				}
			}
		}

		if (reportedViolation) {
			context.disableDefaultConstraintViolation();
		}
		return !reportedViolation;
	}

	private class ProviderInfo {
		private boolean isNull;
		private Field field;

		public ProviderInfo(boolean isNull, Field field) {
			super();
			this.isNull = isNull;
			this.field = field;
		}

		public boolean isNull() {
			return isNull;
		}

		public Field getField() {
			return field;
		}

	}

	private interface DependencyRule {
		boolean isValid(Boolean providerIsNull, Boolean dependentIsNull);
	}

	private class RequiredWhenProviderIsPresent implements DependencyRule {
		public boolean isValid(Boolean providerIsNull, Boolean dependentIsNull) {
			return !(!providerIsNull && dependentIsNull);
		}
	}

	private class CannotBePresentIfProviderIsNotPresent implements DependencyRule {
		public boolean isValid(Boolean providerIsNull, Boolean dependentIsNull) {
			return !(providerIsNull  && !dependentIsNull);
		}
	}

}
