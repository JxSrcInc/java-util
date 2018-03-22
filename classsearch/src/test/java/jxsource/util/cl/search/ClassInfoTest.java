package jxsource.util.cl.search;

import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import jxsource.util.cl.test.util.ClassReflection;
import jxsource.util.cl.test.util.SetMatcher;
import jxsource.util.cl.testdata.TestData;

public class ClassInfoTest {
	static ClassInfo cInfo;
	static ClassReflection classReflection = new ClassReflection(TestData.class);
	@BeforeClass
	public static void init() throws FileNotFoundException, IOException {
		ClassRegistory cr = ClassRegistory.getInstance();
		cr.addFile("target\\test-classes\\jxsource\\util\\cl\\testdata\\TestData.class");
		cInfo = cr.getClassInfo("jxsource/util/cl/testdata/TestData");
		
	}
	@Test
	public void testClassRef() {
		for(String s: cInfo.getClassRef()) {
			Set<String> classes = classReflection.getClassRefs();
			// Add java.io.ByteArrayInputStream that is not used in any method of TestData
			classes.add("java.io.ByteArrayInputStream");
			assertThat(s,SetMatcher.matchesSet(classes));
		}
		
	}
//	@Test
//	public void testMethod() {
//		System.out.println(classReflection);
//	}
}
