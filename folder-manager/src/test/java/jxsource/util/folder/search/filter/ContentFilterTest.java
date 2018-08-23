package jxsource.util.folder.search.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.filter.filefilter.BackDirHolder;
import jxsource.util.folder.search.filter.leaffilter.ContentFilter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilterFactory;

public class ContentFilterTest {
	LeafFilter filter;
	final String msg = "The test content is **abc and xyz**";
	ByteArrayInputStream in;
	SysFile file;
	
	@Before
	public void init() {
		in = new ByteArrayInputStream(msg.getBytes());
		BackDirHolder.get().clear();
		file = mock(SysFile.class);
	}

	@Test
	public void goodTest() throws IOException {
		when(file.getInputStream()).thenReturn(in);
		ContentFilter filter = (ContentFilter)LeafFilterFactory.create(LeafFilterFactory.Content, "content");
		assertThat(filter.delegateStatus(file), is(Filter.ACCEPT));
	}
}
