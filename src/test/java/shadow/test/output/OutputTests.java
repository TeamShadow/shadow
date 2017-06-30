package shadow.test.output;

import static junit.framework.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shadow.Configuration;
import shadow.ConfigurationException;
import shadow.Job;
import shadow.Main;

public class OutputTests {
	// To simplify removal, every unit test executable will have the same name
	private static final String executableName = Job.properExecutableName("OutputTest");
	private static final Path executable = Paths.get("shadow", "test", executableName);

	private ArrayList<String> args = new ArrayList<String>();
	
	@Before
	public void setup() throws Exception {
		args.add("-o");
		args.add(executableName);
		
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		
		String os = System.getProperty("os.name").toLowerCase();
		
		args.add("-c");
		if( os.contains("windows") )
			args.add("windows.xml");		
		else if( os.contains("mac") )
			args.add("mac.xml");
		else
			args.add("linux.xml");
		
		//args.add("-r");
	}
	
	@After
	public void cleanup() throws IOException {
		
		// Try to remove the unit test executable
		try {			
			Files.delete(executable);
		}
		catch(Exception e) {}
	}
	
	private void run(String[] programArgs, String expectedOutput) throws IOException, ConfigurationException, InterruptedException {
		run( programArgs, expectedOutput, "" ); 			
	}
	
	private void run(String[] programArgs, String expectedOutput, String expectedError) throws IOException, ConfigurationException, InterruptedException {
		run( programArgs, expectedOutput, expectedError, 0 ); 			
	}
	
	private void run(String[] programArgs, String expectedOutput, String expectedError, int expectedReturn ) throws IOException, ConfigurationException, InterruptedException {
		
		// Should be initialized at this point by call to Main.run()
		Configuration config = Configuration.getConfiguration();
		
		Path fullExecutable = config.getSystemImport().resolve(executable);
		
		List<String> programCommand = new ArrayList<String>();
		programCommand.add(fullExecutable.toAbsolutePath().toString());
		
		for (String arg : programArgs)
			programCommand.add(arg);
		
		Process program = new ProcessBuilder(programCommand).start();
		
		//regular output
		BufferedReader reader = new BufferedReader(new InputStreamReader(program.getInputStream()));
		BufferedReader errorReader = new BufferedReader(new InputStreamReader(program.getErrorStream()));
		StringBuilder builder = new StringBuilder();
		String line;
		do {
			line = reader.readLine();
			if (line != null)
				builder.append(line).append('\n');
		} while (line != null);		
		String output = builder.toString();
		assertEquals(expectedOutput, output);
		
		//error output		
		builder = new StringBuilder();
		do {
			line = errorReader.readLine();
			if (line != null)
				builder.append(line).append('\n');
		} while (line != null);
		
		String error = builder.toString();	
		assertEquals(expectedError, error);		
		
		//check return value to see if the program ends normally
		//also keeps program from being deleted while running	
		assertEquals("Program exited abnormally.", expectedReturn, program.waitFor()); 
	}	

	private String formatOutputString(CharSequence... elements)
	{
		return String.join("\n", elements) + "\n";
	}
	
	@Test public void testAddressMap() throws Exception {
		args.add("shadow/test/AddressMapTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],	
				"Added key: 47 Value: 1\n" + 
				"Added key: 41 Value: 2\n" + 
				"Added key: 35 Value: 3\n" + 
				"Added key: 29 Value: 4\n" + 
				"Added key: 23 Value: 5\n" + 
				"Added key: 17 Value: 6\n" + 
				"Added key: 11 Value: 7\n" + 
				"Added key: 5 Value: 8\n" + 
				"Added key: 52 Value: 9\n" + 
				"Added key: 46 Value: 10\n" + 
				"Added key: 40 Value: 11\n" + 
				"Added key: 34 Value: 12\n" + 
				"Added key: 28 Value: 13\n" + 
				"Added key: 22 Value: 14\n" + 
				"Added key: 16 Value: 15\n" + 
				"Added key: 10 Value: 16\n" + 
				"Added key: 4 Value: 17\n" + 
				"Added key: 51 Value: 18\n" + 
				"Added key: 45 Value: 19\n" + 
				"Added key: 39 Value: 20\n" + 
				"Added key: 33 Value: 21\n" + 
				"Added key: 27 Value: 22\n" + 
				"Added key: 21 Value: 23\n" + 
				"Added key: 15 Value: 24\n" + 
				"Added key: 9 Value: 25\n" + 
				"Added key: 3 Value: 26\n" + 
				"Added key: 50 Value: 27\n" + 
				"Added key: 44 Value: 28\n" + 
				"Added key: 38 Value: 29\n" + 
				"Added key: 32 Value: 30\n" + 
				"Added key: 26 Value: 31\n" + 
				"Added key: 20 Value: 32\n" + 
				"Added key: 14 Value: 33\n" + 
				"Added key: 8 Value: 34\n" + 
				"Added key: 2 Value: 35\n" + 
				"Added key: 49 Value: 36\n" + 
				"Added key: 43 Value: 37\n" + 
				"Added key: 37 Value: 38\n" + 
				"Added key: 31 Value: 39\n" + 
				"Added key: 25 Value: 40\n" + 
				"Added key: 19 Value: 41\n" + 
				"Added key: 13 Value: 42\n" + 
				"Added key: 7 Value: 43\n" + 
				"Added key: 1 Value: 44\n" + 
				"Added key: 48 Value: 45\n" + 
				"Added key: 42 Value: 46\n" + 
				"Added key: 36 Value: 47\n" + 
				"Added key: 30 Value: 48\n" + 
				"Added key: 24 Value: 49\n" + 
				"Added key: 18 Value: 50\n" + 
				"Added key: 12 Value: 51\n" + 
				"Added key: 6 Value: 52\n" + 
				"Added key: 0 Value: 53\n" + 
				"Contains key 43!\n" + 
				"Does not contain 89!\n" + 
				"Value at 43 is: 37\n" + 
				"Value at 17 is: 6\n" + 
				"Value at 100 is: null\n" + 
				"#1: 0\n" + 
				"#2: 44\n" + 
				"#3: 35\n" + 
				"#4: 5\n" + 
				"#5: 17\n" + 
				"#6: 8\n" + 
				"#7: 52\n" + 
				"#8: 43\n" + 
				"#9: 34\n" + 
				"#10: 25\n" + 
				"#11: 16\n" + 
				"#12: 7\n" + 
				"#13: 51\n" + 
				"#14: 42\n" + 
				"#15: 33\n" + 
				"#16: 24\n" + 
				"#17: 6\n" + 
				"#18: 15\n" + 
				"#19: 41\n" + 
				"#20: 50\n" + 
				"#21: 23\n" + 
				"#22: 32\n" + 
				"#23: 5\n" + 
				"#24: 14\n" + 
				"#25: 40\n" + 
				"#26: 49\n" + 
				"#27: 22\n" + 
				"#28: 31\n" + 
				"#29: 4\n" + 
				"#30: 13\n" + 
				"#31: 39\n" + 
				"#32: 48\n" + 
				"#33: 12\n" + 
				"#34: 3\n" + 
				"#35: 30\n" + 
				"#36: 21\n" + 
				"#37: 29\n" + 
				"#38: 20\n" + 
				"#39: 47\n" + 
				"#40: 38\n" + 
				"#41: 46\n" + 
				"#42: 37\n" + 
				"#43: 11\n" + 
				"#44: 2\n" + 
				"#45: 10\n" + 
				"#46: 1\n" + 
				"#47: 28\n" + 
				"#48: 19\n" + 
				"#49: 18\n" + 
				"#50: 27\n" + 
				"#51: 36\n" + 
				"#52: 45\n" + 
				"#53: 9\n");
	}
	
