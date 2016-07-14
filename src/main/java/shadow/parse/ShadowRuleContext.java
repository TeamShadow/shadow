package shadow.parse;

import org.antlr.v4.runtime.ParserRuleContext;

import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowRuleContext extends ParserRuleContext implements ModifiedType {
	private Type type;	
	private Modifiers modifiers = new Modifiers();
	
	public ShadowRuleContext()
	{}
	
	public ShadowRuleContext(ParserRuleContext parent, int invokingStateNumber)
	{
		super(parent, invokingStateNumber);
	}
	
	public void setType(Type type) 
	{
		this.type = type;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public Modifiers getModifiers()
	{
		return modifiers;
	}
}
