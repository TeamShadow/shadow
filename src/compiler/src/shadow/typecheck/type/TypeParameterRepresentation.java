package shadow.typecheck.type;
import java.util.ArrayList;
import java.util.List;

import shadow.typecheck.type.Type.Kind;


/**
 * 
 * @author Barry Wittman
 * Class purely for sorting out type parameters inside TypeCollector
 * Information must be kept as Strings since types are unknown
 * TypeParameterRepresentation has lists of TypeParameterRepresentations inside since 
 * class Thing<U is Wombat<Thing is Stuff<Pig>>> is legal.
 */

public class TypeParameterRepresentation
{
	private String name;
	private List<TypeParameterRepresentation> bounds = new ArrayList<TypeParameterRepresentation>();

	public TypeParameterRepresentation(String name)
	{
		this.name = name;
	}
	
	public String getName() {
		return name;
	}	
	
	public void addBound(TypeParameterRepresentation bound) {
		bounds.add(bound);
	}
	
	public List<TypeParameterRepresentation> getBounds()
	{
		return bounds;
	}
	
	
}

