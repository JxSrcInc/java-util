package jxsource.util.cl.cff;

import java.util.Vector;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;

public class Method_Info implements Comparable
{	
	public static final String Code = "Code";
	public static final String Exceptions = "Exceptions";
	public int access_flags;
	public int name_index;
	public int descriptor_index;
	public int attributes_count;
	public Attribute_Info[] attribute_info;
	public Vector constant_pool;

	protected String name;

	public Method_Info copy()
	{ Method_Info copyInfo = new Method_Info(constant_pool);
		copyInfo.access_flags = access_flags;
		copyInfo.name_index = name_index;
		copyInfo.descriptor_index = descriptor_index;
		copyInfo.attributes_count = attributes_count;
		copyInfo.attribute_info = new Attribute_Info[attribute_info.length];
		for(int i=0; i<attribute_info.length; i++)
			copyInfo.attribute_info[i] = attribute_info[i].copy();
		return copyInfo;
	}

	public Method_Info() {}

	public Method_Info(Vector constant_pool)
	{	this.constant_pool = constant_pool;
	}

	public void setConstantPool(Vector constant_pool)
	{	this.constant_pool = constant_pool;
		for(int i=0; i<attribute_info.length; i++)
			attribute_info[i].setConstantPool(constant_pool);
	}

	public Attribute_Info getAttribute(String name)
	{	for(int i=0; i<attribute_info.length; i++)
		{	if(attribute_info[i].getName().equals(name))
				return attribute_info[i];
		}
		return null;
	}

	public String getName()
	{	return ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value;
	}

	public String getDescription()
	{	return ((CONSTANT_Utf8_Info)constant_pool.get(descriptor_index)).value;
	}
	
	public Code_Info get_Code_Info()
	{
		return (Code_Info) getAttribute(Code);
	}

	public int read(DataInputStream in) throws IOException
	{	access_flags = in.readUnsignedShort();
		name_index = in.readUnsignedShort();
		descriptor_index = in.readUnsignedShort();
		attribute_info = new Attribute_Info[in.readUnsignedShort()];
		int attribute_len = 0;
		for(int i=0; i<attribute_info.length; i++)
		{	int name_index = in.readUnsignedShort();
			int length = in.readInt();
			if(((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value.equals(Code))
				attribute_info[i] = new Code_Info(constant_pool);
			else
			if(((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value.equals(Exceptions))
				attribute_info[i] = new Exceptions_Info(constant_pool);
			else
				attribute_info[i] = new Attribute_Info(constant_pool);
			attribute_len += attribute_info[i].read(name_index, length, in);
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

	public String toString()
	{	String str = "method: name="+Integer.toString(name_index)+"-"+getName()+", ";
		str += "desc="+Integer.toString(descriptor_index)+"-"+getDescription()+", ";
		str += "acc_flags="+Integer.toHexString(access_flags)+"\n";

		for(int i=0; attribute_info != null && i<attribute_info.length; i++)
		{	str += "\t"+attribute_info[i].toString();
		}

		return str;
	}

	public int compareTo(Object obj)
	{	if(obj instanceof Method_Info)
		{	Method_Info mi = (Method_Info) obj;
			if(getName().equals(mi.getName()))
			{	return getDescription().compareTo(mi.getDescription());
			} else
			{ return getName().compareTo(mi.getName());
			}
		} 
		return -1;
	}

	
}

