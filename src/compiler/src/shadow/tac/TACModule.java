package shadow.tac;

import java.util.ArrayList;
import java.util.List;

import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;

public class TACModule extends TACDeclaration
{
	private ClassInterfaceBaseType type;
	private String name;
	private List<TACField> fields;
	private List<TACMethod> methods;
	public TACModule(ClassInterfaceBaseType moduleType, String moduleName)
	{
		type = moduleType;
		name = moduleName;
		fields = new ArrayList<TACField>();
		methods = new ArrayList<TACMethod>();
	}

	public ClassInterfaceBaseType getType()
	{
		return type;
	}
	// FIXME:
	public ClassType getClassType()
	{
		return (ClassType)type;
	}
	public String getName()
	{
		return name;
	}

	public String getMangledName()
	{
		return type.getMangledName();
	}
	
	public void addField(TACField field)
	{
		fields.add(field);
	}
	public List<TACField> getFields()
	{
		return fields;
	}
	
	public void addMethod(TACMethod method)
	{
		methods.add(method);
	}
	public List<TACMethod> getMethods()
	{
		return methods;
	}
}
