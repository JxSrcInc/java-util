package jxsource.util.folder.manager;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jxsource.util.folder.search.filter.filefilter.CopyFilter;
import jxsource.util.folder.search.filter.filefilter.TempDir;

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
		assertThat(manager.getTempDir().get().getPath(), is(TempDir.defaultTempDir));
	}
	@Test
	public void testFilter() {
		manager.run("testdata/test-data/xyz");
		System.err.println(manager.getEngine());
		System.err.println(manager.getEngine().getFilter());
		assertThat(manager.getEngine().getFilter().getClass().getSimpleName(), is("CopyFilter"));
	}

}
