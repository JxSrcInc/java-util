package jxsource.util.cl.cff;

import java.util.ArrayList;
import java.util.List;

public class DescriptorParser {
	public static DescriptorParser build() {
		return new DescriptorParser();
	}
		public List<Type> parse(String desc)
		{
			List<Type> s = new ArrayList<Type>();
			if(desc.charAt(0) == '(')
			{	int i = desc.indexOf(")");
				if(i > 1)
				{
					// args
					String[] objs = desc.substring(1, i).split(";");
					for(String t: objs)	{	
						while(t.length() > 0) {
							String arg = "";
							while(t.charAt(0) == '[') {
								arg += t.charAt(0);
								t = t.substring(1);
							}
							if(t.charAt(0) == 'L') {
								arg += t;
								t = "";
							} else {
								// prime type
								String baseType = ""+t.charAt(0);
								arg += baseType;
								t = t.substring(1);
							}
							s.add(getValue(arg));							
						}
					}
				}
				// return 
				String retValue = desc.substring(i+1);
				Type retType = getValue(retValue);
				s.add(retType);
			} else {
				// field
				
			}
			return s;
		}

		public Type getValue(String src)
		{	
			Type type = new Type();
			while(src.charAt(0) == '[') {
				type.setArray(true);
				src = src.substring(1);
			}
			if(src.charAt(0) == 'L') {
				if(src.charAt(src.length()-1) == ';') {
					type.setName(src.substring(1,src.length()-1));
				} else {
					type.setName(src.substring(1));					
				}
			} else {
				// prime type
				//TODO: BaseType mapping 
				type.setName(BaseTypeMapper.get(src));
			}
			return type;
		}

	}


