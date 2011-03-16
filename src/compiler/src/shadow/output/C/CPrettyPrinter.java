package shadow.output.C;

import java.io.PrintWriter;

import shadow.TAC.nodes.TACNode;

public class CPrettyPrinter {
	private int tabDepth = 0;
	private int COMMENT_WIDTH = 40;
	private int TAB_WIDTH = 5;
	
	private String prefix = "";
	
	private PrintWriter writer;
	
	public CPrettyPrinter(PrintWriter writer) {
		this.writer = writer;
	}
	
	public CPrettyPrinter(PrintWriter writer, String prefix) {
		this.writer = writer;
		this.prefix = prefix;
	}
	
	public CPrettyPrinter(PrintWriter writer, int commentWidth) {
		this.writer = writer;
		this.COMMENT_WIDTH = commentWidth;
	}
	
	public CPrettyPrinter(PrintWriter writer, int commentWidth, int tabWidth) {
		this.writer = writer;
		this.COMMENT_WIDTH = commentWidth;
		this.TAB_WIDTH = tabWidth;
	}
	
	/**
	 * Pretty print out the C code.
	 * @param str The generated C code.
	 * @param node The node that generated the code.
	 */
	public void print(String str, TACNode node) {
		int lineLength = 0;
		
		// print out the prefix
		writer.print(prefix);
		
		// print out the tab depths
		for(int i=0; i < tabDepth; ++i) {
			for(int j=0; j < TAB_WIDTH; ++j)
				writer.print(" ");
			lineLength += TAB_WIDTH;
		}
		
		// print out the actual code
		writer.print(str);
		lineLength += str.length();
		
		if(node != null) {
			// print out enough spaces to align everything
			for(int i=0; i < (COMMENT_WIDTH - lineLength); ++i) {
				writer.print(" ");
			}
			
			// print out the comment
			writer.println("/* " + node.getAstNode().getLocation() + " */");
		} else {
			writer.println();
		}
		
		writer.flush();
	}
	
	public void print(String str) {
		print(str, null);
	}

	public void indent() {
		++tabDepth;
	}
	
	public void outdent() {
		--tabDepth;
	}

}
