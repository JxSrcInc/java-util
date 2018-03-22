package jxsource.util.cl.cff;

import java.util.Vector;

public class CONSTANT_String_Info
{	public int tag;
	public int string_index;
	public int index;
	private Vector constant_pool;

	public CONSTANT_String_Info(int tag, int string_index, int index, Vector constant_pool)
	{	this.tag = tag;
		this.string_index = string_index;
		this.index = index;
		this.constant_pool = constant_pool;
	}
}

