package shadow.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import shadow.parser.javacc.ParseException;

public abstract class BaseTest {
	protected String baseDir = null;
	private String testName = null;

	public BaseTest(String baseDir) {
		this.baseDir = baseDir;
		this.testName = this.getClass().getSimpleName();
	}
	
	public BaseTest(String baseDir, boolean debug, boolean dump) {
		this.baseDir = baseDir;
		this.testName = this.getClass().getSimpleName();
	}
	
	/**
	 * Test everything under this cur directory
	 * @throws IOException
	 */
	protected void testAll() throws IOException, ParseException {
		testAll(new File(baseDir));
	}
	
	protected void testAll(File testDirs) throws IOException, ParseException {
	    ArrayList<File> fileList = new ArrayList<File>();
	    
	    // we now have a list of all files
	    visitDirectory(testDirs, fileList);
	    
	    for(File f:fileList) {
	    	runTest(f);
	    }
	}
	
	protected void visitDirectory(File f, ArrayList<File> fileList) throws IOException {
		File[] files = f.listFiles();
		
		for(File cf:files) {
			if(cf.isDirectory())
				visitDirectory(cf, fileList);	// recurse
			else if(cf.getCanonicalPath().endsWith(".shadow"))
				fileList.add(cf);
		}
	}
	
	protected abstract void runTest(File sourceFile) throws ParseException;
	
	/**
	 * Given a file, a result, and the time of the test, pretty prints it
	 * @param fileName The name of the file being tested
	 * @param result The result of the test
	 * @param time The time, in ms, it took
	 */
	protected void printResult(String fileName, String result, long time) {
        int fileLength = fileName.length();
		
        System.out.print(testName + "\t" + fileName);
        
        // poor mans pretty printer
        while((60 - fileLength) > 0) {
      	  System.out.print(" ");
      	  fileLength++;
        }

        System.out.println(result + " " + time + "ms");
	}
}
