package jxsource.util.cl.cff;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import jxsource.apps.assetmanagement.investment.FileSource;
import jxsource.util.bytetool.ByteArrayNotuse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

/**
 * Java Class File Object
 * To create
 * 1. Use single argument constructor
 * 2. Use load() method.
 * @author JiangJxSrc
 *
 */
public class CFFormat extends Object
{	public static final int CONSTANT_Class = 7;
	public static final int CONSTANT_Fieldref = 9;
	public static final int CONSTANT_Methodref = 10;
	public static final int CONSTANT_InterfaceMethodref = 11;
	public static final int CONSTANT_String = 8;
	public static final int CONSTANT_Integer = 3;
	public static final int CONSTANT_Float = 4;
	public static final int CONSTANT_Long = 5;
	public static final int CONSTANT_Double = 6;
	public static final int CONSTANT_NameAndType = 12;
	public static final int CONSTANT_Utf8 = 1;

	public static final int CONSTANT_MethodHandle = 15;
	public static final int CONSTANT_MethodType = 16;
	public static final int CONSTANT_InvokeDynamic = 18;

	public static final int ACC_PUBLIC = 0x0001;
	public static final int ACC_FINAL = 0x0010;
	public static final int ACC_SUPER = 0x0020;
	public static final int ACC_INTERFACE = 0x0200;
	public static final int ACC_ABSTRACT = 0x0400;

	public static final int METHOD_ACC_PUBLIC = 0x0001; //  Declared public; may be accessed from outside its package.
	public static final int METHOD_ACC_PRIVATE = 0x0002; //  Declared private; accessible only within the defining class.
	public static final int METHOD_ACC_PROTECTED = 0x0004; //  Declared protected; may be accessed within subclasses.
	public static final int METHOD_ACC_STATIC = 0x0008; //  Declared static.
	public static final int METHOD_ACC_FINAL = 0x0010; //  Declared final; may not be overridden.
	public static final int METHOD_ACC_SYNCHRONIZED = 0x0020; //  Declared synchronized; invocation is wrapped in a monitor lock.
	public static final int METHOD_ACC_NATIVE = 0x0100; //  Declared native; implemented in a language other than Java.
	public static final int METHOD_ACC_ABSTRACT = 0x0400; //  Declared abstract; no implementation is provided.
	public static final int METHOD_ACC_STRICT = 0x0800; //  Declared strictfp; floating-point mode is FP-strict

	public static final int FIELD_ACC_PUBLIC = 0x0001; //  Declared public; may be accessed from outside its package.
	public static final int FIELD_ACC_PRIVATE = 0x0002; //  Declared private; usable only within the defining class.
	public static final int FIELD_ACC_PROTECTED = 0x0004; //  Declared protected; may be accessed within subclasses.
	public static final int FIELD_ACC_STATIC = 0x0008; //  Declared static.
	public static final int FIELD_ACC_FINAL = 0x0010; //  Declared final; no further assignment after initialization.
	public static final int FIELD_ACC_VOLATILE = 0x0040; //  Declared volatile; cannot be cached.
	public static final int FIELD_ACC_TRANSIENT = 0x0080; //  Declared transient; not written or read by a persistent object manager.

	public static final String InnerClasses = "InnerClasses";
	private static final Logger logger = LogManager.getLogger(CFFormat.class);

	int magic;
	int minor_version;
	int major_version;
	int constant_pool_count;
	Vector constant_pool;
	int access_flags;
	int this_class;
	int super_class;
	int interfaces_count;
	Vector interfaces;
	int fields_count;
	Vector fields;
	int methods_count;
	Vector methods;
	int attributes_count;
	Vector attributes;
	int byteCount;
	byte[] rest;
	Vector args;

	// CFFormat -- Class File Format
	public CFFormat() {}
	
	public CFFormat(String file) throws FileNotFoundException, IOException
	{ this(new FileInputStream(file));
	}

	public CFFormat(File file) throws FileNotFoundException, IOException
	{ this(new FileInputStream(file));
	}

	public CFFormat(InputStream is) throws IOException
	{
		load(is);
	}
	
	public void load(String file) throws IOException
	{
		load(new FileInputStream(file));
	}
	
