package shadow.test.typecheck;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shadow.Main;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.TypeCheckException.Error;

public class NegativeTests {

	private ArrayList<String> args = new ArrayList<String>();

	@BeforeEach
	public void setup() throws Exception {
		//args.add("-v");
		args.add("--typecheck");

		String os = System.getProperty("os.name").toLowerCase();
		
		args.add("-c");
		if( os.contains("windows") )
			args.add("windows.xml");		
		else if( os.contains("mac") )
			args.add("mac.xml");
		else
			args.add("linux.xml");
	}
	
	private void enforce(Error type) throws Exception
	{
		try
		{
			Main.run(args.toArray(new String[] { }));
			throw new Exception("Test failed");
		}
		catch( TypeCheckException e )
		{
			if( !e.getError().equals(type) )
				throw new Exception("Test failed");
		}
		catch( Exception e )
		{	
			e.printStackTrace();
			throw new Exception("Test failed");
		}
	}
	
	@Test public void testPackage() throws Exception
	{
		args.add("tests-negative/typechecker/package/Test.shadow");
		enforce(Error.INVALID_PACKAGE);		
	}
	
	@Test public void testWrongFile() throws Exception
	{
		args.add("tests-negative/typechecker/wrong-file/Test.shadow");
		enforce(Error.INVALID_FILE);		
	}
	
	@Test public void testPackagePath() throws Exception
	{
		args.add("tests-negative/typechecker/package-path/Test.shadow");
		enforce(Error.INVALID_PACKAGE);		
	}
	
	@Test public void testClassClassCollision() throws Exception
	{
		args.add("tests-negative/typechecker/class-class-collision/Test.shadow");
		enforce(Error.MULTIPLY_DEFINED_SYMBOL);		
	}
	
	@Test public void testClassExtendsLockedClass() throws Exception
	{
		args.add("tests-negative/typechecker/class-extends-locked-class/Test.shadow"); 
		enforce(Error.INVALID_PARENT);		
	}
	
	@Test public void testImport() throws Exception
	{
		args.add("tests-negative/typechecker/import/Test.shadow");
		enforce(Error.INVALID_IMPORT);		
	}
	
	@Test public void testGetCollision() throws Exception
	{
		args.add("tests-negative/typechecker/get-collision/Test.shadow");
		enforce(Error.INVALID_MODIFIER);		
	}
	
	@Test public void testSetCollision() throws Exception
	{
		args.add("tests-negative/typechecker/set-collision/Test.shadow");
		enforce(Error.INVALID_MODIFIER);		
	}
	
	@Test public void testCircularExtends() throws Exception
	{
		args.add("tests-negative/typechecker/circular-extends/Test.shadow");
		enforce(Error.INVALID_HIERARCHY);		
	}
	
	@Test public void testCircularImplements() throws Exception
	{
		args.add("tests-negative/typechecker/circular-implements/Test.shadow");
		enforce(Error.INVALID_HIERARCHY);		
	}
	
	@Test public void testMultipleVisibility() throws Exception
	{
		args.add("tests-negative/typechecker/multiple-visibility/Test.shadow");
		enforce(Error.INVALID_MODIFIER);		
	}
	
	@Test public void testInterfaceVisibility() throws Exception
	{
		args.add("tests-negative/typechecker/interface-visibility/Test.shadow");
		enforce(Error.INVALID_MODIFIER);		
	}
	
	@Test public void testNoVisibility() throws Exception
	{
		args.add("tests-negative/typechecker/no-visibility/Test.shadow");
		enforce(Error.INVALID_MODIFIER);		
	}
	
	@Test public void testMethodMethodCollision() throws Exception
	{
		args.add("tests-negative/typechecker/method-method-collision/Test.shadow");
		enforce(Error.MULTIPLY_DEFINED_SYMBOL);		
	}	
	
	@Test public void testSingletonCreate() throws Exception
	{
		args.add("tests-negative/typechecker/singleton-create/Test.shadow");
		enforce(Error.INVALID_SINGLETON_CREATE);		
	}	
	
	@Test public void testGetParameters() throws Exception
	{
		args.add("tests-negative/typechecker/get-parameters/Test.shadow");
		enforce(Error.INVALID_MODIFIER);		
	}
	
	@Test public void testGetReturns() throws Exception
	{
		args.add("tests-negative/typechecker/get-returns/Test.shadow");
		enforce(Error.INVALID_MODIFIER);		
	}
	
	@Test public void testSetParameters() throws Exception
	{
		args.add("tests-negative/typechecker/set-parameters/Test.shadow");
		enforce(Error.INVALID_MODIFIER);		
	}
	
