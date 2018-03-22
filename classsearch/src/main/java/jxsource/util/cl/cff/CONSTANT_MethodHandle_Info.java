package jxsource.util.cl.cff;

import java.util.Vector;

/**
 * Add for new Class format.
 * No validation for reference_kind and reference_index
 * @author JiangJxSrc
 *
 */
public class CONSTANT_MethodHandle_Info
{	public int tag;
	public int reference_kind;
	public int reference_index;
	public int index;
	private Vector constant_pool;

	CONSTANT_MethodHandle_Info(int tag, int reference_kind, int reference_index, int index, Vector constant_pool)
	{	this.tag = tag;
		this.reference_kind = reference_kind;
		this.reference_index = reference_index;
		this.index = index;
		this.constant_pool = constant_pool;
	}

}
