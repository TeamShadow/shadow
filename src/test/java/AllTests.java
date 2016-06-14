import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.TestCase;
import shadow.test.doctool.DocumentationTest;
import shadow.test.output.OutputTest;
import shadow.test.output.TACTest;
import shadow.test.typecheck.ShadowUtilityTest;
import shadow.test.typecheck.StandardLibraryTest;
import shadow.test.typecheck.TypeCheckerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({DocumentationTest.class, OutputTest.class, shadow.test.output.NegativeTests.class, OutputTest.class, TACTest.class, shadow.test.parse.NegativeTests.class, shadow.test.typecheck.NegativeTests.class, ShadowUtilityTest.class, StandardLibraryTest.class, TypeCheckerTest.class })
public class AllTests extends TestCase {

	@AfterClass
    public static void ringBell() {
		ClassLoader classLoader = AllTests.class.getClassLoader();
        File file = new File(classLoader.getResource("chime.wav").getFile());        
		try {
			playClip( file );
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private static void playClip(File clipFile) throws IOException, 
	  UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
	  class AudioListener implements LineListener {
	    private boolean done = false;
	    @Override public synchronized void update(LineEvent event) {
	      Type eventType = event.getType();
	      if (eventType == Type.STOP || eventType == Type.CLOSE) {
	        done = true;
	        notifyAll();
	      }
	    }
	    public synchronized void waitUntilDone() throws InterruptedException {
	      while (!done) { wait(); }
	    }
	  }
	  AudioListener listener = new AudioListener();
	  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
	  try {
	    Clip clip = AudioSystem.getClip();
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
