/**
 * 
 */
package shadow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.logging.Log;

import shadow.output.llvm.LLVMOutput;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACBuilder;
import shadow.tac.TACModule;
import shadow.typecheck.TypeChecker;
import shadow.typecheck.type.Type;


/**
 * @author wspeirs
 *
 */
public class Main {
	/*
	 * These are the error codes returned by the compiler
	 */
	public static final int NO_ERROR		 =  0;
	public static final int GENERAL_ERROR	 = -1;
	public static final int PARSE_ERROR		 = -2;
	public static final int TYPE_CHECK_ERROR = -3;
	public static final int TAC_ERROR		 = -4;
	public static final int COMPILE_ERROR	 = -5;

	private static final Log logger = Loggers.SHADOW;

	/**
	 * This is the starting point of the compiler.
	 * 
	 * @param args Command line arguments to control the compiler
	 */
	public static void main(String[] args) {
		int ret = test(args);

		if(ret != NO_ERROR)
			System.exit(ret);
	}

	/**
	 * Used for unit tests, provides a return value.
	 * @param args arguments
	 * @return error
	 */
	public static int test(String[] args) {
		Configuration config = Configuration.getInstance();

		try {
			// create our command-line options
			Options options = Configuration.createCommandLineOptions();
			CommandLineParser cliParser = new PosixParser();
			CommandLine commandLine = cliParser.parse(options, args);

			// see if we should print the help
			if(commandLine.hasOption("h")) {
				HelpFormatter helpFormatter = new HelpFormatter();

				helpFormatter.printHelp("shadow", options);

				return NO_ERROR;
			}

			// parse out the command line
			if(!config.parse(commandLine)) {
				logger.error("Command line parse error");
				return GENERAL_ERROR;
			}

		} catch (org.apache.commons.cli.ParseException e) {
			System.err.println("COMMAND LINE ERROR: " + e.getLocalizedMessage());
			e.printStackTrace();
			return GENERAL_ERROR;
		} catch (ShadowException e) {
			System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());
			return GENERAL_ERROR;
		}

		TypeChecker checker = new TypeChecker(false);
		TACBuilder tacBuilder = new TACBuilder();
		List<String> linkCommand = new ArrayList<String>();
		linkCommand.add("llvm-link");
		linkCommand.add("-");
	linkCommand.add("shadow/Unwind" + config.getArch() + ".ll");
		linkCommand.add("shadow/" + config.getOs() + ".ll");
		String mainClass = null;

