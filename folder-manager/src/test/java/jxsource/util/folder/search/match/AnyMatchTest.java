package jxsource.util.folder.search.match;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.filter.pathfilter.PathMatcher;

public class AnyMatchTest {

	@Test
	public void anyTest() {
		NodeMatch[] match = MatchFactory.createPathMatch("**");
		PathMatcher pm = new PathMatcher(match);
		assertThat(pm.match(new SysFile(new File("root/src/xyz/abc.java"))),is(PathMatcher.OverMatch));
		assertThat(pm.match(new SysFile(new File("root/src/abc.java"))),is(PathMatcher.OverMatch));
		assertThat(pm.match(new SysFile(new File("root/src"))),is(PathMatcher.OverMatch));
	}
	@Test
	public void abcTest() {
		NodeMatch[] match = MatchFactory.createPathMatch("**/abc.java");
		PathMatcher pm = new PathMatcher(match);
		assertThat(pm.match(new SysFile(new File("root/src/xyz/abc.java"))),is(PathMatcher.Match));
		assertThat(pm.match(new SysFile(new File("root/src/abc.java"))),is(PathMatcher.Match));
		assertThat(pm.match(new SysFile(new File("root/src"))),is(PathMatcher.NotMatch));
	}

	@Test
	public void srcAbcTest() {
		NodeMatch[] match = MatchFactory.createPathMatch("**/src/abc.java");
		PathMatcher pm = new PathMatcher(match);
		assertThat(pm.match(new SysFile(new File("root/src/xyz/abc.java"))),is(PathMatcher.NotMatch));
		assertThat(pm.match(new SysFile(new File("root/src/abc.java"))),is(PathMatcher.Match));
		assertThat(pm.match(new SysFile(new File("root/abc.java"))),is(PathMatcher.NotMatch));
		// partial match
		assertThat(pm.match(new SysFile(new File("root/src"))),is(PathMatcher.PartialMatch));
		// over match
		assertThat(pm.match(new SysFile(new File("root/src/abc.java/more"))),is(PathMatcher.OverMatch));
	}
}
