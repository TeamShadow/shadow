package shadow.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import shadow.parser.javacc.ShadowException;

public abstract class BaseTest {
	protected String baseDir = null;
	private String testName = null;
	private static final int screeWidth = 70;

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
	protected void testAll() throws IOException, ShadowException {
		testAll(new File(baseDir));
	}
	
	protected void testAll(File testDirs) throws IOException, ShadowException {
	    ArrayList<File> fileList = new ArrayList<File>();
	    
	    // it's only 1 file
	    if(testDirs.isFile()) {
	    	runTest(testDirs);
	    	return;
	    }
	    
	    // we now have a list of all files
	    visitDirectory(testDirs, fileList);
	    
	    for(File f:fileList) {
	    	runTest(f);
	    }
	}
	
	protected void visitDirectory(File f, ArrayList<File> fileList) throws IOException {
		File[] files = f.listFiles();
		
		if(files == null)
			throw new IOException("No files found: " + f.getAbsolutePath());
		
		for(File cf:files) {
			if(cf.isDirectory())
				visitDirectory(cf, fileList);	// recurse
			else if(cf.getCanonicalPath().endsWith(".shadow"))
				fileList.add(cf);
		}
	}
	
	protected abstract void runTest(File sourceFile) throws ShadowException;
	
	/**
	 * Given a file, a result, and the time of the test, pretty prints it
	 * @param fileName The name of the file being tested
	 * @param result The result of the test
	 * @param time The time, in ms, it took
	 */
	protected void printResult(String fileName, String result, long time) {
        int prefixLength = fileName.length() + testName.length();
		
        System.out.print(testName + "  " + fileName);
        
        // poor mans pretty printer
        while((screeWidth - prefixLength) > 0) {
      	  System.out.print(" ");
      	prefixLength++;
        }

        System.out.println(result + " " + time + "ms");
	}
}
