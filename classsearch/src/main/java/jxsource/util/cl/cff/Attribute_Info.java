package jxsource.util.cl.cff;

import java.util.Vector;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;

public class Attribute_Info
{	public int name_index;
	public byte[] info;
	protected Vector constant_pool;

	public Attribute_Info() {}

	public void setConstantPool(Vector constant_pool)
	{	this.constant_pool = constant_pool;
	}

	public Attribute_Info copy()
	{	Attribute_Info copyInfo = new Attribute_Info(constant_pool);
		copyInfo.name_index = name_index;
		copyInfo.info = new byte[info.length];
		for(int i=0; i<info.length; i++)
			copyInfo.info[i] = info[i];
		return copyInfo;
	}	

	public Attribute_Info(Vector constant_pool)
	{	this.constant_pool = constant_pool;
	}

	public String getName()
	{	return ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value;
	}

	public int read(DataInputStream in) throws IOException
	{	return read(in.readUnsignedShort(), in.readInt(), in);
	}

	public int read(int name_index, int length, DataInputStream in) throws IOException
	{	this.name_index = name_index;
		info = new byte[length];
		if(info.length > 0) in.readFully(info, 0, info.length);
		return 6 + info.length;
	}

	public int write(DataOutputStream out) throws IOException
	{	out.writeShort(name_index);
		out.writeInt(info.length);
		out.write(info,0,info.length);
		return 6 + info.length;
	}
		
	public String toString()
	{	String str = "attribute: name="+Integer.toString(name_index)+"-";
		str += ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value+", length="+Integer.toString(info.length)+"\n";
		return str;
	}
	
}	
								
