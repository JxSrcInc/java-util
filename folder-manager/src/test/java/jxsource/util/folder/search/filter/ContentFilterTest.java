package jxsource.util.folder.search.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import jxsource.util.folder.search.filter.contentfilter.SimpleContentFilter;

public class ContentFilterTest {

	@Test
	public void simpleTest() {
		SimpleContentFilter f = new SimpleContentFilter("abc");
		ByteArrayInputStream in = new ByteArrayInputStream("abcdefg".getBytes());
		try {
			assertTrue(f.accept(in));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void phraseTest() {
		SimpleContentFilter f = new SimpleContentFilter("abc f");
		f.setWordMatch(true);
		try {
			ByteArrayInputStream in = new ByteArrayInputStream("a abc f defg".getBytes());
			assertTrue(f.accept(in));
			in = new ByteArrayInputStream("a abc defg".getBytes());
			assertFalse(f.accept(in));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
