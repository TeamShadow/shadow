package shadow.test;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.AfterAll;
import org.junit.platform.runner.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import junit.framework.TestCase;
import shadow.test.doctool.DocumentationTests;
import shadow.test.output.OutputTests;
import shadow.test.output.TACTests;
import shadow.test.typecheck.StandardLibraryTests;
import shadow.test.typecheck.TypeCheckerTests;
import shadow.test.typecheck.UtilityTests;
import shadow.test.typecheck.WarningTests;

@RunWith(JUnitPlatform.class)
@SelectClasses({DocumentationTests.class, OutputTests.class, TACTests.class, shadow.test.output.NegativeTests.class, shadow.test.parse.NegativeTests.class, shadow.test.typecheck.NegativeTests.class, UtilityTests.class, StandardLibraryTests.class, TypeCheckerTests.class, WarningTests.class })
public class AllTest extends TestCase {

	@AfterAll
    public static void ringBell() {
		ClassLoader classLoader = AllTest.class.getClassLoader();
        File file;
		try {
			file = new File( URLDecoder.decode( classLoader.getResource("chime.wav").getFile(), "UTF-8" ) );
			playClip( file );		
		} catch ( IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
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
	  AudioFormat format = audioInputStream.getFormat();
	  DataLine.Info info = new DataLine.Info(Clip.class, format);
	  try {
	    Clip clip = (Clip)AudioSystem.getLine(info);
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
