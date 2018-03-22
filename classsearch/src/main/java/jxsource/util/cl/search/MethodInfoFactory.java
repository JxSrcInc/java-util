package jxsource.util.cl.search;

import jxsource.util.cl.cff.CFFWrap;
import jxsource.util.cl.cff.CFFormat;
import jxsource.util.cl.cff.Method_Info;

public class MethodInfoFactory extends CFFWrap {
	
	public MethodInfoFactory(CFFormat cff) {
		super(cff);
	}
	public MethodInfo create(Method_Info _m) {
		MethodInfo m = new MethodInfo();
		m.setName(_m.getName());
		String desc = _m.getDescription();
		if(desc.charAt(0) != '(') {
			throw new RuntimeException("Invalid Description: "+desc);
		}
		int i = desc.indexOf(")");
//			if(i > 1)
//			{
//				java.util.StringTokenizer st = new java.util.StringTokenizer(desc.substring(1,i),";");
//				while(st.hasMoreTokens())
//				{	String t = st.nextToken();
//					s.add(t.substring(t.indexOf("L")+1));
//				}
//			}
//			String retValue = desc.substring(i+1);
//			if(retValue.charAt(retValue.length()-1) == ';')
//				s.add(getValue(retValue));
//		}
		
		return m;
	}
}
