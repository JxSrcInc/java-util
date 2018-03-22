package jxsource.util.cl.cff;

import java.util.Vector;

public class LocalVariableTable implements Comparable{
		
	public	int start_pc;
	public 	int length;
	public	int name_index;
	public 	int descriptor_index;
	public	int index; // relative order ot the entry in LocalVariableTable (eg: 0, 1, 3, 4, 5, 7)
	protected Vector constant_pool;
	
		public LocalVariableTable copy()
		{
			LocalVariableTable lvt = new LocalVariableTable();
			lvt.start_pc = start_pc;
			lvt.length = length;
			lvt.name_index = name_index;
			lvt.descriptor_index = descriptor_index;
			lvt.index = index;
			lvt.constant_pool = constant_pool;
			return lvt;
		}
		
		public String getName()
		{	return ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value;
		}
		
		public String getDescriptor()
		{	return ((CONSTANT_Utf8_Info)constant_pool.get(descriptor_index)).value;
		}
		
		public String toString()
		{
			return Integer.toString(index)+","+getName()+","+getDescriptor();
		}
		
		// if < 0, input is less than this
		public int compareTo(Object obj)
		{
			return  index -((LocalVariableTable)obj).index;
		}
}
