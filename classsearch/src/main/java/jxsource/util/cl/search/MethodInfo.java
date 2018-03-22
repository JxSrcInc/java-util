package jxsource.util.cl.search;

import java.util.List;

import jxsource.util.cl.cff.Method_Info;

public class MethodInfo {
	private String name;
	private List<String> argTypes;
	private String returnType;
	
//	public static MethodInfo create(Method_Info m) {
//		return new MethodInfoFactory().create(m);
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getArgTypes() {
		return argTypes;
	}
	public void setArgTypes(List<String> argTypes) {
		this.argTypes = argTypes;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((argTypes == null) ? 0 : argTypes.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
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
		if (argTypes == null) {
			if (other.argTypes != null)
				return false;
		} else if (!argTypes.equals(other.argTypes))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (returnType == null) {
			if (other.returnType != null)
				return false;
		} else if (!returnType.equals(other.returnType))
			return false;
		return true;
	}
}
