package shadow.parser.javacc;

import shadow.typecheck.type.Type;
import shadow.typecheck.type.Visibility;

public class ModifiedNode extends SimpleNode
{	
	private int modifiers;
	private Visibility visibility;
	private Type enclosingType;
	
    public ModifiedNode(int id) {
    	super( id );    	 
    }
    
    public ModifiedNode(ShadowParser sp, int id) {
    	super( sp, id );
    }
    
    public int getModifiers()
	{
		return modifiers;
	}
	
	public void setModifiers( int modifiers )
	{
		this.modifiers = modifiers;
	}	
	
	public void setEnclosingType(Type type)
	{
		enclosingType = type;
	}
	
	public Type getEnclosingType()
	{
		return enclosingType;
	}
	
	public void setVisibility( Visibility visibility )
	{
		this.visibility = visibility;
	}
	
	public Visibility getVisibility()
	{
		return visibility;
	}
    

}
