package jxsource.util.cl.cff;

import java.util.Vector;

public class CONSTANT_Double_Info
{	public int tag;
	public double value;
	public int index;
	private Vector constant_pool;

	CONSTANT_Double_Info(int tag, double value, int index, Vector constant_pool)
	{	this.tag = tag;
		this.value = value;
		this.index = index;
		this.constant_pool = constant_pool;
	}
}

