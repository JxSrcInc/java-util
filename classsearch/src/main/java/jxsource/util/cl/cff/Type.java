package jxsource.util.cl.cff;

public class Type {
	private String name;
	private boolean array;
	
	public Type setName(String name) {
		this.name = name;
		return this;
	}
	public Type setArray(boolean array) {
		this.array = array;
		return this;
	}
	public String getName() {
		return name;
	}
	public boolean isArray() {
		return array;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (array ? 1231 : 1237);
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
		Type other = (Type) obj;
		if (array != other.array)
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
		return "Type [name=" + name + ", array=" + array + "]";
	}
	
}