	public void load(InputStream is) throws IOException
	{	constant_pool = new Vector();
		constant_pool.add("not used");
		interfaces = new Vector();
		fields = new Vector();
		methods = new Vector();
		attributes = new Vector();
		DataInputStream in = new DataInputStream(is);
		magic = in.readInt();
		minor_version = in.readUnsignedShort();
		major_version = in.readUnsignedShort();

		// constant_pool
		constant_pool_count = in.readUnsignedShort();
		byteCount = 10;
		for(int i=1; i<constant_pool_count; i++)
		{	int tag = in.readUnsignedByte();
			switch(tag)
			{	case CONSTANT_Class:
						constant_pool.add(new CONSTANT_Class_Info(tag, in.readUnsignedShort(), i, constant_pool));
						byteCount += 3;
					break;
				case CONSTANT_Fieldref:
						constant_pool.add(new CONSTANT_Fieldref_Info(tag, in.readUnsignedShort(), in.readUnsignedShort(), i, constant_pool));
						byteCount += 5;
					break;
				case CONSTANT_Methodref:
						constant_pool.add(new CONSTANT_Methodref_Info(tag, in.readUnsignedShort(), in.readUnsignedShort(), i, constant_pool));
						byteCount += 5;
					break;
				case CONSTANT_InterfaceMethodref:
						constant_pool.add(new CONSTANT_InterfaceMethodref_Info(tag, in.readUnsignedShort(), in.readUnsignedShort(), i, constant_pool));
						byteCount += 5;
					break;
				case CONSTANT_NameAndType:
						constant_pool.add(new CONSTANT_NameAndType_Info(tag, in.readUnsignedShort(), in.readUnsignedShort(), i, constant_pool));
						byteCount += 5;
					break;
				case CONSTANT_Utf8:
						String utf8 = in.readUTF();
						constant_pool.add(new CONSTANT_Utf8_Info(tag, utf8, i, constant_pool));
						byteCount += 3 + utf8.length();
					break;
				case CONSTANT_String:
						constant_pool.add(new CONSTANT_String_Info(tag, in.readUnsignedShort(), i, constant_pool));
						byteCount += 3;
					break;
				case CONSTANT_Float:
						constant_pool.add(new CONSTANT_Float_Info(tag, in.readFloat(), i, constant_pool));
						byteCount += 5;
					break;
				case CONSTANT_Integer:
						constant_pool.add(new CONSTANT_Integer_Info(tag, in.readInt(), i, constant_pool));
						byteCount += 5;
					break;
				case CONSTANT_Long:
						constant_pool.add(new CONSTANT_Long_Info(tag, in.readLong(), i, constant_pool));
						byteCount += 9;
						i++;
						constant_pool.add(null);	// make constant_pool index consistant
					break;
				case CONSTANT_Double:
						constant_pool.add(new CONSTANT_Double_Info(tag, in.readDouble(), i, constant_pool));
						byteCount += 9;
						i++;
						constant_pool.add(null);  // make constant_pool index consistant
					break;
				case CONSTANT_MethodHandle:
					constant_pool.add(new CONSTANT_MethodHandle_Info(tag, in.readUnsignedByte(), in.readUnsignedByte(), i, constant_pool));
					byteCount += 3;
					break;
				case CONSTANT_MethodType:
					constant_pool.add(new CONSTANT_MethodType_Info(tag, in.readUnsignedShort(), i, constant_pool));
					byteCount += 3;
					break;
				case CONSTANT_InvokeDynamic:
					constant_pool.add(new CONSTANT_InvokeDynamic_Info(tag, in.readUnsignedShort(), in.readUnsignedShort(), i, constant_pool));
					byteCount += 5;
					break;
				default:
					logger.error("Unknown constant_pool_tag: "+Integer.toString(tag)+"\n");
					throw new IOException("Unknown constant_pool_tag: "+Integer.toString(tag));
			}
		}

		access_flags = in.readUnsignedShort();
		this_class = in.readUnsignedShort();
		super_class = in.readUnsignedShort();
		byteCount += 6;

		// interfaces
		interfaces_count = in.readUnsignedShort();
		byteCount += 2;
		for(int i=0; i<interfaces_count; i++)
		{	interfaces.add(new Integer(in.readUnsignedShort()));
			byteCount += 2;
		}

		// fields
		fields_count = in.readUnsignedShort();
		byteCount += 2;
		for(int i=0; i<fields_count; i++)
		{	Field_Info fi = new Field_Info(constant_pool);
			byteCount += fi.read(in);
			fields.add(fi);
		}

		// methods
		methods_count = in.readUnsignedShort();
		byteCount += 2;
		for(int i=0; i<methods_count; i++)
		{	Method_Info mi = new Method_Info(constant_pool);
			byteCount += mi.read(in);
			methods.add(mi);
		}

		// attributes
		attributes_count = in.readUnsignedShort();

		byteCount += 2;
		
		for(int i=0; i<attributes_count; i++)
		{	int attribute_name_index = in.readUnsignedShort();
			int attribute_length = in.readInt();
			if(((CONSTANT_Utf8_Info)constant_pool.get(attribute_name_index)).value.equals(InnerClasses))
			{
				InnerClass_Info innerClass_Info = new InnerClass_Info(constant_pool);
				innerClass_Info.read(attribute_name_index, attribute_length, in);
				attributes.add(innerClass_Info);
			} else
			{
				Attribute_Info attribute_Info = new Attribute_Info(constant_pool);
				attribute_Info.read(attribute_name_index, attribute_length, in);
				attributes.add(attribute_Info);				
			} // add more specific attributes if needed
		}
		
/* general code
		for(int i=0; i<attributes_count; i++)
		{	Attribute_Info ai = new Attribute_Info(constant_pool);
			byteCount += ai.read(in);
			attributes.add(ai);
		}
*/

		ByteArrayNotuse ba = new ByteArrayNotuse();
		rest = ba.get();
		byteCount += rest.length;
		in.close();
	}
	
