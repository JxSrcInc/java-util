package jxsource.util.cl.cff;

import java.util.Vector;

public class CONSTANT_Class_Info
{	public int tag;
	public int name_index;
	public int index;
	private Vector constant_pool;

	public CONSTANT_Class_Info(int tag, int name_index, int index, Vector constant_pool)
	{	this.tag = tag;
		this.name_index = name_index;
		this.index = index;
		this.constant_pool = constant_pool;
	}

	public String getClassName()
	{	return ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value;
	}

	public boolean equals(Object obj)
	{	if(obj instanceof CONSTANT_Class_Info)
			return ((CONSTANT_Class_Info)obj).getClassName().equals(getClassName());
		return false;
	}
}
	
