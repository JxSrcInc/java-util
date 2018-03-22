package jxsource.util.cl.cff;

import java.util.Vector;

public class CONSTANT_Methodref_Info
{	public int tag;
	public int class_index;
	public int name_and_type_index;
	public int index;
	private Vector constant_pool;

	CONSTANT_Methodref_Info(int tag, int class_index, int name_and_type_index, int index, Vector constant_pool)
	{	this.tag = tag;
		this.class_index = class_index;
		this.name_and_type_index = name_and_type_index;
		this.index = index;
		this.constant_pool = constant_pool;
	}

	public String getClassName()
	{	return ((CONSTANT_Utf8_Info)constant_pool.get(((CONSTANT_Class_Info)constant_pool.get(class_index)).name_index)).value;
	}

	public String getName()
	{	return ((CONSTANT_Utf8_Info)constant_pool.get(
			((CONSTANT_NameAndType_Info)constant_pool.get(name_and_type_index)).name_index)).value;
	}

	public String getDescriptor()
	{	return ((CONSTANT_Utf8_Info)constant_pool.get(
			((CONSTANT_NameAndType_Info)constant_pool.get(name_and_type_index)).descriptor_index)).value;
	}

	public boolean equals(Object obj)
	{	if(obj instanceof CONSTANT_Methodref_Info)
		{ CONSTANT_Methodref_Info cmi = (CONSTANT_Methodref_Info) obj;
			return (cmi.getClassName().equals(getClassName()) &&
							cmi.getName().equals(getName()) &&
							cmi.getDescriptor().equals(getDescriptor()));
		}
		return false;
	}

}
