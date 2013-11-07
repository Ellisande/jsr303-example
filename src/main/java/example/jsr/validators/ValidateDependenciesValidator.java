package example.jsr.validators;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import example.jsr.annotations.DependencyProvider;
import example.jsr.annotations.DependsOn;
import example.jsr.annotations.DependsOn.DependencyRules;
import example.jsr.annotations.ValidateDependencies;

public class ValidateDependenciesValidator implements ConstraintValidator<ValidateDependencies, Object> {

	EnumMap<DependencyRules, DependencyRule> rules;

	@Override
	public void initialize(ValidateDependencies constraintAnnotation) {
		rules = new EnumMap<DependencyRules, DependencyRule>(DependencyRules.class);
		rules.put(DependencyRules.REQUIRED_WHEN_PROVIDER_PRESENT, new RequiredWhenProviderPresent());
		rules.put(DependencyRules.OPTIONAL_WHEN_PROVIDER_NOT_PRESENT, new OptionalWhenProviderPresent());
		rules.put(DependencyRules.REJECT_IF_PROVIDER_PRESENT, new RequiredWhenProviderNotPresent());
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean reportedViolation = false;
		Map<String, ProviderInfo> providerFields = new HashMap<String, ProviderInfo>();

		for (Field field : value.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(DependencyProvider.class)) {
				DependencyProvider annotation = AnnotationUtils.getAnnotation(field, DependencyProvider.class);
				boolean isNull = ReflectionUtils.getField(field, value) == null ? true : false;
				providerFields.put(annotation.key(), new ProviderInfo(isNull, field));
			}
		}

		for (Field field : value.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(DependsOn.class)) {
				DependsOn currentAnnotation = AnnotationUtils.getAnnotation(field, DependsOn.class);
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

	private class RequiredWhenProviderPresent implements DependencyRule {
		public boolean isValid(Boolean providerIsNull, Boolean dependentIsNull) {
			return !providerIsNull && !dependentIsNull;
		}
	}

	private class OptionalWhenProviderPresent implements DependencyRule {
		public boolean isValid(Boolean providerIsNull, Boolean dependentIsNull) {
			return !providerIsNull || !dependentIsNull;
		}
	}

	private class RequiredWhenProviderNotPresent implements DependencyRule {
		public boolean isValid(Boolean providerIsNull, Boolean dependentIsNull) {
			return !providerIsNull && dependentIsNull;
		}
	}
}
