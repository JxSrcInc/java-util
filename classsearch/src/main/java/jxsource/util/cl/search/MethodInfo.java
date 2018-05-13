package jxsource.util.cl.search;

import java.util.List;

import jxsource.util.cl.cff.Method_Info;
import jxsource.util.cl.cff.Type;

public class MethodInfo {
	private String name;
	private List<Type> argTypes;
	private Type returnType;
	private String descriptor;
	
//	public static MethodInfo create(Method_Info m) {
//		return new MethodInfoFactory().create(m);
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Type> getArgTypes() {
		return argTypes;
	}
	public void setArgTypes(List<Type> argTypes) {
		this.argTypes = argTypes;
	}
	public Type getReturnType() {
		return returnType;
	}
	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
	public String getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descriptor == null) ? 0 : descriptor.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodInfo other = (MethodInfo) obj;
		if (descriptor == null) {
			if (other.descriptor != null)
				return false;
		} else if (!descriptor.equals(other.descriptor))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MethodInfo [name=" + name + ", descriptor=" + descriptor + ", returnType=" + returnType + ", argTypes="
				+ argTypes + "]";
	}

}
