package shadow;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.Configuration;


public class Loggers
{
	public static final Logger SHADOW =  LogManager.getLogger("Shadow");
	public static final Logger PARSER = LogManager.getLogger("Shadow Parser");
	public static final Logger TYPE_CHECKER = LogManager.getLogger("Shadow Type Checker");
	public static final Logger TAC = LogManager.getLogger("Shadow TAC");
	public static final Logger OUTPUT = LogManager.getLogger("Shadow Output");
	
	public static void setAllToLevel(Level level)
	{
		LoggerContext context = (LoggerContext)LogManager.getContext(false);
		Configuration config = context.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		loggerConfig.setLevel(level);		
		context.updateLoggers();
	}
}
