package jxsource.util.folder.search.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.filter.filefilter.BackDirHolder;
import jxsource.util.folder.search.filter.filefilter.FileFilterFactory;
import jxsource.util.folder.search.filter.filefilter.ModifyFilter;
import jxsource.util.folder.search.util.RegexMatcher;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ModifyFilterTest {
	ModifyFilter filter;
	static final String regex = "abc";
	static final String replacement = "XYZ";
	static final String prefMsg = "The test content is ";
	static ByteArrayInputStream in;
	SysFile file;
	
	@BeforeClass
	public static void init() {
		String msg = prefMsg+regex;
		in = new ByteArrayInputStream(msg.getBytes());
		BackDirHolder.get().clear();
	}
	
	@Before
	public void setup() {
		RegexMatcher matcher = RegexMatcher.builder().build(regex);
		filter = FileFilterFactory.create(ModifyFilter.class).setRegexMatcher(matcher).setReplacement(replacement);
		file = mock(SysFile.class);
	}
	
	@Test
	public void test() {
		try {
			when(file.getInputStream()).thenReturn(in);
			assertThat(filter.delegateStatus(file), is(Filter.ACCEPT));
			System.out.println(filter.getContent());
			assertThat(filter.getContent(), is(prefMsg+replacement));
			assertThat(filter.isChanged(), is(true));
		} catch (IOException e) {
			e.printStackTrace();
			assert(false);
		}
	}
}