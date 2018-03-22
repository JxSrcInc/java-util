package jxsource.util.cl.cff;

import java.util.Vector;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;

public class Exceptions_Info extends Attribute_Info
{	
	public int length;
	public int number_of_exceptions;
	public int[] exception_index_table;

	public Attribute_Info copy()
	{	Exceptions_Info copyInfo = new Exceptions_Info(constant_pool);
		copyInfo.name_index = name_index;
		copyInfo.length = length;
		copyInfo.number_of_exceptions = number_of_exceptions;
		copyInfo.exception_index_table = new int[number_of_exceptions];
		for(int i=0; i<number_of_exceptions; i++)
		{	copyInfo.exception_index_table[i] = exception_index_table[i];
		}
		return copyInfo;
	}

	public Exceptions_Info(Vector constant_pool)
	{	super(constant_pool);
	}

	public Exceptions_Info() {}

	public void setConstantPool(Vector constant_pool)
	{	super.setConstantPool(constant_pool);
	}

	public int read(int name_index, int length, DataInputStream in) throws IOException
	{	this.name_index = name_index;
		this.length = length;
		number_of_exceptions = in.readUnsignedShort();
		exception_index_table = new int[number_of_exceptions];
		for(int i=0; i<number_of_exceptions; i++)
		{	exception_index_table[i] = in.readUnsignedShort();
		}
		return 6 + length;
	}

	public int write(DataOutputStream out) throws IOException
	{	out.writeShort(name_index);
		out.writeInt(length);
		out.writeShort(number_of_exceptions);
		for(int i=0; i<number_of_exceptions; i++)
		{	out.writeShort(exception_index_table[i]);
		}
		return 6 + length;
	}
		
	public String toString()
	{	String str = "attribute: name="+Integer.toString(name_index)+"-";
		str += ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value+", length="+Integer.toString(length)+", ";
		str += "table_length="+Integer.toString(number_of_exceptions)+"\n";
		String[] exceptions = getExceptions();
		for(int i=0; i<number_of_exceptions; i++)
		{	str += "\t\t"+Integer.toString(exception_index_table[i])+" - "+exceptions[i]+"\n";
		}
		str += "\n";
		return str;
	}
	
	public String[] getExceptions()
	{
		String[] exceptions = new String[number_of_exceptions];
		for(int i=0; i<number_of_exceptions; i++)
			exceptions[i] = ((CONSTANT_Utf8_Info)constant_pool.get(((CONSTANT_Class_Info)constant_pool.get(exception_index_table[i])).name_index)).value;
		return exceptions;
	}
	
}	
