package jxsource.util.cl.cff;

import java.util.Vector;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.util.StringTokenizer;
import org.apache.log4j.*;

import jxsource.util.lang.DataUtilities;

public class CodeProc
{	final int lengthOfArrayOperand = 245;//202;
//	final String InstructionPath = "jxsource/util/cl/instruction.txt";
	static CodeProc processor;
	int[] operands;
	final byte lookupswitch = (byte) 171;
	final byte tableswitch = (byte)	170;
	final byte wide = (byte) 196;
	final byte iload = (byte) 21;
	final byte fload = (byte) 23;
	final byte aload = (byte) 25;
	final byte lload = (byte) 22;
	final byte dload = (byte) 24;
	final byte istore = (byte) 54;
	final byte fstore = (byte) 56;
	final byte astore = (byte) 58;
	final byte lstore = (byte) 55;
	final byte dstore = (byte) 57;
	final byte ret = (byte) 169;
	final byte iinc = (byte) 132;
	final byte breakpoint = (byte) 202;
	final byte impdep1 = (byte) 254;
	final byte impdep2 = (byte) 255;

	private static final Logger logger = Logger.getLogger(CodeProc.class);

/* keep this code segment !!!!
	private CodeProc() throws Exception
	{	if(operands == null)
		{	operands = new int[lengthOfArrayOperand];
			for(int i=0; i<operands.length; i++)
				operands[i] = -1;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
							getClass().getClassLoader().getResourceAsStream(InstructionPath)));
			String line = null;
			while((line = reader.readLine()) != null)
			{	StringTokenizer st = new StringTokenizer(line,",");
				st.nextToken();
				int index = Integer.parseInt(st.nextToken().trim());
				if(st.hasMoreTokens())
					operands[index] = Integer.parseInt(st.nextToken().trim());
				else
					operands[index] = 0;
			}
		}
	}

	public static CodeProc getCodeProc()
	{	try
		{	if(processor == null)
				processor = new CodeProc();
		} catch(Exception e)
		{	Log.error("CodeProc",e);
		}
		return processor;
	}
*/
	/**
		* generate operands value using jl.tool.OperandTool.
		*/
	private CodeProc()
	{	operands = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,1,2,2,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,9,9,0,0,0,0,0,0,2,2,2,2,2,2,2,4,-1,2,1,2,0,0,2,2,0,0,9,3,2,2,4,4,-1,0,-1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0};
	}

	public static CodeProc getCodeProc()
	{	if(processor == null)
				processor = new CodeProc();
		return processor;
	}
	
	public int getOperands(int opcode)
	{
		return operands[opcode];
	}
	
	// Vector.elementAt(i) contains locations of opcode(opcodes[i]) in code
	// The element of returned Vector is a Vector.
	// The first Vector contains all locations of the first opcode (opcodes[0]) in code
	// The second Vector contains all locations of the seconde opcode (opcodes[1]) in code
	public Vector[] findOpcodeLocation(byte[] opcodes, byte[] code)
	{	Vector[] location = new Vector[opcodes.length];
		for(int i=0; i<location.length; i++)
			location[i] = new Vector();
		int pos = 0;
		while(pos < code.length)
		{	byte opcode = code[pos];
			// if 'opcode' is the opcode to find, add the location of it to location[]
			for(int i=0; i<opcodes.length; i++)
			{
				if(opcode == opcodes[i])
				{
					location[i].add(new Integer(pos));
				}
			}
			// pass opcode
			pos++;
			// pass oprande
			if(opcode == breakpoint || opcode == impdep1 || opcode == impdep2) // reserved opcode
			{	continue; // no oprande
			} else
			if(opcode == lookupswitch || opcode == tableswitch)
			{	DataInputStream in = new DataInputStream(new ByteArrayInputStream(code,pos,4));
				try
				{	pos += in.readInt();
				} catch(Exception e)
				{	logger.error( e);
				}
			} else
			if(opcode == wide)
			{	opcode = code[pos++];
				if( opcode == iload || opcode == fload || opcode == aload || opcode == dload || opcode == lload ||
						opcode == istore || opcode == fstore || opcode == astore || opcode == dstore || opcode == lstore ||
						opcode == ret)
				{ pos += 2;
				} else
				if(opcode == iinc)
				{	pos += 4;
				}
			} else
			{	int index = DataUtilities.toInt(opcode);
				if(index > lengthOfArrayOperand || operands[index] == -1)
				{	logger.warn("CodeProc: not defined opcode: "+index);
				}	else
				{	pos += operands[index];
				}
			}
		}
		return location;
	}

	public static void main(String[] args)
	{	 final byte opNew = (byte) 187;
		try
		{	CFFormat cff = new CFFormat(args[0]);
			Method_Info mi = cff.getMethod(args[1], args[2]);
			Attribute_Info code_info = mi.getAttribute("Code");
			System.out.println("code_info="+code_info);
			byte[] code = ((Code_Info)code_info).code;
			Vector[] v = CodeProc.getCodeProc().findOpcodeLocation(new byte[] {opNew},code);
			Vector locNew = v[0];
			System.out.println("code.length="+code.length+",#locNew="+locNew.size());
		} catch(Exception e)
		{	e.printStackTrace();
		}
	}
}
