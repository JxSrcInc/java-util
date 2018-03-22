package jxsource.util.cl.cff;

import java.util.Vector;

public class CONSTANT_Integer_Info
{	public int tag;
	public int value;
	public int index;
	private Vector constant_pool;

	CONSTANT_Integer_Info(int tag, int value, int index, Vector constant_pool)
	{	this.tag = tag;
		this.value = value;
		this.index = index;
		this.constant_pool = constant_pool;
	}
}
	
