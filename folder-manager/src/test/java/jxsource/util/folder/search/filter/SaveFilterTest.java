package jxsource.util.folder.search.filter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.filter.filefilter.ModifyFilter;
import jxsource.util.folder.search.filter.filefilter.SaveFilter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilterFactory;

public class SaveFilterTest {

	@Test
	public void testModifyFilter() {
		SaveFilter saveFilter = new SaveFilter();
		saveFilter.setBefore(new ModifyFilter());
		assertThat(saveFilter.delegateStatus(new SysFile(new File("testdata"))), is(Filter.ACCEPT));
	}

	@Test
	public void testExtFilter() {
		SaveFilter saveFilter = new SaveFilter();
		saveFilter.setBefore(LeafFilterFactory.create(LeafFilterFactory.Ext, ""));
		assertThat(saveFilter.delegateStatus(new SysFile(new File("testdata"))), is(Filter.REJECT));
	}

}