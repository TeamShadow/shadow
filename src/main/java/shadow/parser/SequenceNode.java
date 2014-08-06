package shadow.parser;

import shadow.parser.javacc.ShadowParser;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;

public class SequenceNode extends SimpleNode {

	public SequenceNode(int id) {
    	super(id);
    	setType(new SequenceType());
    }

    public SequenceNode(ShadowParser sp, int id) {
    	super(sp, id);
    	setType(new SequenceType());
    }

	@Override
	public SequenceType getType()
	{
		return (SequenceType)(super.getType());
	}

	public void addType(ModifiedType type)
	{
		getType().add(type);
	}
}
