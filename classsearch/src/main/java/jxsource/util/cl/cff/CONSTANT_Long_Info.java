package jxsource.util.cl.cff;

import java.util.Vector;

public class CONSTANT_Long_Info
{	public int tag;
	public long value;
	public int index;
	private Vector constant_pool;

	CONSTANT_Long_Info(int tag, long value, int index, Vector constant_pool)
	{	this.tag = tag;
		this.value = value;
		this.index = index;
		this.constant_pool = constant_pool;
	}
}
		
