/*
 * Not used, because of memory limitation
 * Use SimpleClassInfo
 */
package jxsource.util.cl.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import jxsource.util.cl.cff.*;

public class ClassInfo implements IClass
{ String packageName;
	String srcFile;
	Set<String> classRef;
	Set<MethodInfo> methods = new HashSet<MethodInfo>();
//	MethodRef[] methodRef;
//	FieldRef[] fieldRef;
//	MethodInfo[] methodInfo; // method + interface_method
//	FieldInfo[] fieldInfo;
	String superClass;
//
	public ClassInfo(CFFormat cff, String file)
	{	srcFile = file;
		packageName = cff.getClassName();
		superClass = cff.getSuperClassName();
		classRef = new HashSet<String>();
//		for(Type t: cff.getReferredType()) {
//			String name = t.getName();//.replace('/', '.');
//			if(!BaseTypeMapper.isBaseType(name)) {
//				classRef.add(name);
//			}
//		}
		for(CONSTANT_Class_Info cci: cff.get_CONSTANT_Class_Info()) {
			String name = cci.getClassName();
			classRef.add(name);
		}
		List<Method_Info> methods = cff.getMethods();
		for(Method_Info m: methods) {
			MethodInfo mInfo = new MethodInfo();
			mInfo.setName(m.getName());
			mInfo.setDescriptor(m.getDescription());
			List<Type> params = DescriptorParser.build().parse(m.getDescription());
			Type ret = params.remove(params.size()-1);
			mInfo.setReturnType(ret);
			mInfo.setArgTypes(params);
			this.methods.add(mInfo);
		}
///*
//		CONSTANT_Class_Info[] cInfo = cff.get_CONSTANT_Class_Info();
//		classRef = new String[cInfo.length];
//		for(int i=0; i<cInfo.length; i++)
//			classRef[i] = cInfo[i].getClassName();
//*/
//		CONSTANT_Methodref_Info[] mInfo = cff.get_CONSTANT_Methodref_Info();
//		CONSTANT_InterfaceMethodref_Info[] iInfo = cff.get_CONSTANT_InterfaceMethodref_Info();
//		methodRef = new MethodRef[mInfo.length+iInfo.length];
//		for(int i=0; i<mInfo.length; i++)
//			methodRef[i] = new MethodRef(mInfo[i].getClassName(),
//					mInfo[i].getName(),mInfo[i].getDescriptor(),this);
//		for(int i=0; i<iInfo.length; i++)
//			methodRef[iInfo.length+i] = new MethodRef(iInfo[i].getClassName(),
//					iInfo[i].getName(),iInfo[i].getDescriptor(),this);
//		CONSTANT_Fieldref_Info[] fInfo = cff.get_CONSTANT_Fieldref_Info();
//		fieldRef = new FieldRef[fInfo.length];
//		for(int i=0; i<fInfo.length; i++)
//			fieldRef[i] = new FieldRef(fInfo[i].getClassName(),
//					fInfo[i].getName(),fInfo[i].getDescriptor(),this);
//		Method_Info[] m_Info = cff.getMethodInfos();
//		methodInfo = new MethodInfo[m_Info.length];
//		for(int i=0; i<m_Info.length; i++)
//			methodInfo[i] = new MethodInfo(m_Info[i].getName(), m_Info[i].getDescription(), this);
//		Field_Info[] f_Info = cff.getFieldInfos();
//		fieldInfo = new FieldInfo[f_Info.length];
//		for(int i=0; i<f_Info.length; i++)
//			fieldInfo[i] = new FieldInfo(f_Info[i].getName(), f_Info[i].getDescription(), this);
	}
//
//	public void addSrcFile(String name)
//	{	srcFile.add(name);
//	}
//
//	public String getClassName()
//	{	return packageName;
//	}
//
//	public String getSuperClassName()
//	{	return superClass;
//	}
//
//	public String[] getSrcFileName()
//	{	return (String[])srcFile.toArray(new String[0]);
//	}
//
	public Set<String> getClassRef()
	{	return classRef;
	}
//
//	public MethodRef[] getMethodRef()
//	{	return methodRef;
//	}
//
//	public FieldRef[] getFieldRef()
//	{	return fieldRef;
//	}
//
//	public MethodInfo[] getMethodInfo()
//	{	return methodInfo;
//	}
//
//	public FieldInfo[] getFieldInfo()
//	{	return fieldInfo;
//	}
//
//	public static void main(String[] args)
//	{	try
//		{	String s = "C:\\temp\\classsearch\\target\\classes\\jxsource\\apps\\classfile\\ClassFilePrinter.class";
//			java.io.FileInputStream in = new java.io.FileInputStream(s);
//			CFFormat cff = new CFFormat(in);
//			ClassInfo ci = new ClassInfo(cff,s);
//			for(MethodRef mf: ci.getMethodRef()) {
//				System.out.println(mf.getMethodName());
//			}
//			System.out.println(ci.getMethodRef().length);
//			System.out.println(ci.getFieldRef().length);
//			System.out.println(ci.getMethodInfo().length);
//		} catch(Exception e)
//		{ e.printStackTrace();
//		}
//	}
@Override
public Set<MethodInfo> getMethods() {
	return methods;
}
@Override
public String getPackageName() {
	// TODO Auto-generated method stub
	return null;
}
		
}