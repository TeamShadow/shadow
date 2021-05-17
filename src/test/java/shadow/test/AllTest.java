package shadow.test;

import junit.framework.TestCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import shadow.test.doctool.DocumentationTests;
import shadow.test.interpreter.InterpreterTests;
import shadow.test.output.OutputTests;
import shadow.test.output.TACTests;
import shadow.test.typecheck.*;

import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

@RunWith(JUnitPlatform.class)
@SelectClasses({
  DocumentationTests.class,
  OutputTests.class,
  TACTests.class,
  shadow.test.interpreter.NegativeTests.class,
  shadow.test.parse.NegativeTests.class,
  shadow.test.typecheck.NegativeTests.class,
  UtilityTests.class,
  StandardLibraryTests.class,
  TypeCheckerTests.class,
  WarningTests.class,
  ImportTests.class,
  InterpreterTests.class
})
public class AllTest extends TestCase {

  @AfterAll
  public static void ringBell() {
    ClassLoader classLoader = AllTest.class.getClassLoader();
    File file;
    try {
      //noinspection ConstantConditions
      file =
          new File(
              URLDecoder.decode(classLoader.getResource("resources/chime.wav").getFile(), "UTF-8"));
      playClip(file);
    } catch (IOException
        | UnsupportedAudioFileException
        | LineUnavailableException
        | InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void playClip(File clipFile)
      throws IOException, UnsupportedAudioFileException, LineUnavailableException,
          InterruptedException {
    class AudioListener implements LineListener {
      private boolean done = false;

      @Override
      public synchronized void update(LineEvent event) {
        Type eventType = event.getType();
        if (eventType == Type.STOP || eventType == Type.CLOSE) {
          done = true;
          notifyAll();
        }
      }

      public synchronized void waitUntilDone() throws InterruptedException {
        while (!done) {
          wait();
        }
      }
    }
    AudioListener listener = new AudioListener();
    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
    AudioFormat format = audioInputStream.getFormat();
    DataLine.Info info = new DataLine.Info(Clip.class, format);
    try {
      Clip clip = (Clip) AudioSystem.getLine(info);
      clip.addLineListener(listener);
      clip.open(audioInputStream);
      try {
        clip.start();
        listener.waitUntilDone();
      } finally {
        clip.close();
      }
    } finally {
      audioInputStream.close();
    }
  }
}