	@Test public void testSetReturns() throws Exception
	{
		args.add("tests-negative/typechecker/set-returns/Test.shadow");
		enforce(Error.INVALID_MODIFIER);		
	}
	
	@Test public void testParameterParameterCollision() throws Exception
	{
		args.add("tests-negative/typechecker/parameter-parameter-collision/Test.shadow"); 
		enforce(Error.MULTIPLY_DEFINED_SYMBOL);		
	}	
	
	@Test public void testFieldType() throws Exception
	{
		args.add("tests-negative/typechecker/field-type/Test.shadow"); 
		enforce(Error.UNDEFINED_TYPE);		
	}
	
	@Test public void testFieldConstantParameterized() throws Exception
	{
		args.add("tests-negative/typechecker/field-constant-parameterized/Test.shadow"); 
		enforce(Error.INVALID_TYPE_PARAMETERS);		
	}	

	@Test public void testFieldClassCollision() throws Exception
	{
		args.add("tests-negative/typechecker/field-class-collision/Test.shadow"); 
		enforce(Error.MULTIPLY_DEFINED_SYMBOL);		
	}
	
	@Test public void testFieldFieldCollision() throws Exception
	{
		args.add("tests-negative/typechecker/field-field-collision/Test.shadow"); 
		enforce(Error.MULTIPLY_DEFINED_SYMBOL);		
	}
	
	@Test public void testTypeParameterCollision() throws Exception
	{
		args.add("tests-negative/typechecker/type-parameter-collision/Test.shadow"); 
		enforce(Error.MULTIPLY_DEFINED_SYMBOL);		
	}

	@Test public void testExceptionTypeParameter() throws Exception
	{
		args.add("tests-negative/typechecker/exception-type-parameter/Test.shadow"); 
		enforce(Error.INVALID_TYPE_PARAMETERS);		
	}
	
	@Test public void testSingletonTypeParameter() throws Exception
	{
		args.add("tests-negative/typechecker/singleton-type-parameter/Test.shadow"); 
		enforce(Error.INVALID_TYPE_PARAMETERS);		
	}
	
	@Test public void testClassExtendsNonClass() throws Exception
	{
		args.add("tests-negative/typechecker/class-extends-non-class/Test.shadow"); 
		enforce(Error.INVALID_PARENT);		
	}
	
	@Test public void testExceptionExtendsNonException() throws Exception
	{
		args.add("tests-negative/typechecker/exception-extends-non-exception/Test.shadow"); 
		enforce(Error.INVALID_PARENT);		
	}	
	
	@Test public void testInterfaceExtendsNonInterface() throws Exception
	{
		args.add("tests-negative/typechecker/interface-extends-non-interface/Test.shadow"); 
		enforce(Error.INVALID_INTERFACE);		
	}
	
	@Test public void testClassImplementsInterfaceBeforeParent() throws Exception
	{
		args.add("tests-negative/typechecker/class-implements-interface-before-parent/Test.shadow"); 
		enforce(Error.INVALID_PARENT);		
	}
	
	@Test public void testReadonlyStore() throws Exception
	{
		args.add("tests-negative/typechecker/readonly-store/Test.shadow"); 
		enforce(Error.INVALID_ASSIGNMENT);		
	}
	
	@Test public void testReadonlyCall() throws Exception
	{
		args.add("tests-negative/typechecker/readonly-call/Test.shadow"); 
		enforce(Error.ILLEGAL_ACCESS);		
	}
	
	@Test public void testReadonlyMethodStore1() throws Exception
	{
		args.add("tests-negative/typechecker/readonly-method-store/Test1.shadow"); 
		enforce(Error.INVALID_ASSIGNMENT);		
	}
	
	@Test public void testReadonlyMethodStore2() throws Exception
	{
		args.add("tests-negative/typechecker/readonly-method-store/Test2.shadow"); 
		enforce(Error.INVALID_ASSIGNMENT);		
	}
	
	@Test public void testReadonlyMethodStore3() throws Exception
	{
		args.add("tests-negative/typechecker/readonly-method-store/Test3.shadow"); 
		enforce(Error.INVALID_ASSIGNMENT);		
	}
	
	@Test public void testReadonlyMethodStore4() throws Exception
	{
		args.add("tests-negative/typechecker/readonly-method-store/Test4.shadow"); 
		enforce(Error.INVALID_ASSIGNMENT);		
	}
	
	@Test public void testReadonlyMethodCall1() throws Exception
	{
		args.add("tests-negative/typechecker/readonly-method-call1/Test.shadow"); 
		enforce(Error.ILLEGAL_ACCESS);		
	}
	
