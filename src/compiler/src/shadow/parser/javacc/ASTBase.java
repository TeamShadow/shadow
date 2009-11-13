package shadow.parser.javacc;


public abstract class ASTBase implements Node {
    protected Node parent;
    protected Node[] children;
    protected int id;
    protected ShadowParser parser;
    protected String image;
    
    public ASTBase(int id) {
    	this.id = id;
    }
    
    public ASTBase(ShadowParser sp, int id) {
    	this.id = id;
    	parser = sp;
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
        System.out.println(prefix + (image == null ? "" : ":" + image));
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
