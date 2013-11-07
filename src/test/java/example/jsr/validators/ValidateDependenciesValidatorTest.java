package example.jsr.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumMap;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import example.jsr.annotations.DependencyWith;
import example.jsr.annotations.DependentField;
import example.jsr.annotations.DependentField.DependencyRules;
import example.jsr.annotations.ValidateDependencies;

@RunWith(MockitoJUnitRunner.class)
public class ValidateDependenciesValidatorTest {

	private static final String NOT_NULL = "Not Null";
	private static final String DEPENDENCY_CORRELATION_KEY = "key";
	private static final String CHAIN_DEPENDENCY_CORRELATION_KEY = "chainKey";

	@Spy
	ValidateDependenciesValidator validator = new ValidateDependenciesValidator();

	AnnotatedTestClass testClass;

	@Mock
	ValidateDependencies annotation;

	@Mock
	ConstraintValidatorContext context;
	
	@Mock
	ConstraintViolationBuilder violationBuilder;
	
	@Mock
	NodeBuilderDefinedContext nodeBuilder;
	
	@Before
	public void stubAnnotation(){
		when(annotation.message()).thenReturn("defaultMessage");
	}
	
	@Before
	public void stubBuilders(){
		when(context.buildConstraintViolationWithTemplate(isA(String.class))).thenReturn(violationBuilder);
		when(violationBuilder.addNode(isA(String.class))).thenReturn(nodeBuilder);
		when(nodeBuilder.addConstraintViolation()).thenReturn(context);
	}
	
	@Test
	public void testInitialize() {
		validator.initialize(annotation);
		EnumMap<DependencyRules, ? extends Object> ruleMap = validator.rules;
		assertTrue(ruleMap.size() == 2);
		assertNotNull(ruleMap.get(DependencyRules.REQUIRED_WHEN_PROVIDER_IS_PRESENT));
		assertNotNull(ruleMap.get(DependencyRules.CANNOT_BE_PRESENT_IF_PROVIDER_NOT_PRESENT));
	}

	@Test
	public void testIsValid() {
		validator.initialize(annotation);
		testClass = new AnnotatedTestClass();
		testClass.chainDependentField = NOT_NULL;
		testClass.dependentField1 = NOT_NULL;
		testClass.dependentField2 = null;
		testClass.independentField = NOT_NULL;

		assertTrue(validator.isValid(testClass, context));
	}
	
	@Test
	public void rootDependencyNotPresentValid() {
		validator.initialize(annotation);
		testClass = new AnnotatedTestClass();
		testClass.chainDependentField = NOT_NULL;
		testClass.dependentField1 = NOT_NULL;
		testClass.dependentField2 = null;
		testClass.independentField = null;
		
		assertTrue(validator.isValid(testClass, context));
	}
	
	@Test
	public void violateCannotBePresentRule() {
		validator.initialize(annotation);
		testClass = new AnnotatedTestClass();
		testClass.chainDependentField = NOT_NULL;
		testClass.dependentField1 = NOT_NULL;
		testClass.dependentField2 = NOT_NULL;
		testClass.independentField = null;
		
		assertFalse(validator.isValid(testClass, context));
		verify(context).disableDefaultConstraintViolation();
		verify(context).buildConstraintViolationWithTemplate("Required when independentField is present");
		verify(violationBuilder).addNode("dependentField2");
		verify(nodeBuilder).addConstraintViolation();
	}
	
	@Test
	public void violateRequiredIfPresentRule() {
		validator.initialize(annotation);
		testClass = new AnnotatedTestClass();
		testClass.chainDependentField = NOT_NULL;
		testClass.dependentField1 = null;
		testClass.dependentField2 = NOT_NULL;
		testClass.independentField = NOT_NULL;
		
		assertFalse(validator.isValid(testClass, context));
		verify(context).disableDefaultConstraintViolation();
		verify(context).buildConstraintViolationWithTemplate("Zuzu monkey independentField!");
		verify(violationBuilder).addNode("dependentField1");
		verify(nodeBuilder).addConstraintViolation();
	}
	
	@Test
	public void violateRequiredIfPresentRuleForChain() {
		validator.initialize(annotation);
		testClass = new AnnotatedTestClass();
		testClass.chainDependentField = null;
		testClass.dependentField1 = NOT_NULL;
		testClass.dependentField2 = NOT_NULL;
		testClass.independentField = NOT_NULL;
		
		assertFalse(validator.isValid(testClass, context));
		verify(context).disableDefaultConstraintViolation();
		verify(context).buildConstraintViolationWithTemplate("Required when dependentField1 is present");
		verify(violationBuilder).addNode("chainDependentField");
		verify(nodeBuilder).addConstraintViolation();
	}

	private class AnnotatedTestClass {

		@DependentField(key = CHAIN_DEPENDENCY_CORRELATION_KEY)
		public String chainDependentField;

		@DependencyWith(key = CHAIN_DEPENDENCY_CORRELATION_KEY)
		@DependentField(key = DEPENDENCY_CORRELATION_KEY, violationMesssage="Zuzu monkey %s!")
		public String dependentField1;

		@DependentField(key = DEPENDENCY_CORRELATION_KEY, rule = DependencyRules.CANNOT_BE_PRESENT_IF_PROVIDER_NOT_PRESENT)
		public String dependentField2;

		@DependencyWith(key = DEPENDENCY_CORRELATION_KEY)
		public String independentField;
	}

}
