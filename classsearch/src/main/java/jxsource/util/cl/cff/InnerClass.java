package jxsource.util.cl.cff;

import java.util.Vector;

public class InnerClass {
		
	public static final String Anonymous = "Anonymous";
	public	int inner_class_info_index;
	public 	int outer_class_info_index;
	public	int inner_name_index;
	public 	int inner_class_access_flags;
	protected Vector constant_pool;
	
		public InnerClass copy()
		{
			InnerClass ic = new InnerClass();
			ic.inner_class_info_index = inner_class_info_index;
			ic.outer_class_info_index = outer_class_info_index;
			ic.inner_name_index = inner_name_index;
			ic.inner_class_access_flags = inner_class_access_flags;
			ic.constant_pool = constant_pool;
			return ic;
		}
		
		public String getName()
		{	
			if(inner_name_index != 0)
				return ((CONSTANT_Utf8_Info)constant_pool.get(inner_name_index)).value;
			else
				return Anonymous;
		}
		
		public String getInnerClassInfo()
		{	
			if(inner_class_info_index != 0)
				return ((CONSTANT_Class_Info)constant_pool.get(inner_class_info_index)).getClassName();
			else
				return "inner_class_info_index=0";
		}

		public String getOuterClassInfo()
		{	
			if(outer_class_info_index != 0)
				return ((CONSTANT_Class_Info)constant_pool.get(outer_class_info_index)).getClassName();
			else
				return "outer_class_info_index=0";
		}

		public String toString()
		{
			return "in_cl_info="+Integer.toString(inner_class_info_index)+"-"+getInnerClassInfo()+", "+
			"out_cl_info="+Integer.toString(outer_class_info_index)+"-"+getOuterClassInfo()+", "+
			"name="+Integer.toString(inner_name_index)+"-"+getName()+", "+
			"flags="+Integer.toString(inner_class_access_flags);
		}
}
