package jxsource.util.cl.search;

import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import jxsource.util.cl.test.util.ClassReflection;
import jxsource.util.cl.test.util.SetMatcher;
import jxsource.util.cl.testdata.TestData;

/**
 * use TestData.class
 * it is compiled from jxsource.util.testdata.TestData.java
 * 
 * @author JiangJxSrc
 *
 */
public class ClassInfoTest {
	private static Logger log = LogManager.getLogger(ClassInfoTest.class);
	static ClassInfo cInfo;
	static ClassReflection classReflection = new ClassReflection(TestData.class);
	@BeforeClass
	public static void init() throws FileNotFoundException, IOException {
		ClassRegistry cr = ClassRegistry.getInstance();
		cr.addFile("target\\test-classes\\jxsource\\util\\cl\\testdata\\TestData.class");
		cInfo = cr.getClassInfo("jxsource/util/cl/testdata/TestData");
		
	}
	@Test
	public void testClassRef() {
		Set<String> classes = classReflection.getClassRefs();
		log.debug("Java Reflection Classes: "+classes);
		
		// Add java.io.ByteArrayInputStream that is not used in any method of TestData
		// More notes: the code segment below is in TestData class
		// public InputStream get(int b, File file, String...s) {
		//  	return new ByteArrayInputStream(new byte[1024*8]);
		// }
		// where ByteArrayInputStream is type/class in TestData class file 
		// but it is not a parameter or return type in any TestData method 
		// so Java Reflection does not catch it. 
		// However, it is a referred class in TestData ClassInfo.
		// 
		classes.add("java/io/ByteArrayInputStream");
		// Interface implemented by TestData
		classes.add("jxsource/util/cl/testdata/Test");
		for(String s: cInfo.getClassRef()) {
			assertThat(s,SetMatcher.matchesSet(classes));
			classes.remove(s);
		}
		log.debug("not matched classes: "+classes);
		
	}
//	@Test
//	public void testMethod() {
//		System.out.println(classReflection);
//	}
}
