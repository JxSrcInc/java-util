package jxsource.util.cl.cff;

import java.util.Vector;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;

public class Field_Info implements Comparable
{	public int access_flags;
	public int name_index;
	public int descriptor_index;
	public int attributes_count;
	public Attribute_Info[] attribute_info;
	private Vector constant_pool;

	public Field_Info(int access_flags,
										int name_index,
										int descriptor_index,
										Attribute_Info[] attribute_info,
										Vector constant_pool)
	{	this.access_flags = access_flags;
		this.name_index = name_index;
		this.descriptor_index = descriptor_index;
		this.attribute_info = attribute_info;
		this.constant_pool = constant_pool;
	}

	public Field_Info(Vector constant_pool)
	{	this.constant_pool = constant_pool;
	}

	public int read(DataInputStream in) throws IOException
	{	access_flags = in.readUnsignedShort();
		name_index = in.readUnsignedShort();
		descriptor_index = in.readUnsignedShort();
		attribute_info = new Attribute_Info[in.readUnsignedShort()];
		int attribute_len = 0;
		for(int i=0; i<attribute_info.length; i++)
		{	attribute_info[i] = new Attribute_Info(constant_pool);
			attribute_len += attribute_info[i].read(in);
		}
		return 8 + attribute_len;
	}

	public int write(DataOutputStream out) throws IOException
	{	out.writeShort(access_flags);
		out.writeShort(name_index);
		out.writeShort(descriptor_index);
		out.writeShort(attribute_info.length);
		int attribute_len = 0;
		for(int i=0; i<attribute_info.length; i++)
		{	attribute_len += attribute_info[i].write(out);
		}
		return 8 + attribute_len;
	}

	public String getName()
	{	return ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value;
	}

	public String getDescription()
	{	return ((CONSTANT_Utf8_Info)constant_pool.get(descriptor_index)).value;
	}

	public String toString()
	{	String str = "field: name="+Integer.toString(name_index)+"-"+((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value+", ";
		str += "desc="+Integer.toString(descriptor_index)+"-"+((CONSTANT_Utf8_Info)constant_pool.get(descriptor_index)).value+", ";
		str += "acc_flags="+Integer.toString(access_flags)+"\n";
		for(int i=0; i<attribute_info.length; i++)
		{	str += "\t"+attribute_info[i].toString();
		}
		return str;
	}
	
	public int compareTo(Object obj)
	{	if(obj instanceof Field_Info)
		{	Field_Info mi = (Field_Info) obj;
			if(getName().equals(mi.getName()))
			{	return getDescription().compareTo(mi.getDescription());
			} else
			{ return getName().compareTo(mi.getName());
			}
		} 
		return -1;
	}
}

								
