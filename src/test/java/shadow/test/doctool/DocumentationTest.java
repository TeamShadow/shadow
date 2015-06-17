package shadow.test.doctool;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import shadow.doctool.DocumentationTool;
import shadow.doctool.DirectiveParser;

public class DocumentationTest 
{
	private ArrayList<String> args = new ArrayList<String>();
	
	@Before
	public void setup() throws Exception 
	{
		if (System.getProperty("os.name").contains("Windows")) {
			args.add("-c");
			args.add("windows.xml");
		}
	}
	
	@Test public void misplacedTest() throws Exception 
	{
		args.add("shadow/test/doctool/Misplaced.shadow");
		DocumentationTool.document(args.toArray(new String[] { }));
	}
	
	@Test public void directiveParsingTest() throws Exception
	{
		String text = 	"This method is a beautiful thing\n" +
						"@param example An import parameter that\n" +
						"truly defines the world around it.\n" +
						"@throws fakeException This indicates a tragic failure\n" +
						"@fake this isn't a real directive!\n" +
						"@numbers1234 isn't real either";
		
		DirectiveParser.process(text);
	}
}
