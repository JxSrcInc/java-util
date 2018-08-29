package jxsource.util.folder.search.util;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.anyOf;
public class RegexMatcherTest {

	private String text = "Test is the first word. Then is a testing String. Second Test-info. "+
			"***************************************** "+
			"Last-Test";
	@Test
	public void testFind() {
		List<String> list = RegexMatcher.builder().build("test").find(text);
		assertThat(list.size(), is(4));
		for(String value: list) {
			assertThat(value, anyOf(containsString("#>test<#"),containsString("#>Test<#")));
		}
	}
	@Test
	public void testReplace() {
		String result = RegexMatcher.builder().build("test").replace(text, "###");
		assertThat(result, containsString("###"));
		assertThat(result, not(containsString("test")));
	}
	@Test
	public void testWordFind() {
		List<String> list = RegexMatcher.builder().setWord(true).build("test").find(text);
		assertThat(list.size(), is(3));
		for(String value: list) {
			assertThat(value, anyOf(containsString("#>test<#"),containsString("#>Test<#")));
		}
	}
	@Test
	public void testWordReplace1() {
		text = "Test is the first word. Then is a Testing String. Second Test-info. "+
				"***************************************** "+
				"Last-Test";
		String expect = "### is the first word. Then is a Testing String. Second ###-info. "+
				"***************************************** "+
				"Last-###";
		String result = RegexMatcher.builder().setWord(true).build("Test").replace(text, "###");
		assertThat(result, is(expect));
	}
	@Test
	public void testWordReplace2() {
		text = "Test is the first word. Then is a Testing String. Second Test-info. "+
				"Last-Test";
		String expect = "### is the first word. Then is a Testing String. Second ###-info. "+
				"Last-###";
		String result = RegexMatcher.builder().setWord(true).build("Test").replace(text, "###");
		assertThat(result, is(expect));
	}
	@Test
	public void testWordReplace3() {
		text = "Test is the first word. Then is a Testing String. Second Test-info. "+
				"Last-Test";
		String expect = "##### is the first word. Then is a Testing String. Second #####-info. "+
				"Last-#####";
		String result = RegexMatcher.builder().setWord(true).build("Test").replace(text, "#####");
		assertThat(result, is(expect));
	}
	@Test
	public void testWordReplace4() {
		text = "Testing is the first word. Then is a Testing String. Second Test-info. ";
		String expect = "Testing is the first word. Then is a Testing String. Second #####-info. ";
		String result = RegexMatcher.builder().setWord(true).build("Test").replace(text, "#####");
		assertThat(result, is(expect));
	}
}
