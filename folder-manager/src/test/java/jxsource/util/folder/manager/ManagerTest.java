package jxsource.util.folder.manager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.filter.filefilter.BackDir;
import jxsource.util.folder.search.filter.filefilter.BackDirHolder;

public class ManagerTest {
	ManagerBuilder<SysFile> builder;
	boolean ready;
	@Before
	public void init() {
			try {
				builder = CopyManager.builder();
			} catch (Exception e) {
				e.printStackTrace();
				assert(false);
			}
	}
	@Test
	public void testDefaultTempDir() {
		SysManager manager = builder.setEngine(new SysSearchEngine()).build(SysManager.class);
		assertThat(manager.getBackDir().get(), is(new File(BackDir.rootDir, "copy")));
	}

	@Test(expected = java.lang.AssertionError.class)
	public void testNoEngingError() {
		SysManager manager = builder.build(SysManager.class);
		manager.run("testdata");
		assert(false);
	}
	@Test
	public void testEngine() {
		SysManager manager = builder.setEngine(new SysSearchEngine()).build(SysManager.class);
		assertThat(manager.getEngine() instanceof SysSearchEngine, is(true));
		assertThat(manager.getEngine().getFilter().getClass().getSimpleName(), is("CopyFilter"));
		manager.run("testdata/test-data/xyz");
		BackDirHolder.get().clear();
	}

}
