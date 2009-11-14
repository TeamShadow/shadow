package shadow.parser.javacc;

public class SimpleNode implements Node {
    protected Node parent;
    protected Node[] children;
    protected int id;
    protected ShadowParser parser;
    protected String image;
    protected int line, column;
    
    public SimpleNode(int id) {
    	this.id = id;
    	image = null;
    	line = column = -1;
    }
    
    public SimpleNode(ShadowParser sp, int id) {
    	this.id = id;
    	parser = sp;
    	image = null;
    	line = sp.token.beginLine;
    	column = sp.token.beginColumn;
    }
    
	@Override
	public void jjtAddChild(Node n, int i) {
		if(children == null) {
			children = new Node[i + 1];
		} else if(i >= children.length) {
			Node c[] = new Node[i + 1];
			System.arraycopy(children, 0, c, 0, children.length);
			children = c;
		}
		children[i] = n;
	}

    public Object jjtAccept(ShadowParserVisitor visitor, Object data) {
    	return visitor.visit(this, data);
    }

	@Override
	public void jjtOpen() {
	}

	@Override
	public void jjtClose() {
	}

	@Override
	public Node jjtGetChild(int i) {
		return children[i];
	}

	@Override
	public int jjtGetNumChildren() {
		return (children == null) ? 0 : children.length;
	}

	@Override
	public Node jjtGetParent() {
		return parent;
	}

	@Override
	public void jjtSetParent(Node n) {
		parent = n;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}

    public void dump(String prefix) {
    	String className = this.getClass().getSimpleName();
    	if(image == null)
    		System.out.println(prefix + className);
    	else
    		System.out.println(prefix + className + ": " + image);
        dumpChildren(prefix);
    }

    protected void dumpChildren(String prefix) {
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                SimpleNode n = (SimpleNode) children[i];
                if (n != null) {
                    n.dump(prefix + " ");
                }
            }
        }
    }
}
