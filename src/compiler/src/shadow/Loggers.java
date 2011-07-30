package shadow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Loggers {
	public static final Log SHADOW = LogFactory.getLog("shadow");
	public static final Log PARSER = LogFactory.getLog("shadow-parser");
	public static final Log TYPE_CHECKER = LogFactory.getLog("shadow-typechecker");
	public static final Log TAC = LogFactory.getLog("shadow-tac");
	public static final Log OUTPUT = LogFactory.getLog("shadow-code");
}
