package jxsource.util.cl.cff;

import java.util.Vector;

/**
 * Add for new Class format.
 * No validation for descriptor_index
 * @author JiangJxSrc
 *
 */
public class CONSTANT_MethodType_Info
{	public int tag;
	public int descriptor_index;
	public int index;
	private Vector constant_pool;

	CONSTANT_MethodType_Info(int tag, int descriptor_index, int index, Vector constant_pool)
	{	this.tag = tag;
		this.descriptor_index = descriptor_index;
		this.index = index;
		this.constant_pool = constant_pool;
	}

}
