package jxsource.util.cl.search;

import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import jxsource.util.cl.test.util.SetMatcher;

public class Search {

	public static void main(String...args) throws FileNotFoundException, IOException {
		ClassRegistry cr = ClassRegistry.getInstance();
		cr.addFile("target\\test-classes\\jxsource\\util\\cl\\testdata\\TestData.class");
		ClassInfo cInfo = (ClassInfo)cr.getClassInfo("jxsource/util/cl/testdata/TestData");
		System.out.println(cInfo.getMethods());
		for(String s: cInfo.getClassRef()) {
//			if(s.indexOf("java/") == 0) continue;
//System.out.println(s+": "+cr.getClassInfo(s));
			ClassInfo ci = cr.getClassInfo(s);
			if(ci != null) {
				for(MethodInfo m: ci.getMethods()) {
					System.out.println("\t"+m);
				}
			}
		}
		
	}
}