	@Test public void testArray() throws Exception {
		args.add("shadow/test/ArrayTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"[0, 2, four, 88, shadow:standard@Object]\n" + 
				"[2, four, 88]\n" + 
				"[0, 1, 2, 3, 4]\n" + 
				"[zero, one, two]\n");			
	}
	
	@Test public void testArrayAsObject() throws Exception {
		args.add("shadow/test/ArrayAsObject.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Size: 135\n");	
	}
		
	@Test public void testArrayCreate() throws Exception {
		args.add("shadow/test/ArrayCreateTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 
				"a[0]: \n" +
						"a[1]: \n" +
						"a[2]: \n" +
						"a[3]: \n" +
						"a[4]: \n" +
						"a[5]: \n" +
						"a[6]: \n" +
						"a[7]: \n" +
						"a[8]: \n" +
						"a[9]: \n" +
						"b[0]: Test\n" +
						"b[1]: Test\n" +
						"b[2]: Test\n" +
						"b[3]: Test\n" +
						"b[4]: Test\n" +
						"b[5]: Test\n" +
						"b[6]: Test\n" +
						"b[7]: Test\n" +
						"b[8]: Test\n" +
						"b[9]: Test\n" +
						"c[0]: 5\n" +
						"c[1]: 5\n" +
						"c[2]: 5\n" +
						"c[3]: 5\n" +
						"c[4]: 5\n" +
						"c[5]: 5\n" +
						"c[6]: 5\n" +
						"c[7]: 5\n" +
						"c[8]: 5\n" +
						"c[9]: 5\n" +
						"d[0]: 1\n" +
						"d[1]: 2\n" +
						"d[2]: 3\n" +
						"d[3]: 4\n" +
						"d[4]: 5\n" +
						"d[5]: 6\n" +
						"d[6]: 7\n" +
						"d[7]: 8\n" +
						"d[8]: 9\n" +
						"d[9]: 10\n" +
						"e[0][0]: 1\n" +
						"e[0][1]: 2\n" +
						"e[0][2]: 3\n" +
						"e[0][3]: 4\n" +
						"e[0][4]: 5\n" +
						"e[1][0]: 6\n" +
						"e[1][1]: 7\n" +
						"e[1][2]: 8\n" +
						"e[1][3]: 9\n" +
						"e[1][4]: 10\n" +
						"e[2][0]: 11\n" +
						"e[2][1]: 12\n" +
						"e[2][2]: 13\n" +
						"e[2][3]: 14\n" +
						"e[2][4]: 15\n");
	}
	

	
	@Test public void testArrayCast() throws Exception {
		args.add("shadow/test/ArrayCastTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 				
				"Passed\n" +  
				"Passed: shadow:standard@CastException: Type int[] is not a subtype of short[]\n" + 
				"Passed: shadow:standard@CastException: Type nullable shadow:standard@Object[] is not a subtype of shadow:standard@Object[]\n" +
				"Passed: shadow:standard@CastException: Type shadow:standard@String[] is not a subtype of nullable shadow:standard@String[]\n");

	}
	