	public InnerClass_Info[] getInnerClass()
	{
		Vector v = new Vector();
		for(int i=0; i<attributes.size(); i++)
		{
			Object obj = attributes.get(i);
			if(obj instanceof InnerClass_Info)
				v.add(obj);
		}
		return (InnerClass_Info[]) v.toArray(new InnerClass_Info[0]);
	}
	
	public int getClassIndex()
	{
		return this_class;
	}

	public Method_Info[] getMethodInfos()
	{	return (Method_Info[]) methods.toArray(new Method_Info[0]);
	}

	public Attribute_Info[] getAttributeInfos()
	{	return (Attribute_Info[]) attributes.toArray(new Attribute_Info[0]);
	}

	public Field_Info[] getFieldInfos()
	{	return (Field_Info[]) fields.toArray(new Field_Info[0]);
	}

	public void appendMethodInfo(Method_Info mi)
	{ if(mi.constant_pool == null)
			mi.constant_pool = constant_pool;
		methods.add(mi);
		methods_count++;
	}

	/**
		* Returns the index of appended CONSTANT_Class_Info in constant_pool.
		*	This method does not check the existance of class.
		* All add???? methods do check the existance.
		*/
	public int appendClassInfo(String className)
	{ CONSTANT_Utf8_Info name = new CONSTANT_Utf8_Info(CONSTANT_Utf8, className, constant_pool_count, constant_pool);
		constant_pool.add(name);
		constant_pool_count++;
		CONSTANT_Class_Info ci = new CONSTANT_Class_Info(CONSTANT_Class, constant_pool_count-1, constant_pool_count,constant_pool);
		constant_pool.add(ci);
		constant_pool_count++;
		return constant_pool_count - 1;
	}

	public int addUtf8Info(String name)
	{	int index = getUtf8Index(name);
		if(index != -1)
			return index;
		CONSTANT_Utf8_Info utf8 = new CONSTANT_Utf8_Info(CONSTANT_Utf8, name, constant_pool_count, constant_pool);
		constant_pool.add(utf8);
		constant_pool_count++;
		return constant_pool_count - 1;
	}

	public int appendMethodrefInfo(int class_index, int name_and_type_index)
	{ 
		CONSTANT_Methodref_Info _miRef = new CONSTANT_Methodref_Info(
				CFFormat.CONSTANT_Methodref,
				class_index,
				name_and_type_index,
				constant_pool_count+1,
				constant_pool);
		constant_pool.add(_miRef);
		constant_pool_count++;
		return constant_pool_count - 1;
	}

	public int addClassInfo(String className)
	{ int index = getClassIndex(className);
		if(index != -1)
			return index;
		index = addUtf8Info(className);
		CONSTANT_Class_Info ci = new CONSTANT_Class_Info(CONSTANT_Class, index, constant_pool_count,constant_pool);
		constant_pool.add(ci);
		constant_pool_count++;
		return constant_pool_count - 1;
	}

	public int addNameAndTypeInfo(String name, String descriptor)
	{ int index = getNameAndTypeIndex(name, descriptor);
		if(index != -1)
			return index;
		int name_index = addUtf8Info(name);
		int descriptor_index = addUtf8Info(descriptor);
		CONSTANT_NameAndType_Info nati = new CONSTANT_NameAndType_Info(CONSTANT_NameAndType,
			name_index, descriptor_index, constant_pool_count, constant_pool);
		constant_pool.add(nati);
		constant_pool_count++;
		return constant_pool_count - 1;
	}

