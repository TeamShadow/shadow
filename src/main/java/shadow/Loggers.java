package shadow;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Loggers
{
	public static final Logger SHADOW =  Logger.getLogger("Shadow");
	public static final Logger PARSER = Logger.getLogger("Shadow Parser");
	public static final Logger TYPE_CHECKER = Logger.getLogger("Shadow Type Checker");
	public static final Logger TAC = Logger.getLogger("Shadow TAC");
	public static final Logger OUTPUT = Logger.getLogger("Shadow Output");
	public static final Logger DOC_TOOL = Logger.getLogger("Shadow Documentation Tool");
	
	public static void setAllToLevel(Level level)
	{
		SHADOW.setLevel(level);
		PARSER.setLevel(level);
		TYPE_CHECKER.setLevel(level);
		TAC.setLevel(level);
		OUTPUT.setLevel(level);
		DOC_TOOL.setLevel(level);
	}
}
