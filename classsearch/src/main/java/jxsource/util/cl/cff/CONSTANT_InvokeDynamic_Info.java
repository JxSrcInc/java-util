package jxsource.util.cl.cff;

import java.util.Vector;

/**
 * Add for new Class format.
 * No validation for bootstrap_method_attr_index and name_and_type_index
 * @author JiangJxSrc
 *
 */
public class CONSTANT_InvokeDynamic_Info
{	public int tag;
	public int bootstrap_method_attr_index;
	public int name_and_type_index;
	public int index;
	private Vector constant_pool;

	CONSTANT_InvokeDynamic_Info(int tag, int bootstrap_method_attr_index, int name_and_type_index, int index, Vector constant_pool)
	{	this.tag = tag;
		this.bootstrap_method_attr_index = bootstrap_method_attr_index;
		this.name_and_type_index = name_and_type_index;
		this.index = index;
		this.constant_pool = constant_pool;
	}

}
