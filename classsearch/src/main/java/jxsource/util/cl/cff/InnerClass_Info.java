package jxsource.util.cl.cff;

import java.util.Vector;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;

public class InnerClass_Info extends Attribute_Info
{	
	public int length;
	public int classes_table_length;
	public InnerClass[] classes_table;

	public Attribute_Info copy()
	{	InnerClass_Info copyInfo = new InnerClass_Info(constant_pool);
		copyInfo.name_index = name_index;
		copyInfo.length = length;
		copyInfo.classes_table_length = classes_table_length;
		copyInfo.classes_table = new InnerClass[classes_table_length];
		for(int i=0; i<classes_table_length; i++)
		{	
			copyInfo.classes_table[i] = classes_table[i].copy();
		}
		return copyInfo;
	}

	public InnerClass_Info(Vector constant_pool)
	{	super(constant_pool);
	}

	public InnerClass_Info() {}

	public void setConstantPool(Vector constant_pool)
	{	super.setConstantPool(constant_pool);
	}

	public int read(int name_index, int length, DataInputStream in) throws IOException
	{	this.name_index = name_index;
		this.length = length;
		classes_table_length = in.readUnsignedShort();
		classes_table = new InnerClass[classes_table_length];
		for(int i=0; i<classes_table.length; i++)
		{	
			InnerClass ic = new InnerClass();
			ic.inner_class_info_index = in.readUnsignedShort();
			ic.outer_class_info_index = in.readUnsignedShort();
			ic.inner_name_index = in.readUnsignedShort();
			ic.inner_class_access_flags = in.readUnsignedShort();
			ic.constant_pool = constant_pool;
			classes_table[i] = ic;
		}
		return 6 + length;
	}

	public int write(DataOutputStream out) throws IOException
	{	out.writeShort(name_index);
		out.writeInt(length);
		out.writeShort(classes_table_length);
		for(int i=0; i<classes_table.length; i++)
		{	out.writeShort(classes_table[i].inner_class_info_index);
			out.writeShort(classes_table[i].outer_class_info_index);
			out.writeShort(classes_table[i].inner_name_index);
			out.writeShort(classes_table[i].inner_class_access_flags);
		}
		return 6 + length;
	}
		
	public String toString()
	{	String str = "attribute: name="+Integer.toString(name_index)+"-";
		str += ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value+", length="+Integer.toString(length)+", ";
		str += "table_length="+Integer.toString(classes_table.length)+"\n";
		for(int i=0; i<classes_table.length; i++)
		{	str += "\t"+classes_table[i].toString()+"\n";
		}
		str += "\n";
		return str;
	}
	
}	
