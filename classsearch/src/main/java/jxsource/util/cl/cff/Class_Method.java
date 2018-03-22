package jxsource.util.cl.cff;

	public class Class_Method
	{ public String name;
		public String descriptor;
		public String class_name;

		public Class_Method()
		{ name = "";
			descriptor = "";
			class_name = "";
		}

		public Class_Method(String name, String descriptor, String class_name)
		{ this.name = name;
			this.descriptor = descriptor;
			this.class_name = class_name;
		}

		public boolean equals(Object obj)
		{	if(obj instanceof Class_Method)
			{	if(	((Class_Method)obj).name.equals(name) &&
						((Class_Method)obj).class_name.equals(class_name) &&
						((Class_Method)obj).descriptor.equals(descriptor))
					return true;
			}
			return false;
		}
		
		public int hashCode()
		{	return (class_name+"@#$%"+name+descriptor).hashCode();
		}
	}