	@Test public void testArrayDefault() throws Exception {
		args.add("shadow/test/ArrayDefaultTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 
				"a[0]: Wombat\n" +
						"a[1]: Wombat\n" +
						"a[2]: Wombat\n" +
						"a[3]: Wombat\n" +
						"a[4]: Wombat\n" +
						"b[0]: 42\n" +
						"b[1]: 42\n" +
						"b[2]: 42\n" +
						"b[3]: 42\n" +
						"b[4]: 42\n" +
						"c[0]: shadow:standard@Object\n" +
						"c[1]: shadow:standard@Object\n" +
						"c[2]: shadow:standard@Object\n" +
						"c[3]: shadow:standard@Object\n" +
						"c[4]: shadow:standard@Object\n");
	}
	

	
	@Test public void testArrayDeque() throws Exception {
		args.add("shadow/test/ArrayDequeTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Deserves\n" + 
				"Good\n" + 
				"Every\n" + 
				"Boy\n" + 
				"Fudge\n");
	}
	
	@Test public void testArrayInitializer() throws Exception {
		args.add("shadow/test/ArrayInitializerTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"[1, 4, 9, 16, 25]\n" + 
				"[donut, explanation, story, book, 25]\n" +
				"[[9, 8, 7], [12, 5, 4], [3, 2, 1]]\n" +
				"[a, e, i, o, y]\n" +
				"[[snap, crackle, pop], [tip, top], [taste, the, rainbow]]\n" +
				"[[snap, crackle, pop], [cranberry, jamboplexy, in, place], [taste, the, rainbow]]\n");
	}
	
	@Test public void testArrayList() throws Exception {
		args.add("shadow/test/ArrayListTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[] {"all", "good", "men", "shall", "perish"},
			"true\n" +
			"true\n" +
			"false\n" +
			"all\n" +
			"men\n" +
			"shall\n" +
			"[all, men, shall]\n" + 
			"[4, 16, 25, 36, 49, 64, 81, 100, 121, 144, 169, 196, 225]\n");
	}
	
	@Test public void testArrayOutOfBounds() throws Exception {
		args.add("shadow/test/ArrayOutOfBoundsTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"shadow:standard@IndexOutOfBoundsException: Index 16\n" + 
				"shadow:standard@IndexOutOfBoundsException: Index -1\n" +				
				"shadow:standard@IndexOutOfBoundsException: Index 9\n" +
				"shadow:standard@IndexOutOfBoundsException: Index 9\n" +
				"shadow:standard@IndexOutOfBoundsException: Index 28\n");
	}
	

	@Test public void testAssert() throws Exception {
		args.add("shadow/test/AssertTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"shadow:standard@AssertException: Too small!\n" + 
				"shadow:standard@AssertException\n");
	}
	
	@Test public void testAssignment() throws Exception {
		args.add("shadow/test/AssignmentTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"x: 1\n" + 
				"array: [2, 0, 0, 0, 0, 0, 0, 0, 0, 0]\n" +
				"trouble: {9=3}\n" +
				"array: [0, 0, 0, 4, 0, 0, 0, 0, 0, 0]\n" + 
				"array: [8, 6, 0, 0, 0, 0, 0, 0, 0, 0]\n");
	}

	@Test public void testBasic() throws Exception {
		args.add("shadow/test/BasicTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Count it off!\n" + 
				"I say 1\n" +
				"I say 2\n" +
				"I say 3\n" +
				"[Help me]\n" +
				"125\n");
	}
	
