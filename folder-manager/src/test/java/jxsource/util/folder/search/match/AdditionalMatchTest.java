package jxsource.util.folder.search.match;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.junit.Test;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.filter.pathfilter.PathMatcher;

public class AdditionalMatchTest {
	@Test
	public void testMatchAccept() {
		NodeMatch[] matches = MatchFactory.createPathMatch("**/*.class");
		SysFile f = new SysFile(new File("./target/classes/jxsource/tool/folder/search/JFile.class"));
		PathMatcher pfp = new PathMatcher(matches);
		assertThat(pfp.match(f), is(PathMatcher.Match));
	}

	@Test
	public void testPass() {
		NodeMatch[] matches = MatchFactory.createPathMatch("**/*.class");
		SysFile f = new SysFile(new File("./target/classes/jxsource/tool"));
		PathMatcher matcher = new PathMatcher(matches);
		// although all files in ./target/classes/jxsource/tool are class files
		// ./target/classes/jxsource/tool is directory, not class file.
		// so match() method returns false -- PathMatcher.match() compares a node, not tree
		assertThat(matcher.match(f), is(PathMatcher.NotMatch));
	}

	@Test
	public void testReject() {
		NodeMatch[] matches = MatchFactory.createPathMatch("**/*.java");
		SysFile f = new SysFile(new File("./target/classes/jxsource/tool/folder/search/JFile.class"));
		PathMatcher pfp = new PathMatcher(matches);
		assertThat(pfp.match(f), is(PathMatcher.NotMatch));
	}
//	@Test
//	public void noFilterTest() {
//		String root = "./target";
//		SysSearchEngin engin = new SysSearchEngin();
//		CollectionAction ca = new CollectionAction();
//		ca.setUrl(root);
//		engin.addAction(ca);
//		engin.setFilter(new PathFilter("**/*.xml,*.java"));
//		engin.search(new File(root));
//		assertThat(root, is(ca.getUrl()));
//		List<JFile> files = ca.getFiles();
//		// every item has property "ext" which may have value "java" or "class"
//		assertThat(files, everyItem(hasProperty("ext", MatcherFactory.createStringOrMatcher("java, xml"))));
//	}

//	@Test
//	public void testNoAccept() {
//		Match[] matches = MatchFactory.createPathMatch("**/*.java, *.xml");
//		SysFile f = new SysFile(new File("./src"));
//		PathMatcher pfp = new PathMatcher(matches);
//		assertThat(pfp.start(f), is(Filter.ACCEPT));
//	}


}
