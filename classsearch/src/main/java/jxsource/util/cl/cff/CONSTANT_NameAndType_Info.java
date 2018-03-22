package jxsource.util.cl.cff;

import java.util.Vector;

public class CONSTANT_NameAndType_Info
{	public int tag;
	public int name_index;
	public int descriptor_index;
	public int index;
	private Vector constant_pool;

	CONSTANT_NameAndType_Info(int tag, int name_index, int descriptor_index, int index, Vector constant_pool)
	{	this.tag = tag;
		this.name_index = name_index;
		this.descriptor_index = descriptor_index;
		this.index = index;
		this.constant_pool = constant_pool;
	}
}

