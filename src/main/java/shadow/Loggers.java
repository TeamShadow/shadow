package shadow;

/*import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;


public class Loggers
{
	public static final Logger SHADOW =  LoggerFactory.getLogger("Shadow");
	public static final Logger PARSER = LoggerFactory.getLogger("Shadow Parser");
	public static final Logger TYPE_CHECKER = LoggerFactory.getLogger("Shadow Type Checker");
	public static final Logger TAC = LoggerFactory.getLogger("Shadow TAC");
	public static final Logger OUTPUT = LoggerFactory.getLogger("Shadow Output");
	public static final Logger DOC_TOOL = LoggerFactory.getLogger("Shadow Documentation Tool");
	
	public static void setAllToLevel(Level level)
	{		
		/*LoggerContext context = (LoggerContext)LogManager.getContext(false);
		Configuration config = context.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		loggerConfig.setLevel(level);		
		context.updateLoggers();*/
		
		
		//TODO: FIX LOGGING LEVELS
	}
}
