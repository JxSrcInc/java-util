package jxsource.util.folder.search.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.filter.filefilter.BackDirHolder;
import jxsource.util.folder.search.filter.filefilter.ModifyFilter;
import jxsource.util.folder.search.filter.leaffilter.ContentFilter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilterFactory;

public class ContentFilterTest {
	private static Logger log = LogManager.getLogger(ContentFilterTest.class);
	ByteArrayInputStream in;
	SysFile file;
	
	public void init(String msg)  throws IOException{
		in = new ByteArrayInputStream(msg.getBytes());
		BackDirHolder.get().clear();
		file = mock(SysFile.class);
		when(file.getInputStream()).thenReturn(in);
		when(file.getPath()).thenReturn("test-file");
	}

	@Test
	public void simpleTest() throws IOException {
		String msg = "The test content is **abc and xyz**";
		init(msg);
		ContentFilter filter = (ContentFilter)LeafFilterFactory.create(LeafFilterFactory.Content, "content");
		int status = filter.delegateStatus(file);
		log.debug(filter.getMatchDisplay());
		assertThat(status, is(Filter.ACCEPT));
	}
	@Test
	public void noMatchTest() throws IOException {
		String msg = "The test conten is **abc and xyz**";
		init(msg);
		ContentFilter filter = (ContentFilter)LeafFilterFactory.create(LeafFilterFactory.Content, "content");
		int status = filter.delegateStatus(file);
		log.debug(filter.getMatchDisplay());
		assertThat(status, is(Filter.REJECT));
	}
	@Test
	public void partialMatchTest() throws IOException {
		String msg = "The test content is **abc and xyz**";
		init(msg);
		ContentFilter filter = (ContentFilter)LeafFilterFactory.create(LeafFilterFactory.Content, "cont");
		int status = filter.delegateStatus(file);
		log.debug(filter.getMatchDisplay());
		assertThat(status, is(Filter.ACCEPT));
	}
	@Test
	public void wordTest() throws IOException {
		String msg = "content is the first word. but content_info is not. here is Content again and again content";
		init(msg);
		ContentFilter filter = (ContentFilter)LeafFilterFactory.create(LeafFilterFactory.Content, "content");
		filter.setWordMatch(true);
		int status = filter.delegateStatus(file);
		log.debug(filter.getMatchDisplay());
		assertThat(status, is(Filter.ACCEPT));
	}
	@Test
	public void noWordMatchTest() throws IOException {
		String msg = "conten is the first word. but content_info is not. here is Content1 again and again content10";
		init(msg);
		ContentFilter filter = (ContentFilter)LeafFilterFactory.create(LeafFilterFactory.Content, "content");
		filter.setWordMatch(true);
		int status = filter.delegateStatus(file);
		log.debug(filter.getMatchDisplay());
		assertThat(status, is(Filter.REJECT));
	}
}
