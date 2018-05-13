package jxsource.util.cl.search;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

public class ClassRegistoryTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		ClassRegistry cr = ClassRegistry.getInstance();
		cr.addDir("target/classes/jxsource/util/cl/cff");
		for(Set<String> paths: cr.getUriRegistory().values()) {
			Iterator<String> i = paths.iterator();
			while(i.hasNext()) {
				assertTrue(Util.isClassFile(i.next()));
			}
		}
	}
}
