package jxsource.util.cl.cff;

import java.util.Vector;

public class CONSTANT_Utf8_Info
{	public int tag;
	public String value;
	public int index;
	private Vector constant_pool;

	public CONSTANT_Utf8_Info(int tag, String value, int index, Vector constant_pool)
	{	this.tag = tag;
		this.value = value;
		this.index = index;
		this.constant_pool = constant_pool;
	}
}	

