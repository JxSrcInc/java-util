package jxsource.util.folder.search.match;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.filter.pathfilter.PathMatcher;
import jxsource.util.folder.search.match.MatchFactory;
import jxsource.util.folder.search.match.NodeMatch;
import jxsource.util.folder.search.util.TreeFactory;
import jxsource.util.folder.search.util.TreeFactoryTest;
import jxsource.util.folder.search.util.Util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AnyMatchTest {
	private static Logger log = LogManager.getLogger(AnyMatchTest.class);
//	private static Node root;
//	@BeforeClass
//	public static void init() {
//		List<JFile> files = new ArrayList<JFile>();
//		files.add(new SysFile(new File("root")));
//		files.add(new SysFile(new File("root/src")));
//		files.add(new SysFile(new File("root/src/abc.java")));
//		files.add(new SysFile(new File("root/src/xyz")));
//		files.add(new SysFile(new File("root/src/xyz/abc.java")));
//		files.add(new SysFile(new File("root/resources")));
//		root = TreeFactory.build().createTree(files);
//		log.debug(Util.getJson(root));
//	}

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
