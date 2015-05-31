package shadow.parser.javacc;

import java.io.File;

import org.apache.logging.log4j.Logger;

import shadow.Loggers;
import shadow.typecheck.type.Documentation;
import shadow.typecheck.type.InstantiationException;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class SimpleNode implements Node {
	private static final Logger logger = Loggers.TYPE_CHECKER;
	
    protected Node parent;
    protected Node[] children;
    protected int id;
    //protected ShadowParser parser;
    
    protected String image;
    protected File file;
    protected int lineStart, lineEnd, columnStart, columnEnd;
    
    protected Type type;		// used by the type checker    
    protected Modifiers modifiers = new Modifiers(); 	// used by the type checker
    
    // Used to hold documentation comments regarding this node
    protected Documentation documentation = new Documentation();
    
	private Type enclosingType;	// used by the type checker (refers to the class where the node is used, for private/protected visibility)

	public SimpleNode(int id) {
    	this.id = id;
    	image = "";    	
    	lineStart = columnStart = columnEnd = -1;
    	type = null;    
    	enclosingType = null;
    }
    
    public SimpleNode(ShadowParser sp, int id) {
    	this.id = id;
    	//parser = sp;
    	image = "";
    	if( sp instanceof ShadowFileParser )
    		file = ((ShadowFileParser)sp).getFile();
    	lineStart = sp.token.next.beginLine;
    	lineEnd = sp.token.next.endLine;
    	columnStart = sp.token.next.beginColumn;
    	columnEnd = sp.token.next.endColumn;
    	type = null;    	
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
		//updates the ending line and column of the node based on children
		lineEnd = n.getLineEnd();
		columnEnd = n.getColumnEnd();		
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
	
	public void addToImage(char c) {
		this.image += c;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public int getLineStart() {
		return lineStart;
	}
	
	@Override
	public void setLineStart(int line) {
		this.lineStart = line;
	}
	
	@Override
	public int getLineEnd() {
		return lineEnd;
	}
	
	@Override
	public void setLineEnd(int line) {
		this.lineEnd = line;
	}
	
	@Override
	public int getColumnStart() {
		return columnStart;
	}

	@Override
	public void setColumnStart(int column) {
		this.columnStart = column;
	}
	
	@Override
	public void setColumnEnd(int column) {
		this.columnEnd = column;
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
    		logger.debug(prefix + className + "(" + lineStart + ":" + columnStart + ")");
    	else
    		logger.debug(prefix + className + "(" + lineStart + ":" + columnStart + "): " + image);
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
    
    public Modifiers getModifiers()
	{
		return modifiers;
	}
	
	public void setModifiers( Modifiers modifiers )
	{
		this.modifiers.setModifiers(modifiers.getModifiers());
	}

	public void setModifiers( int mods )
	{
		this.modifiers.setModifiers(mods);
	}
	
	public void addModifier( int mod )
	{
		modifiers.addModifier(mod);
	}
	
	public void removeModifier( int mod )
	{
		modifiers.removeModifier(mod);
	}
	
	@Override
	public Documentation getDocumentation()
	{
		return documentation;
	}
	
	@Override
	public void setDocumentation(Documentation documentation)
	{
		this.documentation = documentation;
	}
	
	@Override
	public void addDocumentationLine(String line)
	{
		documentation.addLine(line);
	}
	
	public void setEnclosingType(Type type)
	{
		enclosingType = type;
	}
	
	public Type getEnclosingType()
	{
		return enclosingType;
	}
	
	public boolean isField()
	{
		return modifiers.isField();
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
	
	public File getFile()
	{
		return file;
	}
	
	@Override
	public SimpleNode clone()
	{
		SimpleNode node = new SimpleNode(id);
		
		node.parent = parent;
		node.children = children;
		//node.parser = parser;
		node.image = image;
		node.file = file;
		node.lineStart = lineStart;
		node.columnStart = columnStart;
		node.columnEnd = columnEnd;
	    node.type = type;
	    node.modifiers = new Modifiers(modifiers);
	    node.enclosingType = enclosingType;	// used by the type checker (refers to the class were the node is used, for private/protected visibility)
	    
	    return node;
	}
	
	public ModifiedType replace(SequenceType values, SequenceType replacements) {
		try {
			return new SimpleModifiedType( type.replace(values, replacements), modifiers );
		}
		catch (InstantiationException e) 
		{
		}
		
		return this;
	}

	@Override
	public int getColumnEnd() {
		return columnEnd;
	}
	
	@Override
	public void setEndToken(Token t) {
		lineEnd = t.endLine;
		columnEnd = t.endColumn;
	}
}
