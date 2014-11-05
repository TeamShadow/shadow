package shadow;

import java.io.File;
import java.net.URL;

import org.apache.commons.digester3.Digester;

public class ConfigParser {

	private Digester digester = new Digester();

	public ConfigParser(Configuration config) {

		digester.setValidating(false);

		// put the config object on the stack
		digester.push(config);

		digester.addSetProperties("shadow");	// root element w/properties

		// parse the system path
		digester.addCallMethod("shadow/system", "setSystemImport", 1);
		digester.addCallParam("shadow/system", 0);

		// parse the import paths
		digester.addCallMethod("shadow/import", "addImport", 1);
		digester.addCallParam("shadow/import", 0);
		
		digester.addCallMethod("shadow/link", "setLinkCommand", 1);
		digester.addCallParam("shadow/link", 0);
		
		// Parse the LLVM target "triple"
		digester.addCallMethod("shadow/target", "setTarget", 1);
		digester.addCallParam("shadow/target", 0);
	}

	public void parse(File configFile) {
		try {
			if(!configFile.exists()) {
				System.err.println("Cannot find configuration file: " + configFile.getAbsolutePath());
				return;
			}

			digester.parse(configFile);
		} catch(Exception e) {
			System.err.println("ERROR PARSING CONFIGURATION FILE: " + configFile.getAbsolutePath());
			e.printStackTrace();
		}
	}

	public void parse(URL configFile) {
		try {
			digester.parse(configFile);
		} catch (Exception e) {
			System.err.println("ERROR PARSING CONFIGURATION FILE: " + configFile);
			e.printStackTrace();
		}
	}
}
