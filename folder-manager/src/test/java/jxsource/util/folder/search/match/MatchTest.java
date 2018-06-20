package jxsource.util.folder.search.match;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.junit.Test;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.filter.pathfilter.PathMatcher;
import jxsource.util.folder.search.match.MatchFactory;
import jxsource.util.folder.search.match.NodeMatch;

public class MatchTest {

	@Test
	public void simpleTest() {
		File testFile = new File("test");
		NodeMatch[] match = MatchFactory.createPathMatch(testFile.getPath());
		PathMatcher pm = new PathMatcher(match);
		SysFile f = new SysFile(testFile);
		assertThat(pm.match(f),is(PathMatcher.Match));
		f = new SysFile(new File("notexist"));
		assertThat(pm.match(f),is(PathMatcher.NotMatch));
		f = new SysFile(new File("notexist/test"));
		assertThat(pm.match(f),is(PathMatcher.NotMatch));
	}
	@Test
	public void multiNodeTest() {
		File testFile = new File("test/data");
		NodeMatch[] match = MatchFactory.createPathMatch(testFile.getPath());
		PathMatcher pm = new PathMatcher(match);
		SysFile f = new SysFile(new File("test/data"));
		assertThat(pm.match(f),is(PathMatcher.Match));
		f = new SysFile(new File("test/notexist"));
		assertThat(pm.match(f),is(PathMatcher.NotMatch));
	}
	@Test
	public void partialMatchTest() {
		File testFile = new File("test/1/2/3");
		NodeMatch[] match = MatchFactory.createPathMatch(testFile.getPath());
		PathMatcher pm = new PathMatcher(match);
		SysFile f = new SysFile(testFile.getParentFile().getParentFile());
		assertThat(pm.match(f),is(PathMatcher.PartialMatch));
		f = new SysFile(new File("test/1/3/3"));
		assertThat(pm.match(f),is(PathMatcher.NotMatch));
	}
	@Test
	public void overMatchTest() {
		File testFile = new File("test/1");
		NodeMatch[] match = MatchFactory.createPathMatch(testFile.getPath());
		PathMatcher pm = new PathMatcher(match);
		SysFile f = new SysFile(new File("test/1/2/3"));
		assertThat(pm.match(f),is(PathMatcher.OverMatch));
		f = new SysFile(new File("test/1/3/3"));
		assertThat(pm.match(f),is(PathMatcher.OverMatch));
	}
	
}
