package jxsource.util.cl.cff;

public class Type {
	private String name;
	private boolean array;
	private int dimension;
	
	public Type setName(String name) {
		this.name = name;
		return this;
	}
	public Type setArray(boolean array) {
		this.array = array;
		dimension++;
		return this;
	}
	public String getName() {
		return name;
	}
	public boolean isArray() {
		return array;
	}
	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dimension;
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
		if (dimension != other.dimension)
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
		return "Type [name=" + name + ", array=" + dimension + "]";
	}
}