	/**
		*	Add CONSTANT_Methodref_Info and other needed entries, like CONSTANT_Class_Info, to constant_pool
		*/
	public int addMethodrefInfo(String className, String name, String descriptor)
	{ int index = getMethodrefIndex(className, name, descriptor);
		if(index != -1)
			return index;
		CONSTANT_Methodref_Info mi = new CONSTANT_Methodref_Info(CONSTANT_Methodref,
			addClassInfo(className), addNameAndTypeInfo(name, descriptor), constant_pool_count, constant_pool);
		constant_pool.add(mi);
		constant_pool_count++;
		return constant_pool_count - 1;
	}

	public Object getConstantPoolElement(int index)
	{	return constant_pool.get(index);
	}

	public int getSuperClassIndex()
	{	return super_class;
	}

	public void setSuperClassIndex(int i)
	{	super_class = i;
	}

	public Vector getConstantPool()
	{	return constant_pool;
	}
	
	/*
	 * Include Java Prime
	 */
	public Set<Type> getReferredType()
	{ 	Set<Type> s = new HashSet<Type>();
		for(int i=0; i<constant_pool_count; i++)
		{	Object element = constant_pool.elementAt(i);
			if(element instanceof CONSTANT_Class_Info)
			{	s.add(new Type().setName(((CONSTANT_Class_Info)element).getClassName()));
			} else
			if(element instanceof CONSTANT_Fieldref_Info)
			{	String desc = ((CONSTANT_Fieldref_Info)element).getDescriptor();
				s.add(DescriptorParser.build().getValue(desc));
			} else
			if(element instanceof CONSTANT_Methodref_Info)
			{	s.addAll(DescriptorParser.build().parse(((CONSTANT_Methodref_Info)element).getDescriptor()));
			} else
			if(element instanceof CONSTANT_InterfaceMethodref_Info)
			{	s.addAll(DescriptorParser.build().parse(((CONSTANT_InterfaceMethodref_Info)element).getDescriptor()));
			}
		}
		Method_Info[] mi = getMethodInfos();
		for(int i=0; i<mi.length; i++)
		{	List<Type> cl = DescriptorParser.build().parse(mi[i].getDescription());
			for(int k=0; k<cl.size(); k++)
				s.add(cl.get(k));
		}
		Field_Info[] fi = getFieldInfos();
		for(int i=0; i<fi.length; i++)
			s.add(DescriptorParser.build().getValue(fi[i].getDescription()));
		return s;
	}

	public CONSTANT_Class_Info[] get_CONSTANT_Class_Info()
	{ Vector v = new Vector();
		for(int i=0; i<constant_pool_count; i++)
			if(constant_pool.elementAt(i) instanceof CONSTANT_Class_Info)
				v.add(constant_pool.elementAt(i));
		return (CONSTANT_Class_Info[]) v.toArray(new CONSTANT_Class_Info[0]);
	}

	public CONSTANT_Fieldref_Info[] get_CONSTANT_Fieldref_Info()
	{ Vector v = new Vector();
		for(int i=0; i<constant_pool_count; i++)
			if(constant_pool.elementAt(i) instanceof CONSTANT_Fieldref_Info)
				v.add(constant_pool.elementAt(i));
		return (CONSTANT_Fieldref_Info[]) v.toArray(new CONSTANT_Fieldref_Info[0]);
	}

	public CONSTANT_Methodref_Info[] get_CONSTANT_Methodref_Info()
	{ Vector v = new Vector();
		for(int i=0; i<constant_pool_count; i++)
			if(constant_pool.elementAt(i) instanceof CONSTANT_Methodref_Info)
				v.add(constant_pool.elementAt(i));
		return (CONSTANT_Methodref_Info[]) v.toArray(new CONSTANT_Methodref_Info[0]);
	}

	public CONSTANT_InterfaceMethodref_Info[] get_CONSTANT_InterfaceMethodref_Info()
	{ Vector v = new Vector();
		for(int i=0; i<constant_pool_count; i++)
			if(constant_pool.elementAt(i) instanceof CONSTANT_InterfaceMethodref_Info)
				v.add(constant_pool.elementAt(i));
		return (CONSTANT_InterfaceMethodref_Info[]) v.toArray(new CONSTANT_InterfaceMethodref_Info[0]);
	}

