package jxsource.util.folder.search.filter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;
import org.junit.BeforeClass;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.filter.filefilter.BackDirHolder;
import jxsource.util.folder.search.filter.filefilter.FileFilterFactory;
import jxsource.util.folder.search.filter.filefilter.ModifyFilter;
import jxsource.util.folder.search.filter.filefilter.SaveFilter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilterFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SaveFilterTest {

	@BeforeClass
	public static void init() {
		BackDirHolder.get().clear();
	}
	@Test
	public void testNoContent() {
		SaveFilter saveFilter = FileFilterFactory.create(SaveFilter.class);
		saveFilter.setBefore(FileFilterFactory.create(ModifyFilter.class));
		assertThat(saveFilter.delegateStatus(new SysFile(new File("testdata/test-save/content.txt"))), is(Filter.REJECT));
	}

	@Test
	public void testNoModifyFilter() {
		SaveFilter saveFilter = FileFilterFactory.create(SaveFilter.class);
		saveFilter.setBefore(LeafFilterFactory.create(LeafFilterFactory.Ext, ""));
		assertThat(saveFilter.delegateStatus(new SysFile(new File("testdata/test-save/content.txt"))), is(Filter.REJECT));
	}
	
	@Test
	public void testSave() {
		ModifyFilter modifyFilter = mock(ModifyFilter.class);
		when(modifyFilter.getContent()).thenReturn("test content");
		SaveFilter saveFilter = FileFilterFactory.create(SaveFilter.class);
		saveFilter.setBefore(modifyFilter);
		assertThat(saveFilter.delegateStatus(new SysFile(new File("testdata/test-save/content.txt"))), is(Filter.ACCEPT));
	}
	

}