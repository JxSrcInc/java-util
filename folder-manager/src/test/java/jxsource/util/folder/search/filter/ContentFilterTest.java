package jxsource.util.folder.search.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import jxsource.util.folder.search.filter.leaffilter.ContentFilter;
import jxsource.util.folder.search.filter.leaffilter.LeafFilterFactory;

public class ContentFilterTest {

	@Test
	public void simpleTest() {
		ContentFilter f = (ContentFilter)LeafFilterFactory.create(LeafFilterFactory.Content, "abc");
		StringBuilder sb = new StringBuilder();
		sb.append("abcdefg");
			assertTrue(f.accept(sb));
	}
	
	@Test
	public void phraseTest() {
		ContentFilter f = (ContentFilter)LeafFilterFactory
				.create(LeafFilterFactory.Content, "abc f");
		StringBuilder sb = new StringBuilder();
		sb.append("a abc f defg");
			assertTrue(f.accept(sb));
	}

}
