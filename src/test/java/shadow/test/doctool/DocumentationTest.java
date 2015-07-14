package shadow.test.doctool;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shadow.doctool.Documentation;
import shadow.doctool.DocumentationBuilder;
import shadow.doctool.DocumentationException;
import shadow.doctool.DocumentationTool;
import shadow.doctool.tag.ParserManager.ArgDescriptionParser;
import shadow.doctool.tag.ParserManager.DelimitedParser;
import shadow.doctool.tag.ParserManager.TagParser;
import shadow.doctool.tag.TagManager.BlockTagType;
import shadow.doctool.tag.TagManager.InlineTagType;
import shadow.doctool.tag.TagManager.TagType;

public class DocumentationTest 
{
	private static final Path outputDirectory = Paths.get("shadow", "test", "doctool", "docs");
	private ArrayList<String> args = new ArrayList<String>();
	
	@Before
	public void setup() throws Exception 
	{
		try {
			FileUtils.deleteDirectory(outputDirectory.toFile());
		} catch(Exception e) {}
		
		if (System.getProperty("os.name").contains("Windows")) {
			args.add("-c");
			args.add("windows.xml");
		}
		
		args.add("-o");
		System.out.println(outputDirectory.toString());
		args.add(outputDirectory.toString());
	}
	
	@After
	public void cleanup()
	{	
		// Try to remove the documentation directory
		try {
			FileUtils.deleteDirectory(outputDirectory.toFile());
		} catch(Exception e) {}
	}
	
	/**
	 * This test should only fail if the documentation tool can't properly
	 * ignore misplaced documentation comments
	 */
	@Test public void misplacedTest() throws Exception 
	{
		args.add("shadow/test/doctool/Misplaced.shadow");
		DocumentationTool.document(args.toArray(new String[] { }));
	}

	@Test public void directiveParsingTest() throws Exception
	{
		String text = 	"This method accomplishes meaningful tasks\n" +
						"@param example An important parameter that\n" +
						"is crucial to this method.\n" +
						"@throws fakeException This indicates a serious failure\n" +
						"@fake this isn't a real directive!\n" +
						"@numbers1234 isn't real either";
		DocumentationBuilder documentation = new DocumentationBuilder();
		documentation.addBlock(text);
		
		Documentation result = documentation.process();
		String mainText = result.getMainText();
		
		assertEquals("This method accomplishes meaningful tasks", mainText);
		
		/*
		// Directive type assertions
		assertEquals(DirectiveParser.DirectiveType.PARAM, directives.get(0).getType());
		assertEquals(DirectiveParser.DirectiveType.THROWS, directives.get(1).getType());
		
		// Directive content assertions
		assertEquals("example", directives.get(0).getArgument(0));
		assertEquals("An important parameter that is crucial to this method.",
				directives.get(0).getDescription());
		assertEquals("fakeException", directives.get(1).getArgument(0));
		assertEquals("This indicates a serious failure @fake this isn't a real "
				+ "directive! @numbers1234 isn't real either",
				directives.get(1).getDescription());
		*/
	}
	
	@Test public void argDescriptionParserTest() throws Exception
	{
		TagParser parser = new ArgDescriptionParser(3, true);
		
		List<String> results = parser.parse("\tfirstArg secondOne \tthirdOne and a \tdescription!    ");
		assertEquals(4, results.size());
		assertEquals("firstArg", results.get(0));
		assertEquals("secondOne", results.get(1));
		assertEquals("thirdOne", results.get(2));
		assertEquals("and a description!", results.get(3));
		
		parser = new ArgDescriptionParser(2, false);
		
		try {
			results = parser.parse("this won't work");
			throw new Exception("Excess text was not caught");
		} catch (DocumentationException e) { }
		
		results = parser.parse("one-and-a \ttwo");
		assertEquals(2, results.size());
		assertEquals("one-and-a", results.get(0));
		assertEquals("two", results.get(1));
	}
	
	@Test public void delimitedParserTest() throws Exception
	{
		TagParser parser = new DelimitedParser(',');
		
		List<String> results = parser.parse(" John Doe, Jane Eyre  , Mary\tShelly,,,\t");
		assertEquals(3, results.size());
		assertEquals("John Doe", results.get(0));
		assertEquals("Jane Eyre", results.get(1));
		assertEquals("Mary Shelly", results.get(2));
		
		try {
			parser = new DelimitedParser(' ');
			throw new Exception("Whitespace delimiter was not caught");
		} catch (IllegalArgumentException e) { }
	}
	
	@Test public void typeRetrievalTest() throws Exception
	{
		TagType type = BlockTagType.getType("author");
		assertEquals(BlockTagType.AUTHOR, type);
		type = BlockTagType.getType("param");
		assertEquals(BlockTagType.PARAM, type);
		type = BlockTagType.getType("throws");
		assertEquals(BlockTagType.THROWS, type);
		type = BlockTagType.getType("FAKE_TAG");
		assertEquals(BlockTagType.INVALID_BLOCK_TAG, type);
		
		type = InlineTagType.getType("see");
		assertEquals(InlineTagType.SEE, type);
		type = InlineTagType.getType("FAKE_TAG");
		assertEquals(InlineTagType.INVALID_INLINE_TAG, type);
	}
}