		try
		{
			// loop through the source files, compiling them
			while(config.hasNext())
			{
				File shadowFile = config.next();
				checker.setCurrentFile(shadowFile);

				//FileInputStream sourceStream = new FileInputStream(shadowFile);
				//ShadowParser parser = new ShadowParser(sourceStream);

				logger.info("Compiling " + shadowFile.getName());

				// get the start time for the compile
				long startTime = System.currentTimeMillis();

				// parse the file
				/*
				SimpleNode node = parser.CompilationUnit();

				if(Loggers.TYPE_CHECKER.isTraceEnabled())
					node.dump("");
				*/
				// type check the AST
				Node node = checker.typeCheck(shadowFile);

				if(node == null) {
					logger.error(shadowFile.getPath() + " FAILED TO TYPE CHECK");

					return TYPE_CHECK_ERROR;
				}

				// we are only parsing & type checking
				if(config.isCheckOnly()) 
				{
					long stopTime = System.currentTimeMillis();

					System.err.println("FILE " + shadowFile.getPath() + " CHECKED IN " + (stopTime - startTime) + "ms");
				}
				else
				{
					for(TACModule module : tacBuilder.build(node))
					{
						if (mainClass == null)
							mainClass = module.getType().getMangledName();
						System.out.println(module);

						// build the TAC
						new LLVMOutput(true).build(module);

						// verify the TAC
						new LLVMOutput(false).build(module);

						// write to file
						String name = module.getName().replace(':', '$');
						File llvmFile = new File(shadowFile.getParent(), name + ".ll");
						new LLVMOutput(llvmFile).build(module);
						if (llvmFile.exists())
							linkCommand.add(llvmFile.getPath());
						File nativeFile = new File(shadowFile.getParent(), name + ".native.ll");
						if (nativeFile.exists())
							linkCommand.add(nativeFile.getPath());
					}

					long stopTime = System.currentTimeMillis();

					System.err.println("COMPILED " + shadowFile.getPath() + " in " + (stopTime - startTime) + "ms");
				}

				Type.clearTypes();
			}
			if (!config.isCheckOnly())
			{
				String target;
				List<String> assembleCommand = new ArrayList<String>();
				assembleCommand.add("gcc");
				assembleCommand.add("-x");
				assembleCommand.add("assembler");
				assembleCommand.add("-");
				if (config.getOs().equals("Linux")) {
					target = "x86_64-gnu-linux";
					assembleCommand.add("-lm");
					assembleCommand.add("-lrt");
				} else if (System.getProperty("os.name").equals("Linux")) {
					target = "i686-w64-mingw32";
					assembleCommand.set(0, System.getProperty("user.home") + "/.wine/drive_c/MinGW/bin/gcc.exe");
				} else {
					target = "i386-unknown-mingw32";
				}
				BufferedReader main = new BufferedReader(new FileReader("shadow/Main.ll"));
				Process link = new ProcessBuilder(linkCommand).redirectError(Redirect.INHERIT).start();
				Process optimize = new ProcessBuilder("opt", "-mtriple", target, "-O3").redirectError(Redirect.INHERIT).start();
				Process compile = new ProcessBuilder("llc", "-mtriple", target, "-O3")/*.redirectOutput(new File("a.s"))*/.redirectError(Redirect.INHERIT).start();
				Process assemble = new ProcessBuilder(assembleCommand).redirectOutput(Redirect.INHERIT).redirectError(Redirect.INHERIT).start();
				try {
					new Pipe(link.getInputStream(), optimize.getOutputStream()).start();
					new Pipe(optimize.getInputStream(), compile.getOutputStream()).start();
					new Pipe(compile.getInputStream(), assemble.getOutputStream()).start();
					String line = main.readLine();
					while (line != null) {
						line = line.replace("_Pshadow_Ptest_CTest", mainClass) + System.getProperty("line.separator");
						link.getOutputStream().write(line.getBytes());
						line = main.readLine();
					}
					try {
						main.close();
					} catch (IOException ex) { }
					try {
						link.getOutputStream().flush();
					} catch (IOException ex) { }
					try {
						link.getOutputStream().close();
					} catch (IOException ex) { }
					if (link.waitFor() != 0) {
						System.err.println("FAILED TO LINK");
						return COMPILE_ERROR;
					}
					if (optimize.waitFor() != 0) {
						System.err.println("FAILED TO OPTIMIZE");
						return COMPILE_ERROR;
					}
					if (compile.waitFor() != 0) {
						System.err.println("FAILED TO COMPILE");
						return COMPILE_ERROR;
					}
					if (assemble.waitFor() != 0) {
						System.err.println("FAILED TO ASSEMBLE");
						return COMPILE_ERROR;
					}
				} catch (InterruptedException ex) {
				} finally {
					link.destroy();
					optimize.destroy();
					compile.destroy();
					assemble.destroy();
				}
			}
		} catch(FileNotFoundException fnfe) {
			System.err.println("FILE " + checker.getCurrentFile() + ") NOT FOUND: " + fnfe.getLocalizedMessage());
			return GENERAL_ERROR;
		} catch(ParseException pe) {
			System.err.println("PARSE ERROR " + checker.getCurrentFile() + ": " + pe.getLocalizedMessage());
			return PARSE_ERROR;
		} catch (ShadowException e) {
			System.err.println("ERROR ON FILE " + checker.getCurrentFile() + ": " + e.getLocalizedMessage());
			e.printStackTrace();
			return TYPE_CHECK_ERROR;
		} catch (IOException e)	{
			System.err.println("FILE DEPENDENCY ERROR " + checker.getCurrentFile() + ": " + e.getLocalizedMessage());
			e.printStackTrace();
			return TYPE_CHECK_ERROR;
		}

		return NO_ERROR;
	}
	private static class Pipe extends Thread {
		private InputStream input;
		private OutputStream output;
		public Pipe(InputStream inputStream, OutputStream outputStream) {
			input = inputStream;
			output = outputStream;
		}
		@Override
		public void run() {
			try {
				try {
					byte[] buffer = new byte[1024];
					int read = input.read(buffer);
					while (read >= 0) {
						output.write(buffer, 0, read);
						read = input.read(buffer);
					}
				} finally {
					try {
						input.close();
					} catch (IOException ex) { }
					try {
						output.flush();
					} catch (IOException ex) { }
					try {
						output.close();
					} catch (IOException ex) { }
				}
			} catch (IOException ex) { }
		}
	}
}
