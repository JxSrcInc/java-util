package jxsource.util.folder.search.filter;

import static org.junit.Assert.assertThat;

import java.io.File;

import static org.hamcrest.Matchers.is;

import org.junit.Test;

import jxsource.util.folder.search.filter.filefilter.CopyFilter;
import jxsource.util.folder.search.filter.filefilter.FileFilterFactory;
import jxsource.util.folder.search.filter.filefilter.TempDir;

public class FileFilterTest {
	@Test
	public void testDefaultDir() throws InstantiationException, IllegalAccessException {
		TempDir tempDir = TempDir.builder().build();
		CopyFilter copyFilter = FileFilterFactory.create(CopyFilter.class, tempDir);
		String tempDirPath = copyFilter.getTempDir().get().getPath();
		assertThat(tempDirPath, is(TempDir.defaultTempDir));
	}
	@Test
	public void testCustomDir() throws InstantiationException, IllegalAccessException {
		TempDir tempDir = TempDir.builder().setSubWorkingDir("copy").build();
		CopyFilter copyFilter = FileFilterFactory.create(CopyFilter.class, tempDir);
		String tempDirPath = copyFilter.getTempDir().get().getPath();
		assertThat(tempDirPath, is(new File(TempDir.defaultTempDir, "copy").getPath()));
	}
}
