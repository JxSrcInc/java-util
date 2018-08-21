package jxsource.util.folder.manager;

import static org.junit.Assert.assertThat;

import java.io.File;

import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jxsource.util.folder.search.filter.filefilter.CopyFilter;
import jxsource.util.folder.search.filter.filefilter.BackDir;

public class ManagerTest {
	static ManagerBuilder builder;
	static Manager manager;
	boolean ready;
	@BeforeClass
	public static void init() {
			try {
				builder = CopyManager.builder();
				manager = builder.build();
			} catch (Exception e) {
				e.printStackTrace();
				assert(false);
			}
	}
	@Test
	public void testDefaultTempDir() {
		assertThat(manager.getTempDir().get(), is(new File(BackDir.rootDir, "copy")));
	}
//	@Test
//	public void testFilter() {
//		manager.run("testdata/test-data/xyz");
//		System.err.println(manager.getEngine());
//		System.err.println(manager.getEngine().getFilter());
//		assertThat(manager.getEngine().getFilter().getClass().getSimpleName(), is("CopyFilter"));
//	}

}