	public List<Method_Info> getMethods()
	{	
		return Arrays.asList(getMethodInfos());
	}

	public Method_Info getMethod(String name, String descriptor) throws NullPointerException
	{	for(int i=0; i<methods.size(); i++)
		{	Method_Info mi = (Method_Info) methods.elementAt(i);
			if(mi.getName().equals(name) &&
					mi.getDescription().equals(descriptor))
				return mi;
		}
		return null;
	}

	public Attribute_Info getAttribute(String name) throws NullPointerException
	{	for(int i=0; i<attributes.size(); i++)
		{	Attribute_Info ai = (Attribute_Info) attributes.elementAt(i);
			if(ai.getName().equals(name) )
				return ai;
		}
		return null;
	}

	public byte[] getBytes() throws IOException
	{	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(byteCount);
		DataOutputStream out = new DataOutputStream(byteArrayOutputStream);
		out.writeInt(magic);
		out.writeShort(minor_version);
		out.writeShort(major_version);
		out.writeShort(constant_pool.size());
		for(int i=1; i<constant_pool.size(); i++)
		{	Object obj = constant_pool.get(i);
			if(obj == null) continue;
			if(obj instanceof CONSTANT_Class_Info)
			{	CONSTANT_Class_Info cci = (CONSTANT_Class_Info)obj;
				out.writeByte(cci.tag);
				out.writeShort(cci.name_index);
			} else
			if(obj instanceof CONSTANT_Fieldref_Info)
			{	CONSTANT_Fieldref_Info cci = (CONSTANT_Fieldref_Info)obj;
				out.writeByte(cci.tag);
				out.writeShort(cci.class_index);
				out.writeShort(cci.name_and_type_index);
			} else
			if(obj instanceof CONSTANT_Methodref_Info)
			{	CONSTANT_Methodref_Info cci = (CONSTANT_Methodref_Info)obj;
				out.writeByte(cci.tag);
				out.writeShort(cci.class_index);
				out.writeShort(cci.name_and_type_index);
			} else
			if(obj instanceof CONSTANT_InterfaceMethodref_Info)
			{	CONSTANT_InterfaceMethodref_Info cci = (CONSTANT_InterfaceMethodref_Info)obj;
				out.writeByte(cci.tag);
				out.writeShort(cci.class_index);
				out.writeShort(cci.name_and_type_index);
			} else
			if(obj instanceof CONSTANT_NameAndType_Info)
			{	CONSTANT_NameAndType_Info cci = (CONSTANT_NameAndType_Info)obj;
				out.writeByte(cci.tag);
				out.writeShort(cci.name_index);
				out.writeShort(cci.descriptor_index);
			} else
			if(obj instanceof CONSTANT_Utf8_Info)
			{	CONSTANT_Utf8_Info cci = (CONSTANT_Utf8_Info)obj;
				out.writeByte(cci.tag);
				out.writeUTF(cci.value);
			} else
			if(obj instanceof CONSTANT_String_Info)
			{	CONSTANT_String_Info cci = (CONSTANT_String_Info)obj;
				out.writeByte(cci.tag);
				out.writeShort(cci.string_index);
			} else
			if(obj instanceof CONSTANT_Integer_Info)
			{	CONSTANT_Integer_Info cci = (CONSTANT_Integer_Info)obj;
				out.writeByte(cci.tag);
				out.writeInt(cci.value);
			} else
			if(obj instanceof CONSTANT_Float_Info)
			{	CONSTANT_Float_Info cci = (CONSTANT_Float_Info)obj;
				out.writeByte(cci.tag);
				out.writeFloat(cci.value);
			} else
			if(obj instanceof CONSTANT_Long_Info)
			{	CONSTANT_Long_Info cci = (CONSTANT_Long_Info)obj;
				out.writeByte(cci.tag);
				out.writeLong(cci.value);
			} else
			if(obj instanceof CONSTANT_Double_Info)
			{	CONSTANT_Double_Info cci = (CONSTANT_Double_Info)obj;
				out.writeByte(cci.tag);
				out.writeDouble(cci.value);
			}
		}
		out.writeShort(access_flags);
		out.writeShort(this_class);
		out.writeShort(super_class);

		// interface
		out.writeShort(interfaces.size());
		for(int i=0; i<interfaces.size(); i++)
		{ out.writeShort(((Integer)interfaces.get(i)).intValue());
		}

		// fields
		out.writeShort(fields.size());
		for(int i=0; i<fields.size(); i++)
		{ ((Field_Info)fields.elementAt(i)).write(out);
		}

		// methods
		out.writeShort(methods.size());
		for(int i=0; i<methods.size(); i++)
		{ ((Method_Info)methods.elementAt(i)).write(out);
		}

		// attributes
		out.writeShort(attributes.size());
		for(int i=0; i<attributes.size(); i++)
		{ ((Attribute_Info)attributes.elementAt(i)).write(out);
		}

		out.write(rest, 0, rest.length);
		out.flush();
		return byteArrayOutputStream.toByteArray();
	}