	@Test public void testBigInteger() throws Exception {
		args.add("shadow/test/BigIntegerTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 
				"0: 2\n" + 
				"1: 4\n" + 
				"2: 8\n" + 
				"3: 16\n" + 
				"4: 32\n" + 
				"5: 64\n" + 
				"6: 128\n" + 
				"7: 256\n" + 
				"8: 512\n" + 
				"9: 1024\n" + 
				"10: 2048\n" + 
				"11: 4096\n" + 
				"12: 8192\n" + 
				"13: 16384\n" + 
				"14: 32768\n" + 
				"15: 65536\n" + 
				"16: 131072\n" + 
				"17: 262144\n" + 
				"18: 524288\n" + 
				"19: 1048576\n" + 
				"20: 2097152\n" + 
				"21: 4194304\n" + 
				"22: 8388608\n" + 
				"23: 16777216\n" + 
				"24: 33554432\n" + 
				"25: 67108864\n" + 
				"26: 134217728\n" + 
				"27: 268435456\n" + 
				"28: 536870912\n" + 
				"29: 1073741824\n" + 
				"30: 2147483648\n" + 
				"31: 4294967296\n" + 
				"32: 8589934592\n" + 
				"33: 17179869184\n" + 
				"34: 34359738368\n" + 
				"35: 68719476736\n" + 
				"36: 137438953472\n" + 
				"37: 274877906944\n" + 
				"38: 549755813888\n" + 
				"39: 1099511627776\n" + 
				"40: 2199023255552\n" + 
				"41: 4398046511104\n" + 
				"42: 8796093022208\n" + 
				"43: 17592186044416\n" + 
				"44: 35184372088832\n" + 
				"45: 70368744177664\n" + 
				"46: 140737488355328\n" + 
				"47: 281474976710656\n" + 
				"48: 562949953421312\n" + 
				"49: 1125899906842624\n" + 
				"50: 2251799813685248\n" + 
				"51: 4503599627370496\n" + 
				"52: 9007199254740992\n" + 
				"53: 18014398509481984\n" + 
				"54: 36028797018963968\n" + 
				"55: 72057594037927936\n" + 
				"56: 144115188075855872\n" + 
				"57: 288230376151711744\n" + 
				"58: 576460752303423488\n" + 
				"59: 1152921504606846976\n" + 
				"60: 2305843009213693952\n" + 
				"61: 4611686018427387904\n" + 
				"62: 9223372036854775808\n" + 
				"63: 18446744073709551616\n" + 
				"64: 36893488147419103232\n" + 
				"65: 73786976294838206464\n" + 
				"66: 147573952589676412928\n" + 
				"67: 295147905179352825856\n" + 
				"68: 590295810358705651712\n" + 
				"69: 1180591620717411303424\n" + 
				"70: 2361183241434822606848\n" + 
				"71: 4722366482869645213696\n" + 
				"72: 9444732965739290427392\n" + 
				"73: 18889465931478580854784\n" + 
				"74: 37778931862957161709568\n" + 
				"75: 75557863725914323419136\n" + 
				"76: 151115727451828646838272\n" + 
				"77: 302231454903657293676544\n" + 
				"78: 604462909807314587353088\n" + 
				"79: 1208925819614629174706176\n" + 
				"80: 2417851639229258349412352\n" + 
				"81: 4835703278458516698824704\n" + 
				"82: 9671406556917033397649408\n" + 
				"83: 19342813113834066795298816\n" + 
				"84: 38685626227668133590597632\n" + 
				"85: 77371252455336267181195264\n" + 
				"86: 154742504910672534362390528\n" + 
				"87: 309485009821345068724781056\n" + 
				"88: 618970019642690137449562112\n" + 
				"89: 1237940039285380274899124224\n" + 
				"90: 2475880078570760549798248448\n" + 
				"91: 4951760157141521099596496896\n" + 
				"92: 9903520314283042199192993792\n" + 
				"93: 19807040628566084398385987584\n" + 
				"94: 39614081257132168796771975168\n" + 
				"95: 79228162514264337593543950336\n" + 
				"96: 158456325028528675187087900672\n" + 
				"97: 316912650057057350374175801344\n" + 
				"98: 633825300114114700748351602688\n" + 
				"99: 1267650600228229401496703205376\n" + 
				"a: 458248854125127692644763689697630452603077525667039488167595\n" + 
				"b: 424527578038877065750254364514248265552431685221767\n" + 
				"a + b: 458248854549655270683640755447884817117325791219471173389362\n" + 
				"a - b: 458248853700600114605886623947376088088829260114607802945828\n" + 
				"a * b: 194539276180831139131011253850959240145863762295190414514980438839514500513512702883553825017239414420038040365\n" + 
				"a / b: 1079432474\n" + 
				"a % b: 281394553380105954880717639067607214972118296706037\n" + 
				"a / b: 3\n" + 
				"a / b: 3\n" + 
				"a / b: 4294967295\n" + 
				"a / b: 5599413508\n" + 
				"a / b: 611612530\n" + 
				"a / b: 4159831672\n" + 
				"a / b: 873727415\n" + 
				"n: 2195521454340267086741763887146703421403138168590319536395743165882538840103478757954437458467991587781634444546316100430675861955984773682480893416347615036033833354001156644056162483366839982619823648021770903663979679691296438211023119129944865865532679111362655761233794694594519636426946285142324520755866494927472412250065210781235421203259580302777998048873938210795799807855003878822406629289\n" + 
				"C: 1112956698262836806664794820794473961145010180577601125590707755951029843006749551702925760533038042622692901510299809647917425905134514887570764862158562662534883462854826818855263583936781681828017953722260950237915404296241216983813592369263412058474774469738265051177236933818586309402181524305722641225977290699659457953607880969651822747591468484978977768458753917979021465084394384421346410164\n" + 
				"Decrypted M: 123456789101112131415161718\n");
	}

