package jxsource.util.folder.search.filter;

import static org.junit.Assert.assertThat;

import java.io.File;

import static org.hamcrest.Matchers.is;

import org.junit.Test;

import jxsource.util.folder.search.filter.filefilter.BackDirHolder;
import jxsource.util.folder.search.filter.filefilter.CopyFilter;
import jxsource.util.folder.search.filter.filefilter.FileFilterFactory;
import jxsource.util.folder.search.filter.filefilter.BackDir;

public class FileFilterTest {
	@Test
	public void testDefaultDir() throws InstantiationException, IllegalAccessException {
		CopyFilter copyFilter = FileFilterFactory.create(CopyFilter.class);
		String tempDirPath = BackDirHolder.get().get().getPath();
		assertThat(tempDirPath, is(BackDir.rootDir));
	}
//	@Test
//	public void testCustomDir() throws InstantiationException, IllegalAccessException {
//		TempDir tempDir = TempDir.builder().setSubWorkingDir("copy").build();
//		CopyFilter copyFilter = FileFilterFactory.create(CopyFilter.class);
//		String tempDirPath = copyFilter.getTempDir().get().getPath();
//		assertThat(tempDirPath, is(new File(TempDir.rootDir, "copy").getPath()));
//	}
}