	public boolean containsString(String[] search, boolean print)
	{	if(search.length == 0)
			return false;
		Vector v = new Vector();
		for(int i=0; i<constant_pool.size(); i++)
		{	Object obj = constant_pool.get(i);
			if(obj instanceof CONSTANT_Utf8_Info)
			{	CONSTANT_Utf8_Info cci = (CONSTANT_Utf8_Info)obj;
				for(int k=0; k<search.length; k++)
				{	if(cci.value.indexOf(search[k]) > -1)
						v.add(cci.value);
				}
			}
		}
		if(v.size() == 0)
		{	return false;
		} else
		{	String[] msg = (String[])v.toArray(new String[0]);
			logger.debug("class="+getClassName(this_class));
			for(int i=0; i<msg.length; i++)
				logger.debug("str="+msg[i]);
			return true;
		}
	}

	public String toStringOfConstantPool()
	{
		String str = "constant_pool_count="+constant_pool_count+"\n";
		for(int i=1; i<constant_pool.size(); i++)
		{	Object obj = constant_pool.get(i);
			if(obj == null) continue;
			str += "   "+toStringOfConstantPoolEntry(obj)+"\n";
		}
		return str;
	}

	public String toStringOfConstantPoolEntry(Object obj)
	{		String str = "";
			if(obj instanceof CONSTANT_Class_Info)
			{	CONSTANT_Class_Info cci = (CONSTANT_Class_Info)obj;
				str += Integer.toString(cci.index)+"\t";
				str += "tag="+Integer.toString(cci.tag)+", name_index="+Integer.toString(cci.name_index)+", ";
				str += cci.getClassName();
			} else
			if(obj instanceof CONSTANT_Fieldref_Info)
			{	CONSTANT_Fieldref_Info cci = (CONSTANT_Fieldref_Info)obj;
				str += Integer.toString(cci.index)+"\t";
				str += "tag="+Integer.toString(cci.tag)+", "+
								"class_index="+Integer.toString(cci.class_index)+", "+
								"name_and_type_index="+Integer.toString(cci.name_and_type_index)+", ";
				str +=	cci.getName()+", "+cci.getDescriptor()+", "+cci.getClassName();
			} else
			if(obj instanceof CONSTANT_Methodref_Info)
			{	CONSTANT_Methodref_Info cci = (CONSTANT_Methodref_Info)obj;
				str += Integer.toString(cci.index)+"\t";
				str += "tag="+Integer.toString(cci.tag)+", "+
								"class_index="+Integer.toString(cci.class_index)+", "+
								"name_and_type_index="+Integer.toString(cci.name_and_type_index)+", ";
				str += cci.getName()+" - "+cci.getDescriptor()+", "+cci.getClassName();
			} else
			if(obj instanceof CONSTANT_InterfaceMethodref_Info)
			{	CONSTANT_InterfaceMethodref_Info cci = (CONSTANT_InterfaceMethodref_Info)obj;
				str += Integer.toString(cci.index)+"\t";
				str += "tag="+Integer.toString(cci.tag)+", "+
								"class_index="+Integer.toString(cci.class_index)+", "+
								"name_and_type_index="+Integer.toString(cci.name_and_type_index)+", ";
				str +=	cci.getName()+", "+cci.getDescriptor()+", "+cci.getClassName();
			} else
			if(obj instanceof CONSTANT_NameAndType_Info)
			{	CONSTANT_NameAndType_Info cci = (CONSTANT_NameAndType_Info)obj;
				str += Integer.toString(cci.index)+"\t";
				str += "tag="+Integer.toString(cci.tag)+", "+
								"name_index="+Integer.toString(cci.name_index)+", "+
								"descriptor_index="+Integer.toString(cci.descriptor_index);
			} else
			if(obj instanceof CONSTANT_Utf8_Info)
			{	CONSTANT_Utf8_Info cci = (CONSTANT_Utf8_Info)obj;
				str += Integer.toString(cci.index)+"\t";
				str += "tag="+Integer.toString(cci.tag)+", "+
								"length="+Integer.toString(cci.value.length())+", "+
								"buyts="+cci.value;
			} else
			if(obj instanceof CONSTANT_String_Info)
			{	CONSTANT_String_Info cci = (CONSTANT_String_Info)obj;
				str += Integer.toString(cci.index)+"\t";
				str += "tag="+Integer.toString(cci.tag)+", "+
								"string_index="+Integer.toString(cci.string_index);
			} else
			if(obj instanceof CONSTANT_Integer_Info)
			{	CONSTANT_Integer_Info cci = (CONSTANT_Integer_Info)obj;
				str += Integer.toString(cci.index)+"\t";
				str += "tag="+Integer.toString(cci.tag)+", "+
								"value="+Integer.toString(cci.value);
			} else
			if(obj instanceof CONSTANT_Float_Info)
			{	CONSTANT_Float_Info cci = (CONSTANT_Float_Info)obj;
				str += Integer.toString(cci.index)+"\t";
				str += "tag="+Integer.toString(cci.tag)+", "+
								"value="+Float.toString(cci.value);
			} else
			if(obj instanceof CONSTANT_Long_Info)
			{	CONSTANT_Long_Info cci = (CONSTANT_Long_Info)obj;
				str += Integer.toString(cci.index)+"\t";
				str += "tag="+Integer.toString(cci.tag)+", "+
								"value="+Long.toString(cci.value);
			} else
			if(obj instanceof CONSTANT_Double_Info)
			{	CONSTANT_Double_Info cci = (CONSTANT_Double_Info)obj;
				str += Integer.toString(cci.index)+"\t";
				str += "tag="+Integer.toString(cci.tag)+", "+
								"value="+Double.toString(cci.value);
			}
			return str;
	}

