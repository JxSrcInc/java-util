package jxsource.util.cl.cff;

import java.util.Vector;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;

public class Code_Info extends Attribute_Info
{	
	public static final String LineNumberTable = "LineNumberTable";
	public static final String LocalVariableTable = "LocalVariableTable";
	public int length;
	public int max_stack;
	public int max_locals;
	public byte[] code;
	public int exception_table_length;
	public int[][] exception_table;
	public Attribute_Info[] attribute_info;

	public Attribute_Info copy()
	{	Code_Info copyInfo = new Code_Info(constant_pool);
		copyInfo.name_index = name_index;
		copyInfo.length = length;
		copyInfo.max_stack = max_stack;
		copyInfo.max_locals = max_locals;
		copyInfo.code = new byte[code.length];
		for(int i=0; i<code.length; i++)
			copyInfo.code[i] = code[i];
		copyInfo.exception_table_length = exception_table_length;
		copyInfo.exception_table = new int[exception_table_length][4];
		for(int i=0; i<exception_table_length; i++)
		{	copyInfo.exception_table[i][0] = exception_table[i][0];
			copyInfo.exception_table[i][1] = exception_table[i][1];
			copyInfo.exception_table[i][2] = exception_table[i][2];
			copyInfo.exception_table[i][3] = exception_table[i][3];
		}
		copyInfo.attribute_info = new Attribute_Info[attribute_info.length];
		for(int i=0; i<attribute_info.length; i++)
			copyInfo.attribute_info[i] = attribute_info[i].copy();
		return copyInfo;
	}

	public Attribute_Info getAttributeInfo(String name)
	{
		for(int i=0; i<attribute_info.length; i++)
		{
			if(attribute_info[i].getName().equals(name))
				return attribute_info[i];
		}
		return null;
	}
	
	public Code_Info() {}

	public Code_Info(Vector constant_pool)
	{	this.constant_pool = constant_pool;
	}

	public void setConstantPool(Vector constant_pool)
	{	this.constant_pool = constant_pool;
		for(int i=0; i<attribute_info.length; i++)
			attribute_info[i].setConstantPool(constant_pool);
	}

	public byte[] getCode()
	{	return code;
	}

	public int read(int name_index, int length, DataInputStream in) throws IOException
	{	this.name_index = name_index;
		this.length = length;
		max_stack = in.readUnsignedShort();
		max_locals = in.readUnsignedShort();
		code = new byte[in.readInt()];
		if(code.length > 0) in.readFully(code, 0, code.length);
		exception_table_length = in.readUnsignedShort();
		exception_table = new int[exception_table_length][4];
		for(int i=0; i<exception_table.length; i++)
		{	exception_table[i][0] = in.readUnsignedShort();
			exception_table[i][1] = in.readUnsignedShort();
			exception_table[i][2] = in.readUnsignedShort();
			exception_table[i][3] = in.readUnsignedShort();
		}
/*
		exception_table = new byte[exception_table_length][8];
		for(int i=0; i<exception_table.length; i++)
		{	in.readFully(exception_table[i],0,8);
		}
*/
		attribute_info = new Attribute_Info[in.readUnsignedShort()];
		for(int i=0; i<attribute_info.length; i++)
		{	int attribute_name_index = in.readUnsignedShort();
			int attribute_length = in.readInt();
			if(((CONSTANT_Utf8_Info)constant_pool.get(attribute_name_index)).value.equals(LineNumberTable))
				attribute_info[i] = new LineNumberTable_Info(constant_pool);
			else
			if(((CONSTANT_Utf8_Info)constant_pool.get(attribute_name_index)).value.equals(LocalVariableTable))
				attribute_info[i] = new LocalVariableTable_Info(constant_pool);
			else
				attribute_info[i] = new Attribute_Info(constant_pool);
			attribute_info[i].read(attribute_name_index, attribute_length, in);
//		{	attribute_info[i] = new Attribute_Info(constant_pool);
			// needs refine later
//			attribute_info[i].read(in);
		}
		return 6 + length;
	}

	public int write(DataOutputStream out) throws IOException
	{	out.writeShort(name_index);
		out.writeInt(length);
		out.writeShort(max_stack);
		out.writeShort(max_locals);
		out.writeInt(code.length);
		out.write(code,0,code.length);
		out.writeShort(exception_table_length);
		for(int i=0; i<exception_table.length; i++)
		{	out.writeShort(exception_table[i][0]);
			out.writeShort(exception_table[i][1]);
			out.writeShort(exception_table[i][2]);
			out.writeShort(exception_table[i][3]);
		}
/*
		for(int i=0; i<exception_table.length; i++)
		{	out.write(exception_table[i],0,8);
		}
*/
		out.writeShort(attribute_info.length);
		for(int i=0; i<attribute_info.length; i++)
		{	attribute_info[i].write(out);
		}
		return 6 + length;
	}
		
	public String toString()
	{	String str = "attribute: name="+Integer.toString(name_index)+"-";
		str += ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value+", length="+Integer.toString(length)+", ";
		str += "stack="+Integer.toString(max_stack)+", locals="+Integer.toString(max_locals)+"\n";
		str += "\tcode="+toString(code)+"\n";
		str += "\texceptions="+exception_table_length+"\n";
		for(int i=0; i<exception_table_length; i++)
		{	str += "\t\tstart_pc="+Integer.toString(exception_table[i][0])+
							", end_pc="+Integer.toString(exception_table[i][1])+
							", handler_pc="+Integer.toString(exception_table[i][2])+
							", catch_type="+Integer.toString(exception_table[i][3])+"\n";
		}
		str += "\tattributes="+attribute_info.length+"\n";
		for(int i=0; i<attribute_info.length; i++)
		{	str += "\t"+attribute_info[i].toString() + " ";
		}
		str += "\n";
		return str;
	}
	
	public static String toString(byte[] bytes)
	{	String str = "";
		for(int i=0; i<bytes.length; i++)
		{	if(bytes[i]>-1)
				str += Byte.toString(bytes[i])+" ";
			else
				str += Integer.toString(256+bytes[i])+" ";		
		}
		return str;
	}
}	
