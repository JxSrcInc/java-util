package jxsource.util.cl.cff;

import java.util.Vector;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;

public class LineNumberTable_Info extends Attribute_Info
{	
	public int length;
	public int line_number_table_length;
	public int[][] line_number_table;

	public Attribute_Info copy()
	{	LineNumberTable_Info copyInfo = new LineNumberTable_Info(constant_pool);
		copyInfo.name_index = name_index;
		copyInfo.length = length;
		copyInfo.line_number_table_length = line_number_table_length;
		copyInfo.line_number_table = new int[line_number_table_length][2];
		for(int i=0; i<line_number_table_length; i++)
		{	copyInfo.line_number_table[i][0] = line_number_table[i][0];
			copyInfo.line_number_table[i][1] = line_number_table[i][1];
		}
		return copyInfo;
	}

	public LineNumberTable_Info(Vector constant_pool)
	{	super(constant_pool);
	}

	public LineNumberTable_Info() {}

	public void setConstantPool(Vector constant_pool)
	{	super.setConstantPool(constant_pool);
	}

	public int read(int name_index, int length, DataInputStream in) throws IOException
	{	this.name_index = name_index;
//		name = ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value;
		this.length = length;
		line_number_table_length = in.readUnsignedShort();
		line_number_table = new int[line_number_table_length][2];
		for(int i=0; i<line_number_table.length; i++)
		{	line_number_table[i][0] = in.readUnsignedShort();
			line_number_table[i][1] = in.readUnsignedShort();
		}
		return 6 + length;
	}

	public int write(DataOutputStream out) throws IOException
	{	out.writeShort(name_index);
		out.writeInt(length);
		out.writeShort(line_number_table_length);
		for(int i=0; i<line_number_table.length; i++)
		{	out.writeShort(line_number_table[i][0]);
			out.writeShort(line_number_table[i][1]);
		}
		return 6 + length;
	}
		
	public String toString()
	{	String str = "attribute: name="+Integer.toString(name_index)+"-";
		str += ((CONSTANT_Utf8_Info)constant_pool.get(name_index)).value+", length="+Integer.toString(length)+", ";
		str += "table_length="+Integer.toString(line_number_table.length)+"\n";
		for(int i=0; i<line_number_table.length; i++)
		{	str += "\t\tstart_pc="+Integer.toString(line_number_table[i][0])
							+",line#="+Integer.toString(line_number_table[i][1])+"\n";
		}
		str += "\n";
		return str;
	}
	
}	