	public String getLoadingClassInfo()
	{	return "Class="+getClassName(this_class)+", index="+this_class;
	}

	public String toString()
	{	String str = "magic="+Integer.toHexString(magic).toUpperCase()+"\n";
		str += "minor_version="+minor_version+"\n";
		str += "major_version="+major_version+"\n";
		str += toStringOfConstantPool();
		str += "access_flags="+Integer.toHexString(access_flags)+"\n";
		str += "this_class="+this_class+","+getClassName(this_class)+"\n";
		str += "super_class="+super_class+","+getClassName(super_class)+"\n";

		str += "interfaces_count="+Integer.toString(interfaces.size())+"\n";
		for(int i=0; i<interfaces.size(); i++)
		{	Integer index = (Integer)interfaces.elementAt(i);
			str += "   "+index.toString()+",\t"+getClassName(index.intValue())+"\n";
		}

		// fields
		str += "fields_count="+Integer.toString(fields.size())+"\n";
		for(int i=0; i<fields.size(); i++)
		{ str += "   "+((Field_Info)fields.elementAt(i)).toString();
		}

		// methods
		str += "methods_count="+Integer.toString(methods.size())+"\n";
		for(int i=0; i<methods.size(); i++)
		{ str += "   "+((Method_Info)methods.elementAt(i)).toString();
		}

		// attributes
		str += "attributes_count="+Integer.toString(attributes.size())+"\n";
		for(int i=0; i<attributes.size(); i++)
		{ str += "   "+((Attribute_Info)attributes.elementAt(i)).toString();
		}

		str += "file_length="+byteCount+"\n";
		return str;
	}

	public String getClassName(int index)
	{	
		Object obj = constant_pool.get(index);
		if(obj instanceof String)
			return "null";
		return ((CONSTANT_Utf8_Info)constant_pool.get(((CONSTANT_Class_Info)constant_pool.get(index)).name_index)).value;
	}

	/**
		* return the name of this class
		*/
	public String getClassName()
	{ return getClassName(this_class);
	}
	
	public int getConstantPoolIndexOfThisClass()
	{
		return getUtf8Index(getClassName());
	}

	public String getSuperClassName()
	{	return getClassName(getSuperClassIndex());
	}

	public boolean isPublic()
	{	return ((access_flags & 0x000F) == 1);
	}

	public void setPublic()
	{	access_flags = (access_flags | 0x0001);
	}

	public int getAccessFlags()
	{	return access_flags;
	}

	public void setAccessFlags(int flags)
	{	access_flags = flags;
	}

