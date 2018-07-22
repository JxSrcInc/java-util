package jxsource.util.cl.test.util;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import jxsource.util.cl.cff.BaseTypeMapper;
import jxsource.util.cl.cff.DescriptorParser;

/**
 * Use Java Reflection to find out all classes referred in a class
 * 
 * @author JiangJxSrc
 *
 */
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
		// parameter class
		classRefs.add(cl.getName().replaceAll("\\.","/"));
		// interfaces ?
		for(Class<?> c:cl.getDeclaredClasses()) {
			classRefs.add(c.getName().replaceAll("\\.", "/"));
		}
		// all types defined in all methods
		// include prime type and object type
		for(Method m: cl.getDeclaredMethods()) {
			// method parameters
			for(Class<?> argType: m.getParameterTypes()) {
				String type = argType.getName().replaceAll("\\.", "/");
				procType(type);
			}
			// return
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
