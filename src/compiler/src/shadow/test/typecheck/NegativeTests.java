package shadow.test.typecheck;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;

import shadow.Loggers;
import shadow.Main;
import shadow.TypeCheckException;
import shadow.TypeCheckException.Error;

public class NegativeTests {

	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		args.add("--check");
		args.add("--config");

		String osName = System.getProperty("os.name");

		if(osName.startsWith("Windows"))
			args.add("src/test_windows_config.xml");
		else
			args.add("src/test_linux_config.xml");		
		
		// set the levels of our loggers
		Loggers.SHADOW.setLevel(Level.DEBUG);
		Loggers.TYPE_CHECKER.setLevel(Level.INFO);
		Loggers.PARSER.setLevel(Level.INFO);
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
	
	@Test public void testMethodClassCollision() throws Exception
	{
		args.add("tests-negative/typechecker/method-class-collision/Test.shadow");
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
	
	@Test public void testParameterPrimitiveNullable() throws Exception
	{
		args.add("tests-negative/typechecker/parameter-primitive-nullable/Test.shadow"); 
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
	
	@Test public void testFieldPrimitiveNullable() throws Exception
	{
		args.add("tests-negative/typechecker/field-primitive-nullable/Test.shadow"); 
		enforce(Error.INVALID_MODIFIER);		
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
	
	@Test public void testErrorTypeParameter() throws Exception
	{
		args.add("tests-negative/typechecker/error-type-parameter/Test.shadow"); 
		enforce(Error.INVALID_TYPE_PARAMETERS);		
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
		enforce(Error.INVALID_EXTEND);		
	}
	
	@Test public void testExceptionExtendsNonException() throws Exception
	{
		args.add("tests-negative/typechecker/exception-extends-non-exception/Test.shadow"); 
		enforce(Error.INVALID_EXTEND);		
	}	
	
	@Test public void testInterfaceExtendsNonInterface() throws Exception
	{
		args.add("tests-negative/typechecker/interface-extends-non-interface/Test.shadow"); 
		enforce(Error.INVALID_EXTEND);		
	}
	
	@Test public void testClassImplementsNonInterface() throws Exception
	{
		args.add("tests-negative/typechecker/class-implements-non-interface/Test.shadow"); 
		enforce(Error.INVALID_IMPLEMENT);		
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
}