	@Test public void testReadonlyMethodCall2() throws Exception
	{
		args.add("tests-negative/typechecker/readonly-method-call2/Test.shadow"); 
		enforce(Error.ILLEGAL_ACCESS);		
	}
	
	@Test public void testReadonlyMethodCall3() throws Exception
	{
		args.add("tests-negative/typechecker/readonly-method-call3/Test.shadow"); 
		enforce(Error.ILLEGAL_ACCESS);		
	}
	
	@Test public void testReadonlyMethodCall4() throws Exception
	{
		args.add("tests-negative/typechecker/readonly-method-call4/Test.shadow"); 
		enforce(Error.ILLEGAL_ACCESS);		
	}
	
	@Test public void testImmutableStore() throws Exception
	{
		args.add("tests-negative/typechecker/immutable-store/Test.shadow"); 
		enforce(Error.INVALID_ASSIGNMENT);		
	}
	
	@Test public void testImmutableCall() throws Exception
	{
		args.add("tests-negative/typechecker/immutable-call/Test.shadow"); 
		enforce(Error.ILLEGAL_ACCESS);		
	}	
		
	@Test public void testMethodLeaksMutableReference() throws Exception
	{
		args.add("tests-negative/typechecker/method-leaks-mutable-reference/Test.shadow"); 
		enforce(Error.INVALID_METHOD);		
	}
	
	@Test public void testMethodLockedOverride() throws Exception
	{
		args.add("tests-negative/typechecker/method-locked-override/Test.shadow"); 
		enforce(Error.INVALID_OVERRIDE);		
	}
	
	@Test public void testMethodParameterAndReturnVisibility() throws Exception
	{
		args.add("tests-negative/typechecker/method-parameter-and-return-visibility/Test.shadow");
		enforce(Error.ILLEGAL_ACCESS);
	}
	
	@Test public void testNoDefaultCreateForArray() throws Exception
	{
		args.add("tests-negative/typechecker/no-default-create-for-array/Test.shadow"); 
		enforce(Error.INVALID_CREATE);		
	}
	
	@Test public void testBadArrayCast() throws Exception
	{
		args.add("tests-negative/typechecker/bad-array-cast/Test.shadow"); 
		enforce(Error.MISMATCHED_TYPE);		
	}
	
	@Test public void testBreakOutsideLoop() throws Exception
	{
		args.add("tests-negative/typechecker/break-outside-loop/Test.shadow"); 
		enforce(Error.INVALID_STRUCTURE);		
	}
	
	@Test public void testStoreToSingleton() throws Exception
	{
		args.add("tests-negative/typechecker/store-to-singleton/Test.shadow"); 
		enforce(Error.INVALID_ASSIGNMENT);		
	}
	
	@Test public void testInvalidLiteral() throws Exception {
		args.add("tests-negative/typechecker/invalid-literal/Test.shadow");
		enforce(Error.INVALID_LITERAL);			
	}
	
	
	@Test public void testPropertyGetVisibility() throws Exception {
		args.add("tests-negative/typechecker/property-get-visibility/Test.shadow");
		enforce(Error.ILLEGAL_ACCESS);			
	}	
	
	@Test public void testPropertySetVisibility() throws Exception {
		args.add("tests-negative/typechecker/property-set-visibility/Test.shadow");
		enforce(Error.ILLEGAL_ACCESS);			
	}
	
	@Test public void testReadonlyPropertyGet() throws Exception {
		args.add("tests-negative/typechecker/readonly-property-get/Test.shadow");
		enforce(Error.ILLEGAL_ACCESS);			
	}
	
	@Test public void testReadonlyPropertySet() throws Exception {
		args.add("tests-negative/typechecker/readonly-property-set/Test.shadow");
		enforce(Error.ILLEGAL_ACCESS);			
	}
	
	@Test public void testInnerClassImplementsCreate() throws Exception {
		args.add("tests-negative/typechecker/inner-class-implements-create/Test.shadow");
		enforce(Error.INVALID_INTERFACE);			
	}
	
	@Test public void testInterfaceArrayCreate() throws Exception {
		args.add("tests-negative/typechecker/interface-array-create/Test.shadow");
		enforce(Error.INVALID_CREATE);			
	}
	
	@Test public void testInterfaceCreate() throws Exception {
		args.add("tests-negative/typechecker/interface-create/Test.shadow");
		enforce(Error.INVALID_CREATE);			
	}
	
	@Test public void testMethodDoesNotReturn() throws Exception {
		args.add("tests-negative/typechecker/method-does-not-return/Test.shadow");
		enforce(Error.NOT_ALL_PATHS_RETURN);			
	}
	
	@Test public void testUnreachableCode() throws Exception {
		args.add("tests-negative/typechecker/unreachable-code/Test.shadow");
		enforce(Error.UNREACHABLE_CODE);			
	}
	
