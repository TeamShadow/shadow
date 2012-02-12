package shadow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Loggers {
	public static final Log SHADOW = LogFactory.getLog("shadow");
	public static final Log PARSER = LogFactory.getLog("shadow-parser");
	public static final Log TYPE_CHECKER = LogFactory.getLog("shadow-typechecker");
	public static final Log TAC = LogFactory.getLog("shadow-tac");
	public static final Log OUTPUT = LogFactory.getLog("shadow-output");
	
	public static void setAllToDebug() {
		setAllToLevel(Level.DEBUG);
	}
	
	public static void setAllToLevel(Level level) {
		Logger.getLogger("shadow").setLevel(level);
		Logger.getLogger("shadow-parser").setLevel(level);
		Logger.getLogger("shadow-typechecker").setLevel(level);
		Logger.getLogger("shadow-tac").setLevel(level);
		Logger.getLogger("shadow-output").setLevel(level);
	}
	
	public static void setLoggerToLevel(Log logger, Level level) {
		if(logger == SHADOW) {
			Logger.getLogger("shadow").setLevel(level);
		} else if(logger == PARSER) {
			Logger.getLogger("shadow-parser").setLevel(level);
		} else if(logger == TYPE_CHECKER) {
			Logger.getLogger("shadow-typechecker").setLevel(level);
		} else if(logger == TAC) {
			Logger.getLogger("shadow-tac").setLevel(level);
		} else if(logger == OUTPUT) {
			Logger.getLogger("shadow-output").setLevel(level);
		}
	}
	
	public static Level getLoggerLevel(Log logger) {
		if(logger == SHADOW) {
			return Logger.getLogger("shadow").getLevel();
		} else if(logger == PARSER) {
			return Logger.getLogger("shadow-parser").getLevel();
		} else if(logger == TYPE_CHECKER) {
			return Logger.getLogger("shadow-typechecker").getLevel();
		} else if(logger == TAC) {
			return Logger.getLogger("shadow-tac").getLevel();
		} else if (logger == OUTPUT) {
			return Logger.getLogger("shadow-output").getLevel();
		} else
			return null;
	}
}
