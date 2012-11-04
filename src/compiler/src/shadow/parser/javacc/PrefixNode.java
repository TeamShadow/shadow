package shadow.parser.javacc;

public class PrefixNode extends SimpleNode {

	private boolean prefix = false;	
	
	public PrefixNode(int id) {
    	super(id);
    }
    
    public PrefixNode(ShadowParser sp, int id) {
    	super(sp, id);
    }
    
	public boolean hasPrefix() {
		return prefix;
	}
	
	public void setPrefix(boolean prefix) {
		this.prefix = prefix;
	}
  
    
    
}
