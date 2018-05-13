package jxsource.util.cl.test.util;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import jxsource.util.cl.cff.BaseTypeMapper;
import jxsource.util.cl.cff.DescriptorParser;

public class ClassReflection {
	Set<String> classRefs = new HashSet<String>();
	public ClassReflection(Class<?> cl) {
		procClass(cl);
	}
	private void procType(String type) {
		if(!BaseTypeMapper.isBaseType(type)) {
			if(type.charAt(0) == '[' || type.charAt(0) == 'L') {
				type = DescriptorParser.build().getValue(type).getName();
			}
			classRefs.add(type);
		}		
	}
	private void procClass(Class<?> cl) {
		classRefs.add(cl.getName().replaceAll("\\.","/"));
		for(Class<?> c:cl.getDeclaredClasses()) {
			classRefs.add(c.getName().replaceAll("\\.", "/"));
		}
		for(Method m: cl.getDeclaredMethods()) {
			for(Class<?> argType: m.getParameterTypes()) {
				String type = argType.getName().replaceAll("\\.", "/");
				procType(type);
			}
			procType(m.getReturnType().getName().replaceAll("\\.", "/"));
		}
		if(cl.getSuperclass() != null) {
			procClass(cl.getSuperclass());
		}
		
	}
	
	public Set<String> getClassRefs() {
		return classRefs;
	}
	@Override
	public String toString() {
		return "ClassReflection [classes=" + classRefs + "]";
	}
	
	
}
