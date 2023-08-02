package shadow.test.output;

import com.sun.jna.platform.win32.WinReg;
import org.junit.jupiter.api.*;
import shadow.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class OutputTests {
  // To simplify removal, every unit test executable will have the same name
  private static final Path executable =
      Main.properExecutableName(Paths.get("bin", "shadow", "test", "OutputTest"));

  private final ArrayList<String> args = new ArrayList<>();

  @BeforeAll
  public static void clearConfiguration() {
    Configuration.clearConfiguration();
  }

  @BeforeEach
  public void setup() {
    args.add("-o");
    args.add(executable.toString());

    System.out.println("Working Directory = " + System.getProperty("user.dir"));

    String os = System.getProperty("os.name").toLowerCase();

    args.add("-c");
    if (os.contains("windows")) args.add("windows.json");
    else if (os.contains("mac")) args.add("mac.json");
    else args.add("linux.json");

    // args.add("-r");
    // args.add("-f");
    // args.add("-v");
  }

  @AfterEach
  public void cleanup() {

    // Try to remove the unit test executable
    try {
      Files.delete(executable);
    } catch (Exception ignored) {
    }
  }

  private void run(String[] programArgs, String expectedOutput)
      throws IOException, InterruptedException, TimeoutException {
    run(programArgs, expectedOutput, "");
  }

  @SuppressWarnings("SameParameterValue")
  private void run(String[] programArgs, String expectedOutput, String expectedError)
      throws IOException, InterruptedException, TimeoutException {
    run(programArgs, expectedOutput, expectedError, null, 0);
  }

  private void run(
      String[] programArgs,
      String expectedOutput,
      String expectedError,
      String input,
      int expectedReturn)
      throws IOException, InterruptedException, TimeoutException {

    List<String> programCommand = new ArrayList<>();
    programCommand.add(executable.toString());

    Collections.addAll(programCommand, programArgs);

    // Set working directory as parent of executable, in case executable makes any files
    Process program =
        new ProcessBuilder(programCommand).directory(executable.getParent().toFile()).start();

    // Send input
    if (input != null && !input.trim().isEmpty()) {
      PrintWriter writer = new PrintWriter(new BufferedOutputStream(program.getOutputStream()));
      writer.print(input);
      writer.close();
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(program.getInputStream()));
    BufferedReader errorReader =
        new BufferedReader(new InputStreamReader(program.getErrorStream()));

    // Regular output
    StringBuilder builder = new StringBuilder();
    String line;
    do {
      line = reader.readLine();
      if (line != null) builder.append(line).append("\n");
    } while (line != null);
    reader.close();
    String output = builder.toString();
    Assertions.assertEquals(expectedOutput, output);

    // Error output
    builder = new StringBuilder();
    do {
      line = errorReader.readLine();
      if (line != null) builder.append(line).append("\n");
    } while (line != null);
    errorReader.close();

    String error = builder.toString();
    Assertions.assertEquals(expectedError, error);

    // Check return value to see if the program ends normally
    // Also keeps program from being deleted while running
    if (program.waitFor(30, TimeUnit.SECONDS)) {
      int exitValue = program.exitValue();
      program.destroy();
      Assertions.assertEquals(expectedReturn, exitValue, "Program exited abnormally.");
    } else {
      program.destroyForcibly();
      throw new TimeoutException();
    }
  }

  private String formatOutputString(CharSequence... elements) {
    return String.join("\n", elements) + "\n";
  }

  @Test
  public void testAbstract() throws Exception {
    args.add("shadow/test/AbstractTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Your offer is accepted! The motorcycle is yours!
                    Buckle up!
                    Your motorcycle is going 75 mph!
                    """);
  }

  @Test
  public void testAddressMap() throws Exception {
    args.add("shadow/test/AddressMapTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Added key: 47 Value: 1
                    Added key: 41 Value: 2
                    Added key: 35 Value: 3
                    Added key: 29 Value: 4
                    Added key: 23 Value: 5
                    Added key: 17 Value: 6
                    Added key: 11 Value: 7
                    Added key: 5 Value: 8
                    Added key: 52 Value: 9
                    Added key: 46 Value: 10
                    Added key: 40 Value: 11
                    Added key: 34 Value: 12
                    Added key: 28 Value: 13
                    Added key: 22 Value: 14
                    Added key: 16 Value: 15
                    Added key: 10 Value: 16
                    Added key: 4 Value: 17
                    Added key: 51 Value: 18
                    Added key: 45 Value: 19
                    Added key: 39 Value: 20
                    Added key: 33 Value: 21
                    Added key: 27 Value: 22
                    Added key: 21 Value: 23
                    Added key: 15 Value: 24
                    Added key: 9 Value: 25
                    Added key: 3 Value: 26
                    Added key: 50 Value: 27
                    Added key: 44 Value: 28
                    Added key: 38 Value: 29
                    Added key: 32 Value: 30
                    Added key: 26 Value: 31
                    Added key: 20 Value: 32
                    Added key: 14 Value: 33
                    Added key: 8 Value: 34
                    Added key: 2 Value: 35
                    Added key: 49 Value: 36
                    Added key: 43 Value: 37
                    Added key: 37 Value: 38
                    Added key: 31 Value: 39
                    Added key: 25 Value: 40
                    Added key: 19 Value: 41
                    Added key: 13 Value: 42
                    Added key: 7 Value: 43
                    Added key: 1 Value: 44
                    Added key: 48 Value: 45
                    Added key: 42 Value: 46
                    Added key: 36 Value: 47
                    Added key: 30 Value: 48
                    Added key: 24 Value: 49
                    Added key: 18 Value: 50
                    Added key: 12 Value: 51
                    Added key: 6 Value: 52
                    Added key: 0 Value: 53
                    Contains key 43!
                    Does not contain 89!
                    Value at 43 is: 37
                    Value at 17 is: 6
                    Value at 100 is: null
                    #1: 0
                    #2: 44
                    #3: 35
                    #4: 5
                    #5: 17
                    #6: 8
                    #7: 52
                    #8: 43
                    #9: 34
                    #10: 25
                    #11: 16
                    #12: 7
                    #13: 51
                    #14: 42
                    #15: 33
                    #16: 24
                    #17: 6
                    #18: 15
                    #19: 41
                    #20: 50
                    #21: 23
                    #22: 32
                    #23: 5
                    #24: 14
                    #25: 40
                    #26: 49
                    #27: 22
                    #28: 31
                    #29: 4
                    #30: 13
                    #31: 39
                    #32: 48
                    #33: 12
                    #34: 3
                    #35: 30
                    #36: 21
                    #37: 29
                    #38: 20
                    #39: 47
                    #40: 38
                    #41: 46
                    #42: 37
                    #43: 11
                    #44: 2
                    #45: 10
                    #46: 1
                    #47: 28
                    #48: 19
                    #49: 18
                    #50: 27
                    #51: 36
                    #52: 45
                    #53: 9
                    """);
  }

  @Test
  public void testArray() throws Exception {
    args.add("shadow/test/ArrayTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    [0, 2, four, 88, shadow:standard@Object]
                    [2, four, 88]
                    [0, 1, 2, 3, 4]
                    [zero, one, two]
                    """);
  }

  @Test
  public void testArrayAsObject() throws Exception {
    args.add("shadow/test/ArrayAsObject.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "Size: 135\n");
  }

  @Test
  public void testArrayCreate() throws Exception {
    args.add("shadow/test/ArrayCreateTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    a[0]:\s
                    a[1]:\s
                    a[2]:\s
                    a[3]:\s
                    a[4]:\s
                    a[5]:\s
                    a[6]:\s
                    a[7]:\s
                    a[8]:\s
                    a[9]:\s
                    b[0]: Test
                    b[1]: Test
                    b[2]: Test
                    b[3]: Test
                    b[4]: Test
                    b[5]: Test
                    b[6]: Test
                    b[7]: Test
                    b[8]: Test
                    b[9]: Test
                    c[0]: 5
                    c[1]: 5
                    c[2]: 5
                    c[3]: 5
                    c[4]: 5
                    c[5]: 5
                    c[6]: 5
                    c[7]: 5
                    c[8]: 5
                    c[9]: 5
                    d[0]: 1
                    d[1]: 2
                    d[2]: 3
                    d[3]: 4
                    d[4]: 5
                    d[5]: 6
                    d[6]: 7
                    d[7]: 8
                    d[8]: 9
                    d[9]: 10
                    e[0][0]: 1
                    e[0][1]: 2
                    e[0][2]: 3
                    e[0][3]: 4
                    e[0][4]: 5
                    e[1][0]: 6
                    e[1][1]: 7
                    e[1][2]: 8
                    e[1][3]: 9
                    e[1][4]: 10
                    e[2][0]: 11
                    e[2][1]: 12
                    e[2][2]: 13
                    e[2][3]: 14
                    e[2][4]: 15
                    """);
  }

  @Test
  public void testArrayCast() throws Exception {
    args.add("shadow/test/ArrayCastTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Passed
                    Passed: shadow:standard@CastException: Type int[] is not a subtype of short[]
                    Passed: shadow:standard@CastException: Type nullable shadow:standard@Object[] is not a subtype of shadow:standard@Object[]
                    Passed: shadow:standard@CastException: Type shadow:standard@String[] is not a subtype of nullable shadow:standard@String[]
                    """);
  }

  @Test
  public void testArrayDefault() throws Exception {
    args.add("shadow/test/ArrayDefaultTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    a[0]: Wombat
                    a[1]: Wombat
                    a[2]: Wombat
                    a[3]: Wombat
                    a[4]: Wombat
                    b[0]: 42
                    b[1]: 42
                    b[2]: 42
                    b[3]: 42
                    b[4]: 42
                    c[0]: shadow:standard@Object
                    c[1]: shadow:standard@Object
                    c[2]: shadow:standard@Object
                    c[3]: shadow:standard@Object
                    c[4]: shadow:standard@Object
                    """);
  }

  @Test
  public void testArrayDeque() throws Exception {
    args.add("shadow/test/ArrayDequeTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            Deserves
            Good
            Every
            Boy
            Fudge
            """);
  }

  @Test
  public void testArrayInitializer() throws Exception {
    args.add("shadow/test/ArrayInitializerTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    [1, 4, 9, 16, 25]
                    [donut, explanation, story, book, 25]
                    [[9, 8, 7], [12, 5, 4], [3, 2, 1]]
                    [a, e, i, o, y]
                    [[snap, crackle, pop], [tip, top], [taste, the, rainbow]]
                    [[snap, crackle, pop], [cranberry, jamboplexy, in, place], [taste, the, rainbow]]
                    """);
  }

  @Test
  public void testArrayList() throws Exception {
    args.add("shadow/test/ArrayListTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[] {"all", "good", "men", "shall", "perish"},
            """
                    true
                    true
                    false
                    all
                    men
                    shall
                    [all, men, shall]
                    [4, 16, 25, 36, 49, 64, 81, 100, 121, 144, 169, 196, 225]
                    """);
  }

  @Test
  public void testArrayOutOfBounds() throws Exception {
    args.add("shadow/test/ArrayOutOfBoundsTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    shadow:standard@IndexOutOfBoundsException: Index 16
                    shadow:standard@IndexOutOfBoundsException: Index -1
                    shadow:standard@IndexOutOfBoundsException: Index 9
                    shadow:standard@IndexOutOfBoundsException: Index 9
                    shadow:standard@IndexOutOfBoundsException: Index 28
                    """);
  }

  @Test
  public void testAssert() throws Exception {
    args.add("shadow/test/AssertTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    shadow:standard@AssertException: Too small!
                    shadow:standard@AssertException
                    """);
  }

  @Test
  public void testAssignment() throws Exception {
    args.add("shadow/test/AssignmentTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    x: 1
                    array: [2, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                    trouble: {9=3}
                    array: [0, 0, 0, 4, 0, 0, 0, 0, 0, 0]
                    array: [8, 6, 0, 0, 0, 0, 0, 0, 0, 0]
                    """);
  }

  @Test
  public void testBasic() throws Exception {
    args.add("shadow/test/BasicTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Count it off!
                    I say 1
                    I say 2
                    I say 3
                    [Help me]
                    125
                    """);
  }

  @Test
  public void testBigInteger() throws Exception {
    args.add("shadow/test/BigIntegerTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    0: 2
                    1: 4
                    2: 8
                    3: 16
                    4: 32
                    5: 64
                    6: 128
                    7: 256
                    8: 512
                    9: 1024
                    10: 2048
                    11: 4096
                    12: 8192
                    13: 16384
                    14: 32768
                    15: 65536
                    16: 131072
                    17: 262144
                    18: 524288
                    19: 1048576
                    20: 2097152
                    21: 4194304
                    22: 8388608
                    23: 16777216
                    24: 33554432
                    25: 67108864
                    26: 134217728
                    27: 268435456
                    28: 536870912
                    29: 1073741824
                    30: 2147483648
                    31: 4294967296
                    32: 8589934592
                    33: 17179869184
                    34: 34359738368
                    35: 68719476736
                    36: 137438953472
                    37: 274877906944
                    38: 549755813888
                    39: 1099511627776
                    40: 2199023255552
                    41: 4398046511104
                    42: 8796093022208
                    43: 17592186044416
                    44: 35184372088832
                    45: 70368744177664
                    46: 140737488355328
                    47: 281474976710656
                    48: 562949953421312
                    49: 1125899906842624
                    50: 2251799813685248
                    51: 4503599627370496
                    52: 9007199254740992
                    53: 18014398509481984
                    54: 36028797018963968
                    55: 72057594037927936
                    56: 144115188075855872
                    57: 288230376151711744
                    58: 576460752303423488
                    59: 1152921504606846976
                    60: 2305843009213693952
                    61: 4611686018427387904
                    62: 9223372036854775808
                    63: 18446744073709551616
                    64: 36893488147419103232
                    65: 73786976294838206464
                    66: 147573952589676412928
                    67: 295147905179352825856
                    68: 590295810358705651712
                    69: 1180591620717411303424
                    70: 2361183241434822606848
                    71: 4722366482869645213696
                    72: 9444732965739290427392
                    73: 18889465931478580854784
                    74: 37778931862957161709568
                    75: 75557863725914323419136
                    76: 151115727451828646838272
                    77: 302231454903657293676544
                    78: 604462909807314587353088
                    79: 1208925819614629174706176
                    80: 2417851639229258349412352
                    81: 4835703278458516698824704
                    82: 9671406556917033397649408
                    83: 19342813113834066795298816
                    84: 38685626227668133590597632
                    85: 77371252455336267181195264
                    86: 154742504910672534362390528
                    87: 309485009821345068724781056
                    88: 618970019642690137449562112
                    89: 1237940039285380274899124224
                    90: 2475880078570760549798248448
                    91: 4951760157141521099596496896
                    92: 9903520314283042199192993792
                    93: 19807040628566084398385987584
                    94: 39614081257132168796771975168
                    95: 79228162514264337593543950336
                    96: 158456325028528675187087900672
                    97: 316912650057057350374175801344
                    98: 633825300114114700748351602688
                    99: 1267650600228229401496703205376
                    a: 458248854125127692644763689697630452603077525667039488167595
                    b: 424527578038877065750254364514248265552431685221767
                    a + b: 458248854549655270683640755447884817117325791219471173389362
                    a - b: 458248853700600114605886623947376088088829260114607802945828
                    a * b: 194539276180831139131011253850959240145863762295190414514980438839514500513512702883553825017239414420038040365
                    a / b: 1079432474
                    a % b: 281394553380105954880717639067607214972118296706037
                    a / b: 3
                    a / b: 3
                    a / b: 4294967295
                    a / b: 5599413508
                    a / b: 611612530
                    a / b: 4159831672
                    a / b: 873727415
                    n: 2195521454340267086741763887146703421403138168590319536395743165882538840103478757954437458467991587781634444546316100430675861955984773682480893416347615036033833354001156644056162483366839982619823648021770903663979679691296438211023119129944865865532679111362655761233794694594519636426946285142324520755866494927472412250065210781235421203259580302777998048873938210795799807855003878822406629289
                    C: 1112956698262836806664794820794473961145010180577601125590707755951029843006749551702925760533038042622692901510299809647917425905134514887570764862158562662534883462854826818855263583936781681828017953722260950237915404296241216983813592369263412058474774469738265051177236933818586309402181524305722641225977290699659457953607880969651822747591468484978977768458753917979021465084394384421346410164
                    Decrypted M: 123456789101112131415161718
                    """);
  }

  @Test
  public void testCastException() throws Exception {
    args.add("shadow/test/CastExceptionTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Cast from String to String
                    shadow:standard@CastException: Type shadow:standard@Object is not a subtype of shadow:standard@String
                    Cast from String to CanIterate<code>
                    Cast from String to CanEqual<String>
                    shadow:standard@CastException: Class shadow:standard@Object does not implement interface shadow:standard@CanIterate<code>
                    """);
  }

  @Test
  public void testChild() throws Exception {
    args.add("shadow/test/ChildTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    shadow:test@ParentTest:create("Hello World!")
                    shadow:test@ChildTest:main([])
                    """);
  }

  @Test
  public void testCollidingWrapper() throws Exception {
    args.add("shadow/test/CollidingWrapperTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            13
            8
            13
            8
            """);
  }

  @Test
  public void testCommandLine() throws Exception {
    args.add("shadow/test/CommandLine.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[] {"this", "is", "the", "bleh", "situation"},
            """
                    Yes!
                    situation doesn't match!
                    bleh matches!
                    the doesn't match!
                    is doesn't match!
                    this doesn't match!
                    """);
  }

  @Test
  public void testComplex() throws Exception {
    args.add("shadow/test/ComplexTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    a: 2 - i
                    b: 3 + 5i
                    c: 5 + 4i
                    d: -1 - 6i
                    e: 11 + 7i
                    """);
  }

  @Test
  public void testComplicatedException() throws Exception {
    args.add("shadow/test/ComplicatedExceptionTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    1
                    Catch shadow:test@ExceptionA
                    Finally
                    No catch
                    2
                    Finally
                    Catch outer 2 shadow:standard@Exception
                    3
                    Finally
                    Catch outer 1 shadow:test@ExceptionB
                    4
                    Recover
                    Finally
                    No catch
                    5
                    Finally
                    Overriding old exception with shadow:test@ExceptionB
                    Catch outer 1 shadow:test@ExceptionB
                    """);
  }

  @Test
  public void testNestedException() throws Exception {
    args.add("shadow/test/NestedExceptionTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            Dunks
            Sunks
            Chunks
            Monks
            Punks
            """);
  }

  @Test
  public void testConsole() throws Exception {
    args.add("shadow/test/ConsoleTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    12345
                    Hello World!
                    Test String
                    -12
                    130
                    1000
                    61234
                    2000000002
                    3100000000
                    -8000000000000
                    10223372036854775807

                    Enter your name: Your name is Alan Turing!
                    Enter your age: Your age is 45
                    Enter your weight: Your weight is 132.5
                    a
                    b
                    c
                    d
                    e
                    f
                    """,
        "",
        "Alan Turing"
            + System.lineSeparator()
            + "45"
            + System.lineSeparator()
            + "132.5"
            + System.lineSeparator()
            + "abcdef",
        0);
  }

  @Test
  public void testCopy() throws Exception {
    args.add("shadow/test/CopyTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    s1: Walnuts
                    s2: Walnuts
                    o1: (13,StuffStuff)
                    o2: (14,Stuff)
                    integer1: 3
                    integer2: 3
                    array1: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                    array2: [2, 4, 6, 8, 10, 12, 14, 16, 18, 20]
                    array3: [[1, 2, 3, 4, 5], [6, 7, 8, 9, 10], [11, 12, 13, 14, 15], [16, 17, 18, 19, 20]]
                    array4: [[2, 3, 4, 5, 6], [7, 8, 9, 10, 11], [12, 13, 14, 15, 16], [17, 18, 19, 20, 21]]
                    array5: [[0, 3, 6, 9, 12], [150, 153, 156, 159, 162], [300, 303, 306, 309, 312], [450, 453, 456, 459, 462]]
                    array6: [[0, 1, 2, 3, 4], [50, 51, 52, 53, 54], [100, 101, 102, 103, 104], [150, 151, 152, 153, 154]]
                    array7: [[[0, 2, 4], [6, 8, 10]]]
                    array8: [[[0, 1, 2], [3, 4, 5]]]
                    array9: [[0, 2, 4], [6, 8, 10, 12]]
                    array10: [[0, 1, 2], [3, 4, 5, 6]]
                    matrix1: [3.0, 2.0, 0.0]
                    [0.0, 0.0, 7.0]

                    matrix2: [0.0, 2.0, 0.0]
                    [0.0, 0.0, 5.0]

                    """);
  }

  @Test
  public void testCreateStringInConstant() throws Exception {
    args.add("shadow/test/CreateStringInConstantTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "Empty: \nNon-empty: Hello!\n");
  }

  @Test
  public void testDependentConstants() throws Exception {
    args.add("shadow/test/DependentConstantsTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "3\n4\n5\n9\nwalnut\nwalnuts\n2\n");
  }

  @Test
  public void testException() throws Exception {
    args.add("shadow/test/ExceptionTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    finally
                    test2 caught ExceptionB
                    """,
        "shadow:standard@Exception\n",
        null,
        1);
  }

  @Test
  public void testFinally() throws Exception {
    args.add("shadow/test/FinallyTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    before method
                    before try
                    loop before break
                    try before break
                    finally break
                    finally
                    after method
                    return value
                    """);
  }

  @Test
  public void testForeach() throws Exception {
    args.add("shadow/test/ForeachTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    20
                    19
                    18
                    17
                    16
                    15
                    14
                    13
                    12
                    11
                    10
                    9
                    8
                    7
                    6
                    5
                    4
                    3
                    2
                    1
                    My
                    dog
                    has
                    fleas
                    20 19 18 17 16\s
                    15 14 13 12 11\s
                    10 9 8 7 6\s
                    5 4 3 2 1\s
                    """);
  }

  @Test
  public void testGeneric() throws Exception {
    args.add("shadow/test/GenericTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Class: shadow:test@Container<shadow:standard@String>
                    Contents: blub
                    Class: shadow:test@Container<int>
                    Contents: 17
                    """);
  }

  @Test
  public void testGenericArray() throws Exception {
    args.add("shadow/test/GenericArrayTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    0 0 0 4 0 0 0 0 0 10\s
                    [0, 0, 0, 4, 0, 0, 0, 0, 0, 10]
                    [one, two, three, four, five]
                    [0, 0, 0, 4, 0, 0, 0, 0, 0, 10]
                    [null, Hey, null, null, Ho]
                    """);
  }

  @Test
  public void testHashMap() throws Exception {
    args.add("shadow/test/HashMapTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Passed 1
                    Passed 2
                    Passed 3
                    Bozo
                    {Sandwich=20, Clothes=58}
                    Deal
                    Passed 4
                    Passed 5
                    Passed 6
                    Passed 7
                    Passed 8
                    Passed 9
                    Passed 10
                    #1: 0
                    #2: 1
                    #3: 4
                    #4: 16
                    #5: 25
                    #6: 36
                    #7: 49
                    #8: 64
                    #9: 81
                    #10: 100
                    #11: 121
                    #12: 144
                    #13: 169
                    #14: 196
                    #15: 225
                    #16: 289
                    #17: 256
                    #18: 361
                    #19: 324
                    """);
  }

  @Test
  public void testHashSet() throws Exception {
    args.add("shadow/test/HashSetTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Passed 1
                    Passed 2
                    Passed 3
                    Passed 4
                    true
                    {Bozo, Clown, the}
                    Passed 5
                    Passed 6
                    Passed 7
                    {0, 1, 169, 100, 64, 36, 196, 9, 225, 289, 16, 324, 256, 49, 81, 25, 144, 361, 121}
                    """);
  }

  @Test
  public void testInheritedConstants() throws Exception {
    args.add("shadow/test/InheritedConstantsTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            -2
            10
            15
            6
            """);
  }

  @Test
  public void testInterface() throws Exception {
    args.add("shadow/test/InterfaceTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    shadow:test@Cheetah runs at 75 mph.
                    shadow:test@Hare runs at 40 mph.
                    shadow:test@Tortoise runs slow and steady.
                    shadow:test@Tortoise runs slow and steady.
                    shadow:test@Cheetah runs at 75 mph.
                    shadow:test@Hare runs at 40 mph.
                    """);
  }

  @Test
  public void testInterfaceConstant() throws Exception {
    args.add("shadow/test/InterfaceConstantTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "13\n");
  }

  @Test
  public void testInterfaceCreate() throws Exception {
    args.add("shadow/test/InterfaceCreateTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
        "",
        "shadow:standard@InterfaceCreateException: Cannot create interface shadow:standard@CanCreate\n",
        null,
        1);
  }

  @Test
  public void testIs() throws Exception {
    args.add("shadow/test/IsTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    shadow:test@ParentTest:create("Hello World!")
                    shadow:test@ParentTest:create()
                    String is String
                    String is Object
                    Object is Object
                    Object is not String
                    Tortoise is Tortoise
                    Tortoise is CanRun
                    ChildTest is ParentTest
                    ChildTest is Object
                    """);
  }

  @Test
  public void testLinkedList() throws Exception {
    args.add("shadow/test/LinkedListTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    [Fox, Socks, Box, Knox, Knox in box, Fox in socks, Knox on fox in socks in box]
                    [Socks, Box, Knox in box, Fox in socks, Knox on fox in socks in box]
                    Fox
                    1
                    [Socks, Box, Fox in socks, Knox on fox in socks in box]
                    [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99]
                    15
                    [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]
                    """);
  }

  @Test
  public void testLoop() throws Exception {
    args.add("shadow/test/LoopTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    before outer
                    try before outer
                    loop before skip
                    try before skip
                    try after skip
                    finally skip
                    loop after skip
                    loop before skip
                    try before skip
                    try after skip
                    finally skip
                    loop after skip
                    loop before continue
                    try before continue
                    finally continue
                    loop before continue
                    try before continue
                    finally continue
                    loop before break
                    try before break
                    finally break
                    try after outer
                    before return first
                    finally outer
                    return first
                    """);
  }

  @Test
  public void testManyConstants() throws Exception {
    args.add("shadow/test/ManyConstantsTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    7
                    -34
                    -41
                    1200
                    1205
                    -5
                    2000000000
                    14000000000
                    -10000000995
                    120
                    -128
                    8
                    C
                    A
                    t
                    Goats
                    Goatsta
                    ta
                    Stack
                    7
                    48
                    41
                    1200
                    64341
                    5
                    2000000000
                    14000000000
                    9999999005
                    120
                    128
                    8
                    5
                    """);
  }

  @Test
  public void testMatrix() throws Exception {
    args.add("shadow/test/MatrixTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    a:
                    [1.0, 0.0, 0.0]
                    [0.0, 1.0, 4.0]
                    [0.0, 0.0, 1.0]

                    b:
                    [1.0, 0.0, 0.0]
                    [0.0, 1.0, 0.0]
                    [3.0, 0.0, 1.0]

                    c:
                    [2.0, 0.0, 0.0]
                    [0.0, 2.0, 4.0]
                    [3.0, 0.0, 2.0]

                    d:
                    [0.0, 0.0, 0.0]
                    [0.0, 0.0, 4.0]
                    [-3.0, 0.0, 0.0]

                    e:
                    [1.0, 0.0, 0.0]
                    [12.0, 1.0, 4.0]
                    [3.0, 0.0, 1.0]

                    """);
  }

  @Test
  public void testMethodOperations() throws Exception {
    args.add("shadow/test/MethodOperations.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            x == y
            x != z
            s1 == s2
            s1 != s3
            """);
  }

  @Test
  public void testMutableString() throws Exception {
    args.add("shadow/test/MutableStringTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            peach
            p
            e
            a
            c
            h
            hcaep
            """);
  }

  @Test
  public void testNullableArray() throws Exception {
    args.add("shadow/test/NullableArrayTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            Hello
            Goodbye
            [null, Hello, null, Goodbye, null]
            """);
  }

  @Test
  public void testNullableInterface() throws Exception {
    args.add("shadow/test/NullableInterfaceTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            Interface is null!
            80092981320
            """);
  }

  @Test
  public void testNullablePrimitive() throws Exception {
    args.add("shadow/test/NullablePrimitiveTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            Not equal!
            Equal!
            Equal!
            Not equal!
            null
            """);
  }

  @Test
  public void testNullableWithCheck() throws Exception {
    args.add("shadow/test/NullableWithCheckTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "Recovered!\n");
  }

  @Test
  public void testNullableWithoutCheck() throws Exception {
    args.add("shadow/test/NullableWithoutCheckTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "", "shadow:standard@UnexpectedNullException\n", null, 1);
  }

  @Test
  public void testPrimitive() throws Exception {
    args.add("shadow/test/PrimitiveTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            5
            5
            5
            5
            5
            8
            """);
  }

  @Test
  public void testProperty() throws Exception {
    args.add("shadow/test/PropertyTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            17
            24
            5.0
            Jams!
            53
            182
            """);
  }

  @Test
  public void testSimple() throws Exception {
    args.add("shadow/test/SimpleTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "Hello, world!\n");
  }

  @Test
  public void testSimpleException() throws Exception {
    args.add("shadow/test/SimpleExceptionTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            success
            shadow:test@ExceptionA
            """);
  }

  @Test
  public void testSubscript() throws Exception {
    args.add("shadow/test/SubscriptTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    my
                    17
                    off
                    Words: [Visit, me, with, my, wombats]
                    Other words: [Help, me, off, my, boat]
                    Numbers: [1, 2, 3, 409, 5, 6, 7, 8, 9, 10, 41, 12, 13, 14, 15, 16, 17, 18, 19, 20]
                    """);
  }

  @Test
  public void testSwitch() throws Exception {
    args.add("shadow/test/SwitchTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[] {"bedula", "sesame"},
            """
                    Default!
                    Default!
                    Three!
                    Four!
                    Five
                    Default!
                    Others!
                    Others!
                    Others!
                    Ten!
                    Welcome, bedula
                    That's the magic word!
                    separate scopes
                    """);
  }

  @Test
  public void testSystem() throws Exception {
    args.add("shadow/test/SystemTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    String os = System.getProperty("os.name");
    boolean isWindows = os.toLowerCase().contains("windows");
    String version;
    if (isWindows) {
      os =
          RegistryAccess.readString(
              WinReg.HKEY_LOCAL_MACHINE,
              "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion",
              "ProductName");
      int major =
          RegistryAccess.readInt(
              WinReg.HKEY_LOCAL_MACHINE,
              "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion",
              "CurrentMajorVersionNumber");
      int minor =
          RegistryAccess.readInt(
              WinReg.HKEY_LOCAL_MACHINE,
              "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion",
              "CurrentMinorVersionNumber");
      version = major + "." + minor;
    } else version = System.getProperty("os.version");

    String path = System.getenv("PATH");
    if (path == null) path = "";

    // Time since Unix epoch rounded to nearest 10 second chunk
    String time = "" + Math.round(System.currentTimeMillis() / 10000.0);
    run(new String[0], isWindows + "\n" + path + "\n" + os + "\n" + version + "\n" + time + "\n");
  }

  @Test
  public void testTest() throws Exception {
    args.add("shadow/test/Test.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            true
            true
            false
            false
            true
            """);
  }

  @Test
  public void testToughTry() throws Exception {
    args.add("shadow/test/ToughTry.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    test 1
                    16
                    finally
                    test 2
                    catch (shadow:standard@Exception)
                    finally
                    test 3
                    finally
                    Result: 3
                    """);
  }

  @Test
  public void testTry() throws Exception {
    args.add("shadow/test/TryTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    before throw
                    catch (shadow:standard@Exception: message)
                    finally
                    """);
  }

  @Test
  public void testTreeMap() throws Exception {
    args.add("shadow/test/TreeMapTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Passed 1
                    Passed 2
                    Passed 3
                    Bozo
                    {Clothes=58, Sandwich=20}
                    Deal
                    Passed 4
                    Passed 5
                    Passed 6
                    Passed 7
                    Passed 8
                    Passed 9
                    Passed 10
                    0 1 4 16 25 36 49 64 81 100 121 144 169 196 225 256 289 324 361\s
                    0
                    1
                    2
                    4
                    5
                    6
                    7
                    8
                    9
                    10
                    11
                    12
                    13
                    14
                    15
                    16
                    17
                    18
                    19
                    """);
  }

  @Test
  public void testTreeSet() throws Exception {
    args.add("shadow/test/TreeSetTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Passed 1
                    Passed 2
                    Passed 3
                    Passed 4
                    true
                    {Bozo, Clown, the}
                    Passed 5
                    Passed 6
                    Passed 7
                    {0, 1, 9, 16, 25, 36, 49, 64, 81, 100, 121, 144, 169, 196, 225, 256, 289, 324, 361}
                    Min: 0
                    Max: 361
                    Floor (5): 1
                    Ceiling (5): 9
                    Elements (10-100): [16, 25, 36, 49, 64, 81, 100]
                    """);
  }

  @Test
  public void testConstantPropagation() throws Exception {
    args.add("shadow/test/ConstantPropagation.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            13
            130
            false
            countrytime
            """);
  }

  @Test
  public void testGenericInterface() throws Exception {
    args.add("shadow/test/GenericInterfaceTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            true
            true
            true
            true
            false
            5
            7
            """);
  }

  @Test
  public void testExternals() throws Exception {
    args.add("shadow/test/ExternalsTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
        formatOutputString(
            "This is the result from running _shadowObject_toString(ref) from C!",
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
            "double"));
  }

  @Test
  public void testString() throws Exception {
    args.add("shadow/test/StringTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
        formatOutputString(
            "Hello World!",
            "b",
            "l",
            "e",
            "a",
            "r",
            "g",
            "h",
            "h",
            "h",
            "h",
            "a: 5 (byte)",
            "b: -7 (byte)",
            "c: 5 (ubyte)",
            "d: 5 (short)",
            "e: -7 (short)",
            "f: 5 (ushort)",
            "g: 5 (int)",
            "h: -7 (int)",
            "i: 5 (uint)",
            "j: 5 (long)",
            "k: -7 (long)",
            "m: 5 (ulong)",
            "n: -3.47 (double)",
            "o: 1.23657E23 (double)",
            "p: -1.23657E-19 (double)",
            "q: -1.23657E-19 (float)",
            "fun"));
  }

  @Test
  public void testReadOnlyList() throws Exception {
    args.add("shadow/test/ReadOnlyListTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], formatOutputString("5", "7"));
  }

  @Test
  public void testRecursion() throws Exception {
    args.add("shadow/test/Recursion.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "Fibonacci of 10: 55\n");
  }

  @Test
  public void testEqualityComparer() throws Exception {
    args.add("shadow/test/EqualityComparerTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            true
            true
            true
            true
            true
            true
            true
            """);
  }

  @Test
  public void testFreezeImmutable() throws Exception {
    args.add("shadow/test/FreezeImmutableTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "Hello.\n");
  }

  @Test
  public void testGenericMangling() throws Exception {
    args.add("shadow/test/GenericManglingTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            Method 1
            Method 2
            """);
  }

  @Test
  public void testGenericWithBound() throws Exception {
    args.add("shadow/test/GenericWithBoundTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], """
            10283.6
            230953.34
            Finished!
            """);
  }

  @Test
  public void testGarbageCollectionOutput() throws Exception {
    args.add("shadow/test/GarbageCollectionOutputTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Name: B
                    Name: C
                    C
                    Name: E
                    F
                    B
                    D
                    E
                    A
                    """);
  }

  @Test
  public void testPath() throws Exception {
    args.add("shadow/test/PathTest.shadow");

    String path = "www" + File.separatorChar + "data" + File.separatorChar + "file.txt";

    new Main(args.toArray(new String[] {})).run();
    run(new String[] {path}, path + "\n");
  }

  @Test
  public void testFile() throws Exception {
    args.add("shadow/test/FileTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[] {"FileTest.txt"}, formatOutputString("Hello World!", "12", "true", "false"));
  }

  @Test
  public void testHello() throws Exception {
    args.add("shadow/test/HelloTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "Hello, world!\n");
  }

  @Test
  public void testArrayCopy() throws Exception {
    args.add("shadow/test/ArrayCopyTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    [Millipede, Bumptious, Camelquote, Anthracite]
                    [1, 5, 13, 25, 41]
                    0
                    0
                    [Panamanian, Bumptious, Camelquote, Dutchess]
                    [1, 7, 13, 19, 41]
                    90
                    0
                    """);
  }

  @Test
  public void testMethodReference() throws Exception {
    args.add("shadow/test/MethodReferenceTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
            """
                    Test 1
                    3.5
                    Test 2
                    8.5
                    Test 3
                    4.0
                    Test 4
                    5.0
                    Test 5
                    6
                    Test 6
                    9
                    Test 7
                    7
                    """);
  }

  @Test
  public void testImportExportAssembly() throws Exception {
    args.add("shadow/test/ImportExportAssemblyTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "15\n");
  }

  @Test
  public void testImportExportNative() throws Exception {
    args.add("shadow/test/ImportExportNativeTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], "12\n");
  }

  @Test
  public void testMutex() throws Exception {
    args.add("shadow/test/MutexTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
        formatOutputString(
            "Nested locks",
            "Both locks",
            "shadow:natives@MutexException: Thread 'Thread#main' cannot unlock a Mutex it does not own.",
            "Done!"));
  }

  @Test
  public void testThreadSleep() throws Exception {
    args.add("shadow/test/ThreadSleepTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
        formatOutputString(
            "I am going to wait 3 seconds.",
            "I waited 3 seconds.",
            "I am a thread.",
            "I am a thread.",
            "I am a thread.",
            "true"));
  }

  @Test
  public void testThreadName() throws Exception {
    args.add("shadow/test/ThreadNameTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
            new String[0],
            // stdin
            formatOutputString("RZA 1", "GZA 2", "Method Man 3", "Raekwon 4", "Inspectah Deck 5", "done"));
  }

  @Test
  public void testComplexSendAndReceive() throws Exception {
    args.add("shadow/test/ComplexSendAndReceiveTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
        formatOutputString(
            "Test1",
            "Thread#2: hello world!",
            "Thread#3: hello world!",
            "Thread#4: hello world!",
            "Thread#5: hello world!",
            "Thread#main: stop",
            "Test2",
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
            "[10, 11, 12, 13, 14]"),
        "shadow:standard@IncompatibleMessageTypeException: Expected 'int' but got 'shadow:standard@String'.\n");
  }

  @Test
  public void testInterrupt() throws Exception {
    args.add("shadow/test/InterruptTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
        formatOutputString(
            "waiting",
            "I am Thread#2",
            "Thread#2: 0",
            "I am Thread#3",
            "Thread#3: 1",
            "end",
            "InterruptedException thrown",
            "1",
            "done"));
  }

  @Test
  public void testSendAndReceive() throws Exception {
    args.add("shadow/test/SendAndReceiveTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
        formatOutputString(
            "Thread#1 received: aardvark from Thread#main",
            "Thread#2 received: formulation from Thread#main"));
  }

  @Test
  public void testSimpleThread() throws Exception {
    args.add("shadow/test/SimpleThreadTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
        // stdin
        formatOutputString("Printing on a separate thread", "Joined thread"));
  }

  @Test
  public void testAttributeUsage() throws Exception {
    args.add("shadow/test/AttributeUsageTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
        // stdin
        formatOutputString("42", "19"));
  }

  @Test
  public void testThread() throws Exception {
    args.add("shadow/test/ThreadTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(
        new String[0],
        // stdin
        formatOutputString(
            "Thread#main",
            "Thread#1",
            "Thread#2",
            "Thread#3",
            "Thread#4",
            "Thread#5",
            "true",
            "true",
            "Thread#6",
            "Thread#7",
            "Thread#8"),

        // stderr
        formatOutputString(
            "Uncaught Thread Exception @Thread#3:",
            "   Propagated from: (Thread#3)",
            "   Throwing: (shadow:standard@Exception: from Thread#3)",
            "Uncaught Thread Exception @Thread#4:",
            "   Propagated from: (Thread#8 to Thread#6 to Thread#5 to Thread#4)",
            "   Throwing: (shadow:standard@Exception: from Thread#8)",
            "Uncaught Thread Exception @Thread#7:",
            "   Propagated from: (Thread#7)",
            "   Throwing: (shadow:standard@Exception: from Thread#7)"));
  }

  @Test
  public void testTLSThread() throws Exception {
    args.add("shadow/test/TLSTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], formatOutputString("1", "1"));
  }

  @Test
  public void testThreadIsolatedRunner() throws Exception {
    args.add("shadow/test/ThreadIsolatedRunnerTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    run(new String[0], formatOutputString("1 1", "1 1", "1 1", "0"));
  }
}