	@Test public void testCastException() throws Exception {
		args.add("shadow/test/CastExceptionTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Cast from String to String\n" + 
				"shadow:standard@CastException: Type shadow:standard@Object is not a subtype of shadow:standard@String\n" +
				"Cast from String to CanIterate<code>\n" +
				"Cast from String to CanEqual<String>\n" +
				"shadow:standard@CastException: Class shadow:standard@Object does not implement interface shadow:standard@CanIterate<code>\n");
	}

	
	@Test public void testChild() throws Exception {
		args.add("shadow/test/ChildTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"shadow:test@ParentTest:create(\"Hello World!\")\n" + 
				"shadow:test@ChildTest:main([])\n");
	}
	
	@Test public void testCollidingWrapper() throws Exception {
		args.add("shadow/test/CollidingWrapperTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"13\n" + 
				"8\n" + 
				"13\n" + 
				"8\n");
	}
	
	@Test public void testComplex() throws Exception {
		args.add("shadow/test/ComplexTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"a: 2 - i\n" + 
				"b: 3 + 5i\n" +
				"c: 5 + 4i\n" +
				"d: -1 - 6i\n" +
				"e: 11 + 7i\n");
	}
	
	@Test public void testCopy() throws Exception {
		args.add("shadow/test/CopyTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 				
				"s1: Walnuts\n" + 
				"s2: Walnuts\n" +
				"o1: (13,StuffStuff)\n" +
				"o2: (14,Stuff)\n" +
				"integer1: 3\n" +
				"integer2: 3\n" +
				"array1: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]\n" +
				"array2: [2, 4, 6, 8, 10, 12, 14, 16, 18, 20]\n" +
				"array3: [[1, 2, 3, 4, 5], [6, 7, 8, 9, 10], [11, 12, 13, 14, 15], [16, 17, 18, 19, 20]]\n" +
				"array4: [[2, 3, 4, 5, 6], [7, 8, 9, 10, 11], [12, 13, 14, 15, 16], [17, 18, 19, 20, 21]]\n" +
				"array5: [[0, 3, 6, 9, 12], [150, 153, 156, 159, 162], [300, 303, 306, 309, 312], [450, 453, 456, 459, 462]]\n" +
				"array6: [[0, 1, 2, 3, 4], [50, 51, 52, 53, 54], [100, 101, 102, 103, 104], [150, 151, 152, 153, 154]]\n" +
				"array7: [[[0, 2, 4], [6, 8, 10]]]\n" +
				"array8: [[[0, 1, 2], [3, 4, 5]]]\n" +
				"array9: [[0, 2, 4], [6, 8, 10, 12]]\n" +
				"array10: [[0, 1, 2], [3, 4, 5, 6]]\n" +
				"matrix1: [3.0, 2.0, 0.0]\n" +
				"[0.0, 0.0, 7.0]\n" +
				"\n" +
				"matrix2: [0.0, 2.0, 0.0]\n" +
				"[0.0, 0.0, 5.0]\n\n");
	}
	
	@Test public void testCreateStringInConstant() throws Exception {
		args.add("shadow/test/CreateStringInConstantTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], "Empty: \nNon-empty: Hello!\n");
	}
	
	@Test public void testDependentConstants() throws Exception {
		args.add("shadow/test/DependentConstantsTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], "3\n4\n5\n9\nwalnut\nwalnuts\n2\n");
	}	
	
	@Test public void testException() throws Exception {
		args.add("shadow/test/ExceptionTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"test2 caught ExceptionB\n", 
				"shadow:standard@Exception\n",
				1);
	}
	
	@Test public void testForeach() throws Exception {
		args.add("shadow/test/ForeachTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],		
				"20\n" + 
				"19\n" + 
				"18\n" + 
				"17\n" + 
				"16\n" + 
				"15\n" + 
				"14\n" + 
				"13\n" + 
				"12\n" + 
				"11\n" + 
				"10\n" + 
				"9\n" + 
				"8\n" + 
				"7\n" + 
				"6\n" + 
				"5\n" + 
				"4\n" + 
				"3\n" + 
				"2\n" + 
				"1\n" + 
				"My\n" + 
				"dog\n" + 
				"has\n" + 
				"fleas\n" + 
				"20 19 18 17 16 \n" +
				"15 14 13 12 11 \n" +
				"10 9 8 7 6 \n" +
				"5 4 3 2 1 \n"
				);
	}	
	
	@Test public void testGeneric() throws Exception {
		args.add("shadow/test/GenericTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 
			"Class: shadow:test@Container<shadow:standard@String>\n" + 
			"Contents: blub\n" + 
			"Class: shadow:test@Container<int>\n" + 
			"Contents: 17\n");
	}
	
	
	@Test public void testGenericArray() throws Exception {
		args.add("shadow/test/GenericArrayTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 
			"0 0 0 4 0 0 0 0 0 10 \n" + 
			"[0, 0, 0, 4, 0, 0, 0, 0, 0, 10]\n" +
			"[one, two, three, four, five]\n" +
			"[0, 0, 0, 4, 0, 0, 0, 0, 0, 10]\n" +
			"[null, Hey, null, null, Ho]\n");
	}
	
	@Test public void testHashMap() throws Exception {
		args.add("shadow/test/HashMapTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Passed 1\n" + 
				"Passed 2\n" +
				"Passed 3\n" +
				"Bozo\n" +
				"{Sandwich=20, Clothes=58}\n" +
				"Deal\n" +
				"Passed 4\n" +
				"Passed 5\n" +
				"Passed 6\n" +
				"Passed 7\n" +
				"Passed 8\n" +
				"Passed 9\n" +
				"Passed 10\n" + 
				"#1: 0\n" + 
				"#2: 1\n" + 
				"#3: 4\n" + 
				"#4: 16\n" + 
				"#5: 25\n" + 
				"#6: 36\n" + 
				"#7: 49\n" + 
				"#8: 64\n" + 
				"#9: 81\n" + 
				"#10: 100\n" + 
				"#11: 121\n" + 
				"#12: 144\n" + 
				"#13: 169\n" + 
				"#14: 196\n" + 
				"#15: 225\n" + 
				"#16: 289\n" + 
				"#17: 256\n" + 
				"#18: 361\n" + 
				"#19: 324\n");
	}
	
	@Test public void testHashSet() throws Exception {
		args.add("shadow/test/HashSetTest.shadow");
		Main.run(args.toArray(new String[] { }));	
		run(new String[0],
			"Passed 1\n" + 
			"Passed 2\n" + 
			"Passed 3\n" + 
			"Passed 4\n" + 
			"true\n" + 
			"{Bozo, Clown, the}\n" + 
			"Passed 5\n" + 
			"Passed 6\n" + 
			"Passed 7\n" + 
			"{0, 1, 169, 100, 64, 36, 196, 9, 225, 289, 16, 324, 256, 49, 81, 25, 144, 361, 121}\n");
	}
	
	@Test public void testInterface() throws Exception {
		args.add("shadow/test/InterfaceTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],		
			"shadow:test@Cheetah runs at 75 mph.\n" + 
			"shadow:test@Hare runs at 40 mph.\n" +
			"shadow:test@Tortoise runs slow and steady.\n" +
			"shadow:test@Tortoise runs slow and steady.\n" +
			"shadow:test@Cheetah runs at 75 mph.\n" +
			"shadow:test@Hare runs at 40 mph.\n");
	}
	
	@Test public void testInterfaceCreate() throws Exception {
		args.add("shadow/test/InterfaceCreateTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 	"", 			
				"shadow:standard@InterfaceCreateException: Cannot create interface shadow:standard@CanCreate\n",
				1);
	}
		
	
	@Test public void testIs() throws Exception {
		args.add("shadow/test/IsTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],	
			"shadow:test@ParentTest:create(\"Hello World!\")\n" + 
			"shadow:test@ParentTest:create()\n" +
			"String is String\n" +
			"String is Object\n" +
			"Object is Object\n" +
			"Object is not String\n" +
			"Tortoise is Tortoise\n" +
			"Tortoise is CanRun\n" +
			"ChildTest is ParentTest\n" +
			"ChildTest is Object\n");
	}
	
	@Test public void testLinkedList() throws Exception {
		args.add("shadow/test/LinkedListTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
			"[Fox, Socks, Box, Knox, Knox in box, Fox in socks, Knox on fox in socks in box]\n" + 
			"[Socks, Box, Knox in box, Fox in socks, Knox on fox in socks in box]\n" + 
			"Fox\n" + 
			"1\n" + 
			"[Socks, Box, Fox in socks, Knox on fox in socks in box]\n" + 
			"[2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99]\n" + 
			"15\n" + 
			"[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]\n");
	}
	
	
	@Test public void testLoop() throws Exception {
		args.add("shadow/test/LoopTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],	
			"before outer\n" +
			"try before outer\n" +
			"loop before skip\n" +
			"try before skip\n" +
			"try after skip\n" +
			"finally skip\n" +
			"loop after skip\n" +
			"loop before skip\n" +
			"try before skip\n" +
			"try after skip\n" +
			"finally skip\n" +
			"loop after skip\n" +
			"loop before continue\n" +
			"try before continue\n" +
			"finally continue\n" +
			"loop before continue\n" +
			"try before continue\n" +
			"finally continue\n" +
			"loop before break\n" +
			"try before break\n" +
			"finally break\n" +
			"try after outer\n" +
			"before return first\n" +
			"finally outer\n" +			
			"return first\n");
	}
	
	@Test public void testMatrix() throws Exception {
		args.add("shadow/test/MatrixTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
			"a:\n" + 
			"[1.0, 0.0, 0.0]\n" + 
			"[0.0, 1.0, 4.0]\n" + 
			"[0.0, 0.0, 1.0]\n" + 
			"\n" + 
			"b:\n" + 
			"[1.0, 0.0, 0.0]\n" + 
			"[0.0, 1.0, 0.0]\n" + 
			"[3.0, 0.0, 1.0]\n" + 
			"\n" + 
			"c:\n" + 
			"[2.0, 0.0, 0.0]\n" + 
			"[0.0, 2.0, 4.0]\n" + 
			"[3.0, 0.0, 2.0]\n" + 
			"\n" + 
			"d:\n" + 
			"[0.0, 0.0, 0.0]\n" + 
			"[0.0, 0.0, 4.0]\n" + 
			"[-3.0, 0.0, 0.0]\n" + 
			"\n" + 
			"e:\n" + 
			"[1.0, 0.0, 0.0]\n" + 
			"[12.0, 1.0, 4.0]\n" + 
			"[3.0, 0.0, 1.0]\n\n");
	}
	
	@Test public void testMethodOperations() throws Exception {
		args.add("shadow/test/MethodOperations.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
			"x == y\n" + 
			"x != z\n" +
			"s1 == s2\n" +
			"s1 != s3\n");
	}
	
	@Test public void testMutableString() throws Exception {
		args.add("shadow/test/MutableStringTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
			"peach\n" +
			"p\n" +
			"e\n" +
			"a\n" +
			"c\n" +
			"h\n" + 
			"hcaep\n");
	}
	
	@Test public void testNullableArray() throws Exception {
		args.add("shadow/test/NullableArrayTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Hello\n" + 
				"Goodbye\n" + 
				"[null, Hello, null, Goodbye, null]\n");
	}
	
	@Test public void testNullableInterface() throws Exception {
		args.add("shadow/test/NullableInterfaceTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Interface is null!\n" + 
				"80092981320\n");
	}
	
	@Test public void testNullablePrimitive() throws Exception {
		args.add("shadow/test/NullablePrimitiveTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
			"Not equal!\n" + 
			"Equal!\n" +
			"Equal!\n" +
			"Not equal!\n" +
			"null\n");
	}
	
	@Test public void testNullableWithCheck() throws Exception {
		args.add("shadow/test/NullableWithCheckTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Recovered!\n");
	}
	
	@Test public void testNullableWithoutCheck() throws Exception {
		args.add("shadow/test/NullableWithoutCheckTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], "",
				"shadow:standard@UnexpectedNullException\n",
				1);
	}
	
	@Test public void testPrimitive() throws Exception {
		args.add("shadow/test/PrimitiveTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"5\n" + 
				"5\n" + 
				"5\n" + 
				"5\n" + 
				"5\n" +
				"8\n");
	}	

	@Test public void testProperty() throws Exception {
		args.add("shadow/test/PropertyTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
			"17\n" +
			"24\n" +
			"5.0\n" +
			"Jams!\n" +
			"53\n" +
			"182\n");
	}
	
	@Test public void testSimple() throws Exception {
		args.add("shadow/test/SimpleTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], "Hello, world!\n");
	}
	
	@Test public void testSubscript() throws Exception {
		args.add("shadow/test/SubscriptTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 
				"my\n" + 
				"17\n" +
				"off\n" +
				"Words: [Visit, me, with, my, wombats]\n" +
				"Other words: [Help, me, off, my, boat]\n" +
				"Numbers: [1, 2, 3, 409, 5, 6, 7, 8, 9, 10, 41, 12, 13, 14, 15, 16, 17, 18, 19, 20]\n");
	}
	
	@Test public void testSwitch() throws Exception {
		args.add("shadow/test/SwitchTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[] {"bedula", "sesame"}, 
				"Default!\n" +
				"Default!\n" +
				"Three!\n" +
				"Four!\n" +
				"Five\n" +
				"Default!\n" +
				"Others!\n" +
				"Others!\n" +
				"Others!\n" +
				"Ten!\n" +
				"Welcome, bedula\n" +
				"That's the magic word!\n" +
				"separate scopes\n");
	}
	
	@Test public void testTest() throws Exception {
		args.add("shadow/test/Test.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"true\n" + 
				"true\n" + 
				"false\n" + 
				"false\n" + 
				"true\n");
	}
	
	@Test public void testToughTry() throws Exception {
		args.add("shadow/test/ToughTry.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
			"test 1\n" + 
			"16\n" +
			"finally\n" +
			"test 2\n" +
			"catch (shadow:standard@Exception)\n" +
			"finally\n" +
			"test 3\n" +
			"finally\n" +
			"Result: 3\n");
	}
	
	@Test public void testTry() throws Exception {
		args.add("shadow/test/TryTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"before throw\n" + 
				"catch (shadow:standard@Exception: message)\n" +
				"finally\n"
				);
	}	

	@Test public void testTreeMap() throws Exception {
		args.add("shadow/test/TreeMapTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Passed 1\n" + 
				"Passed 2\n" +
				"Passed 3\n" +
				"Bozo\n" +
				"{Clothes=58, Sandwich=20}\n" +
				"Deal\n" +
				"Passed 4\n" +
				"Passed 5\n" +
				"Passed 6\n" +
				"Passed 7\n" +
				"Passed 8\n" +
				"Passed 9\n" +
				"Passed 10\n" +
				"0 1 4 16 25 36 49 64 81 100 121 144 169 196 225 256 289 324 361 \n" +
				"0\n" + 
				"1\n" +
				"2\n" +				
				"4\n" +
				"5\n" +
				"6\n" +
				"7\n" +
				"8\n" +
				"9\n" +
				"10\n" +
				"11\n" +
				"12\n" +
				"13\n" +
				"14\n" +
				"15\n" +
				"16\n" +
				"17\n" +
				"18\n" + 
				"19\n");
	}
	
	@Test public void testTreeSet() throws Exception {
		args.add("shadow/test/TreeSetTest.shadow");
		Main.run(args.toArray(new String[] { }));	
		run(new String[0],
				"Passed 1\n" + 
				"Passed 2\n" + 
				"Passed 3\n" + 
				"Passed 4\n" + 
				"true\n" + 
				"{Bozo, Clown, the}\n" + 
				"Passed 5\n" + 
				"Passed 6\n" + 
				"Passed 7\n" + 
				"{0, 1, 9, 16, 25, 36, 49, 64, 81, 100, 121, 144, 169, 196, 225, 256, 289, 324, 361}\n" + 
				"Min: 0\n" + 
				"Max: 361\n" + 
				"Floor (5): 1\n" + 
				"Ceiling (5): 9\n" + 
				"Elements (10-100): [16, 25, 36, 49, 64, 81, 100]\n");		
	}
	
	@Test public void testConstantPropagation() throws Exception {
		args.add("shadow/test/ConstantPropagation.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"13\n" + 
				"130\n" + 
				"false\n" + 
				"countrytime\n");	
	}
	
	@Test public void testGenericInterface() throws Exception {
		args.add("shadow/test/GenericInterfaceTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],  "true\n" + 
							"true\n" + 
							"true\n" +
							"true\n" +
							"false\n" + 
							"5\n" + 
							"7\n");
	}
	
	@Test public void testExternals() throws Exception {
		args.add("shadow/test/ExternalsTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], formatOutputString(
								"This is the result from running shadow_ToString(ref) from C!!", 
								"This is a string created in C and printed using Shadow's Console.printLine()", 
								"2500", 
								"3500",
								"shadow:test@ExternalsTest",
								"boolean",
								"byte",
								"ubyte",
								"short",
								"ushort",
								"int",
								"uint",
								"code",
								"long",
								"ulong",
								"float",
								"double"
		));
	}
	
	@Test public void testReadOnlyList() throws Exception {
		args.add("shadow/test/ReadOnlyListTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], formatOutputString("5", "7"));
	}
	
	@Test public void testEqualityComparer() throws Exception {
		args.add("shadow/test/EqualityComparerTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"true\n" +
				"true\n" +
				"true\n" +
				"true\n" +
				"true\n" +
				"true\n" +
				"true\n");
	}
	
	@Test public void testFreezeImmutable() throws Exception {
		args.add("shadow/test/FreezeImmutableTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Hello.\n");
	}
	
	@Test public void testGenericMangling() throws Exception {
		args.add("shadow/test/GenericManglingTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Method 1\n" + 
				"Method 2\n");
	}
	
	@Test public void testGarbageCollectionOutput() throws Exception {
		args.add("shadow/test/GarbageCollectionOutputTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Name: B\n" + 
				"Name: C\n" + 
				"C\n" + 
				"Name: E\n" +
				"F\n" +
				"B\n" + 
				"D\n" +
				"E\n" +
				"A\n");
	}
	
	
	@Test public void testPath() throws Exception {
		args.add("shadow/test/PathTest.shadow");
		
		String path = "www" + File.separatorChar + "data" + File.separatorChar + "file.txt";
		
		Main.run(args.toArray(new String[] { }));
		run(new String[] { path }, path + "\n");
	}
	
	@Test public void testFile() throws Exception {
		args.add("shadow/test/FileTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[] { "FileTest.txt" }, formatOutputString("Hello World!"));
	}
	
	@Test public void testHello() throws Exception {
		args.add("shadow/test/HelloTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"Hello, world!\n");
	}
	
	
	@Test public void testArrayCopy() throws Exception {
		args.add("shadow/test/ArrayCopyTest.shadow");
		Main.run(args.toArray(new String[] { }));	
		run(new String[0], 
				"[Millipede, Bumptious, Camelquote, Anthracite]\n" + 
				"[1, 5, 13, 25, 41]\n" +
				"0\n" +
				"0\n" + 
				"[Panamanian, Bumptious, Camelquote, Dutchess]\n" + 
				"[1, 7, 13, 19, 41]\n" +
				"90\n" +
				"0\n");		
	}
	
	/*@Test public void testMessageQueue() throws Exception {
		args.add("shadow/test/MessageQueueTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], "FullListException\n" + 
							"10\n");
	}
	
	@Test public void testMutex() throws Exception {
	args.add("shadow/test/MutexTest.shadow");
	Main.run(args.toArray(new String[] { }));
	run(new String[0],  formatOutputString(
						"lock1",
						"shadow:natives@MutexException: This mutex does not allow recursive locks.",
						"Nested locks",
						"shadow:natives@MutexException: This mutex is not owned by 'Thread#main' and cannot be unlocked.",
						"Done!")
		);
	}
	
	@Test public void testSignaler() throws Exception {
		args.add("shadow/test/SignalerTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 
				"Thread#1: waiting!\n" + 
				"Thread#2: waiting!\n" + 
				"Thread#3: waiting!\n" + 
				"Thread#3: finished waiting!\n" + 
				"Thread#4: waiting!\n" + 
				"Thread#5: waiting!\n" + 
				"Thread#1: finished waiting!\n" + 
				"Thread#2: finished waiting!\n" + 
				"Thread#4: finished waiting!\n" + 
				"Thread#5: finished waiting!\n"
				);
	}
	
	@Test public void testThreadSleep() throws Exception {
		args.add("shadow/test/ThreadSleepTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"I am going to wait 3 seconds.\n" +
				"I waited 3 seconds.\n" +
				"I am a thread.\n" +
			    "I am a thread.\n" +
				"I am a thread.\n" +
			    "true\n");
	}
	
	@Test public void testMailbox() throws Exception {
		args.add("shadow/test/MailboxTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], formatOutputString(
							"Test1",
							"Thread#2: hello world!",
							"Thread#3: hello world!",
							"Thread#4: hello world!",
							"Thread#5: hello world!",
							"Thread#main: stop",
							
							"Test2",
							"true",
							"Thread#7",
							"Thread#8",
							
							"A string from Thread#7",
							"Secret number: 0",
							"[0, 1, 2, 3, 4]",
							"done",
							"[0, 1, 2, 3, 4]",
							
							"A string from Thread#8",
							"Secret number: 1",
							"[10, 11, 12, 13, 14]",
							"done",
							"[10, 11, 12, 13, 14]"
						),
				"shadow:standard@IncompatibleMessageTypeException: Expected 'int' but got 'shadow:standard@String'.\n"			
		);
	}
	
	@Test public void testMessagePassing() throws Exception {
		args.add("shadow/test/MessagePassingTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], formatOutputString("9592"));
	}
	
	@Test public void testInterrupt() throws Exception {
		args.add("shadow/test/InterruptTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], formatOutputString(
				"waiting",
				"I am Thread#2",
				"Thread#2: 0",
				"I am Thread#3",
				"Thread#3: 1",
				"end",
				"InterruptedException thrown",
				"1",
				"done")
			);
	}

	@Test public void testThread() throws Exception {
		args.add("shadow/test/ThreadTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				// stdin
				"Thread#main\n" +
				"Thread#1\n" +
				"Thread#2\n" +
				"Thread#3\n" +
				"Thread#4\n" + 
				"Thread#5\n" + 
				"true\n" +
				"true\n" +
				"Thread#6\n" +
				"Thread#7\n" +
				"Thread#8\n",

				// stderr
				"Uncaught Thread Exception @Thread#3:\n" +
				"   Propagated from: (Thread#3)\n" +
				"   Throwing: (shadow:standard@Exception: from Thread#3)\n" +
				
				"Uncaught Thread Exception @Thread#4:\n" +
				"   Propagated from: (Thread#8 to Thread#6 to Thread#5 to Thread#4)\n" +
				"   Throwing: (shadow:standard@Exception: from Thread#8)\n" +
				
				"Uncaught Thread Exception @Thread#7:\n" +
				"   Propagated from: (Thread#7)\n" +
				"   Throwing: (shadow:standard@Exception: from Thread#7)\n"
			);
	}
	
	@Test public void testTLSThread() throws Exception {
		args.add("shadow/test/TLSTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 
				"1\n" +
				"1\n");
	}
	
	@Test public void testThreadIsolatedRunner() throws Exception {
		args.add("shadow/test/ThreadIsolatedRunnerTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0], 
				"1 1\n" +
				"1 1\n" +
				"1 1\n" +
				"0\n");
	}*/
}