	public String[] getInterfaces()
	{	String[] interfaceName = new String[interfaces.size()];
		for(int i=0; i<interfaces.size(); i++)
		{	Integer index = (Integer)interfaces.elementAt(i);
			interfaceName[i] = getClassName(index.intValue());
		}
		return interfaceName;
	}

	public int getUtf8Index(String value)
	{ for(int i=0; i<constant_pool.size(); i++)
		{	Object obj = constant_pool.elementAt(i);
			if((obj instanceof CONSTANT_Utf8_Info) && ((CONSTANT_Utf8_Info)obj).value.equals(value))
				return i;
		}
		return -1;
	}
/*
	public boolean setUtf8Index(int index, String value)
	{ 
		Object obj = constant_pool.elementAt(index);
		if((obj instanceof CONSTANT_Utf8_Info))
		{
			((CONSTANT_Utf8_Info)obj).value = value;
			return true;
		}
		return false;
	}
*/
	public int getClassIndex(String className)
	{ for(int i=0; i<constant_pool.size(); i++)
		{	Object obj = constant_pool.elementAt(i);
			if((obj instanceof CONSTANT_Class_Info) &&
					((CONSTANT_Utf8_Info)constant_pool.get(((CONSTANT_Class_Info)obj).name_index)).value.equals(className))
				return i;
		}
		return -1;
	}

	public int getNameAndTypeIndex(String name, String descriptor)
	{ for(int i=0; i<constant_pool.size(); i++)
		{	Object obj = constant_pool.elementAt(i);
			if((obj instanceof CONSTANT_NameAndType_Info) &&
					((CONSTANT_Utf8_Info)constant_pool.get(((CONSTANT_NameAndType_Info)obj).name_index)).value.equals(name) &&
					((CONSTANT_Utf8_Info)constant_pool.get(((CONSTANT_NameAndType_Info)obj).descriptor_index)).value.equals(descriptor))
				return i;
		}
		return -1;
	}

	public int getMethodrefIndex(String className, String name, String descriptor)
	{ for(int i=0; i<constant_pool.size(); i++)
		{	Object obj = constant_pool.elementAt(i);
			if(obj instanceof CONSTANT_Methodref_Info)
			{	CONSTANT_Class_Info classInfo = (CONSTANT_Class_Info)constant_pool.get(((CONSTANT_Methodref_Info)obj).class_index);
				int name_and_type_index = ((CONSTANT_Methodref_Info)obj).name_and_type_index;
				CONSTANT_NameAndType_Info nameAndType = (CONSTANT_NameAndType_Info)constant_pool.get(name_and_type_index);
				if(((CONSTANT_Utf8_Info)constant_pool.get(classInfo.name_index)).value.equals(className) &&
					((CONSTANT_Utf8_Info)constant_pool.get(nameAndType.name_index)).value.equals(name) &&
					((CONSTANT_Utf8_Info)constant_pool.get(nameAndType.descriptor_index)).value.equals(descriptor))
				return i;
			}
		}
		return -1;
	}
	
	public CONSTANT_Methodref_Info getMethodref(String className, String name, String descriptor)
	{
		return (CONSTANT_Methodref_Info)constant_pool.get(getMethodrefIndex(className, name, descriptor));
	}

	public int getFieldrefIndex(String className, String name, String descriptor)
	{ for(int i=0; i<constant_pool.size(); i++)
		{	Object obj = constant_pool.elementAt(i);
			if(obj instanceof CONSTANT_Fieldref_Info)
			{	CONSTANT_Class_Info classInfo = (CONSTANT_Class_Info)constant_pool.get(((CONSTANT_Fieldref_Info)obj).class_index);
				int name_and_type_index = ((CONSTANT_Fieldref_Info)obj).name_and_type_index;
				CONSTANT_NameAndType_Info nameAndType = (CONSTANT_NameAndType_Info)constant_pool.get(name_and_type_index);
				if(((CONSTANT_Utf8_Info)constant_pool.get(classInfo.name_index)).value.equals(className) &&
					((CONSTANT_Utf8_Info)constant_pool.get(nameAndType.name_index)).value.equals(name) &&
					((CONSTANT_Utf8_Info)constant_pool.get(nameAndType.descriptor_index)).value.equals(descriptor))
				return i;
			}
		}
		return -1;
	}

	public void setMajorVersion(int major_version)
	{
		this.major_version = major_version;
	}

	public void print(String filename) throws IOException
	{	
		PrintStream printer = new PrintStream(new FileOutputStream(filename));
		printer.println(toString());
		printer.flush();
		printer.close();
	}
}

