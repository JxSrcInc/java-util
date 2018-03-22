package jxsource.util.cl.cff;

import java.util.Vector;

public class CONSTANT_Float_Info
{	public int tag;
	public float value;
	public int index;
	private Vector constant_pool;

	CONSTANT_Float_Info(int tag, float value, int index, Vector constant_pool)
	{	this.tag = tag;
		this.value = value;
		this.index = index;
		this.constant_pool = constant_pool;
	}
}

