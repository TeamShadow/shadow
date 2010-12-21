package shadow.parser.javacc;

import shadow.typecheck.type.Type;

public class SimpleNode implements Node {
    protected Node parent;
    protected Node[] children;
    protected int id;
    protected ShadowParser parser;
    
    protected String image;
    protected int line, column;
    
    protected Type type;		// used by the type checker    
    protected int modifiers; 	// used by the type checker
	private Type enclosingType;	// used by the type checker (refers to the class were the node is used, for private/protected visibility)    

	public SimpleNode(int id) {
    	this.id = id;
    	image = "";
    	line = column = -1;
    	type = null;
    	modifiers = 0;
    	enclosingType = null;
    }
    
    public SimpleNode(ShadowParser sp, int id) {
    	this.id = id;
    	parser = sp;
    	image = "";
    	line = sp.token.beginLine;
    	column = sp.token.beginColumn;
    	type = null;
    	modifiers = 0;
    	enclosingType = null;
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
	
	public void jjtSwapChild(Node n, int i) {
		children[i] = n;
	}

    public Object jjtAccept(ShadowParserVisitor visitor, Boolean secondVisit) throws ShadowException {
    	return visitor.visit(this, secondVisit);
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
		if(image == null) {
			return type.getTypeName();
		} else {
			return image;
		}
	}
	
	public boolean isImageNull() {
		return image == null || image.length() == 0;
	}
	
	public void setImage(String image) {
		this.image = image;
	}

	public int getLine() {
		return line;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public Type getType() {
		return type;		
	}
	
	public void setType(Type type) {
		this.type = type;
	}

    public void dump(String prefix) {
    	String className = this.getClass().getSimpleName();
    	if(image == null || image.length() == 0)
    		System.out.println(prefix + className + "(" + line + ":" + column + ")");
    	else
    		System.out.println(prefix + className + "(" + line + ":" + column + "): " + image);
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
    
    public int getModifiers()
	{
		return modifiers;
	}
	
	public void setModifiers( int modifiers )
	{
		this.modifiers = modifiers;
	}	
	
	public void addModifier( int mod )
	{
		modifiers |= mod;
	}
	
	public void removeModifier( int mod )
	{
		modifiers &= ~mod;
	}
	
	public void setEnclosingType(Type type)
	{
		enclosingType = type;
	}
	
	public Type getEnclosingType()
	{
		return enclosingType;
	} 
	
	public String toString()
	{
		if( image.isEmpty() )
		{
			String output = "";
			for( int i = 0; i < this.jjtGetNumChildren(); i++ )
				output += jjtGetChild(i) + " ";
			return output;			
		}
		else
			return image;
	}
}