	@Test public void testUndefinedVariable1() throws Exception {
		args.add("tests-negative/typechecker/undefined-variable1/Test.shadow");
		enforce(Error.UNDEFINED_VARIABLE);			
	}
	
	@Test public void testUndefinedVariable2() throws Exception {
		args.add("tests-negative/typechecker/undefined-variable2/Test.shadow");
		enforce(Error.UNDEFINED_VARIABLE);			
	}
	
	@Test public void testCircularCreates() throws Exception {
		args.add("tests-negative/typechecker/circular-creates/Test.shadow");
		enforce(Error.CIRCULAR_CREATE);			
	}
	
	@Test public void testFieldReferencesItself() throws Exception {
		args.add("tests-negative/typechecker/field-references-itself/Test.shadow");
		enforce(Error.UNINITIALIZED_FIELD);			
	}
	
	@Test public void testFieldUsedBeforeInitialization() throws Exception {
		args.add("tests-negative/typechecker/field-used-before-initialization/Test.shadow");
		enforce(Error.UNINITIALIZED_FIELD);			
	}	
	
	@Test public void testFieldMightNotBeInitialized() throws Exception {
		args.add("tests-negative/typechecker/field-might-not-be-initialized/Test.shadow");
		enforce(Error.UNINITIALIZED_FIELD);			
	}
	
	@Test public void testCallOnSequence() throws Exception {
		args.add("tests-negative/typechecker/call-on-sequence/Test.shadow");
		enforce(Error.INVALID_TYPE);			
	}
	
	@Test public void testThisStoredBeforeInitialization() throws Exception {
		args.add("tests-negative/typechecker/this-stored-before-initialization/Test.shadow");
		enforce(Error.READ_OF_THIS_IN_CREATE);			
	}	
	
	@Test public void testMethodCallBeforeFieldInitialization() throws Exception {
		args.add("tests-negative/typechecker/method-call-before-field-initialization/Test.shadow");
		enforce(Error.ILLEGAL_ACCESS);			
	}
	
	@Test public void testUnlockedCallOnThisInCreate() throws Exception {
		args.add("tests-negative/typechecker/unlocked-call-on-this-in-create/Test.shadow");
		enforce(Error.ILLEGAL_ACCESS);			
	}
	
	@Test public void testCallOnThisBeforeFieldInitialization() throws Exception {
		args.add("tests-negative/typechecker/call-on-this-before-field-initialization/Test.shadow");
		enforce(Error.UNINITIALIZED_FIELD);			
	}
	
	@Test public void testChainOfCallsBeforeFieldInitialization() throws Exception {
		args.add("tests-negative/typechecker/chain-of-calls-before-field-initialization/Test.shadow");
		enforce(Error.UNINITIALIZED_FIELD);			
	}
	
	@Test public void testDependencyListInRegularFile() throws Exception {
		args.add("tests-negative/typechecker/dependency-list-in-regular-file/Test.shadow");
		enforce(Error.INVALID_STRUCTURE);			
	}
	
	@Test public void testClassWithoutTypeArguments() throws Exception {
		args.add("tests-negative/typechecker/class-without-type-arguments/Test.shadow");
		enforce(Error.MISSING_TYPE_ARGUMENTS);			
	}
	
	@Test public void testNonArrayNullableCast() throws Exception {
		args.add("tests-negative/typechecker/non-array-nullable-cast/Test.shadow");
		enforce(Error.INVALID_CAST);		
	}	
	
	@Test public void testBreakOutOfFinally() throws Exception {
		args.add("tests-negative/typechecker/break-out-of-finally/Test.shadow");
		enforce(Error.INVALID_STRUCTURE);		
	}
	
	@Test public void testReturnFromFinally() throws Exception {
		args.add("tests-negative/typechecker/return-from-finally/Test.shadow");
		enforce(Error.INVALID_STRUCTURE);		
	}
	
	@Test public void testCheckLeavingFinally() throws Exception {
		args.add("tests-negative/typechecker/check-leaving-finally/Test.shadow");
		enforce(Error.INVALID_STRUCTURE);		
	}
	
	@Test public void testTypeParameterWithMultipleClassBounds() throws Exception {
		args.add("tests-negative/typechecker/type-parameter-with-multiple-class-bounds/Test.shadow");
		enforce(Error.INVALID_TYPE_PARAMETERS);		
	}
	
	@Test public void testMethodIsNotReadonlyButInterfaceMethodIs() throws Exception {
		args.add("tests-negative/typechecker/method-is-not-readonly-but-interface-method-is/Test.shadow");
		enforce(Error.MISSING_INTERFACE);		
	}
}
