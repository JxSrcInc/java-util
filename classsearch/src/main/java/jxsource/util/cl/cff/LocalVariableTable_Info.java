package jxsource.util.cl.cff;

import java.util.Vector;
import java.util.Arrays;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;

public class LocalVariableTable_Info extends Attribute_Info
{	
	public int length;
	public int local_variable_table_length;
	public LocalVariableTable[] local_variable_table;

	public Attribute_Info copy()
	{	LocalVariableTable_Info copyInfo = new LocalVariableTable_Info(constant_pool);
		copyInfo.name_index = name_index;
		copyInfo.length = length;
		copyInfo.local_variable_table_length = local_variable_table_length;
		copyInfo.local_variable_table = new LocalVariableTable[local_variable_table_length];
		for(int i=0; i<local_variable_table_length; i++)
		{	
			copyInfo.local_variable_table[i] = local_variable_table[i].copy();
		}
		return copyInfo;
	}

	public LocalVariableTable[] getLocalVariableTable()
	{
		LocalVariableTable[] lvt = new LocalVariableTable[local_variable_table_length];
		for(int i=0; i<lvt.length; i++)
			lvt[i] = local_variable_table[i];
		Arrays.sort(lvt);
		return lvt;
	}
	public LocalVariableTable_Info(Vector constant_pool)
	{	super(constant_pool);
	}

	public LocalVariableTable_Info() {}

	public void setConstantPool(Vector constant_pool)
	{	super.setConstantPool(constant_pool);
	}

	public int read(int name_index, int length, DataInputStream in) throws IOException
	{	this.name_index = name_index;
		this.length = length;
		local_variable_table_length = in.readUnsignedShort();
		local_variable_table = new LocalVariableTable[local_variable_table_length];
		for(int i=0; i<local_variable_table.length; i++)
		{	
			LocalVariableTable lvt = new LocalVariableTable();
			lvt.start_pc = in.readUnsignedShort();
			lvt.length = in.readUnsignedShort();
			lvt.name_index = in.readUnsignedShort();
			lvt.descriptor_index = in.readUnsignedShort();
			lvt.index = in.readUnsignedShort();
			lvt.constant_pool = constant_pool;
			local_variable_table[i] = lvt;
		}
		return 6 + length;
	}

	public int write(DataOutputStream out) throws IOException
	{	out.writeShort(name_index);
		out.writeInt(length);
		out.writeShort(local_variable_table_length);
		for(int i=0; i<local_variable_table.length; i++)
		{	out.writeShort(local_variable_table[i].start_pc);
			out.writeShort(local_variable_table[i].length);
			out.writeShort(local_variable_table[i].name_index);
			out.writeShort(local_variable_table[i].descriptor_index);
			out.writeShort(local_variable_table[i].index);
		}
		return 6 + length;
	}
		
	public String toString()
	{	String str = "attribute: name="+Integer.toString(name_index)+"-";
		str += ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value+", length="+Integer.toString(length)+", ";
		str += "table_length="+Integer.toString(local_variable_table.length)+"\n";
		for(int i=0; i<local_variable_table.length; i++)
		{	str += "\t\tstart_pc="+Integer.toString(local_variable_table[i].start_pc)
				+",len="+Integer.toString(local_variable_table[i].length)
				+",name="+((CONSTANT_Utf8_Info)constant_pool.get(local_variable_table[i].name_index)).value
				+",desc="+((CONSTANT_Utf8_Info)constant_pool.get(local_variable_table[i].descriptor_index)).value
				+",index="+Integer.toString(local_variable_table[i].index)+"\n";
		}
		str += "\n";
		return str;
	}
	
}	
