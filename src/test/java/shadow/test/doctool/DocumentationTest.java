package shadow.test.doctool;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import shadow.doctool.DocumentationTool;

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
		DocumentationTool.main(args.toArray(new String[] { }));
	}	
}
