package jxsource.util.cl.search;

import java.util.HashSet;
import java.util.Set;

/**
 * referred class is a class referred in class file.
 * It has only methods and fields referred by class file.
 * @author JiangJxSrc
 *
 */
public class RefClassInfo implements IClass{

	private String packageName;
	private Set<MethodInfo> methods = new HashSet<MethodInfo>();
	public RefClassInfo(String packageName) {
		this.packageName = packageName;
	}
	@Override
	public Set<MethodInfo> getMethods() {
		return methods;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}
	
	public RefClassInfo addMethod(MethodInfo methodInfo) {
		methods.add(methodInfo);
		return this;
	}
	@Override
	public String toString() {
		return "RefClassInfo [packageName=" + packageName + ", methods=" + methods + "]";
	}
	
}
