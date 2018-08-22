package jxsource.util.folder.search.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.pathfilter.PathFilter;

public class PathFilterTest {
	
	File root = new File("testdata/test-data");
	SysSearchEngine engine = new SysSearchEngine();
	CollectionAction<SysFile> ca = new CollectionAction<SysFile>();
	boolean ready = false;
	@Before
	public void init() {
		if(!ready) {
			ca.setUrl(root.getName());
			engine.addAction(ca);
		}
		ca.reset();
	}
	@Test
	public void testRoot() {
		engine.setFilter(new PathFilter("testdata/test-data"));
		engine.search(root);
		assertThat(ca.getNodes().size(), is(5));
	}
	@Test
	public void testSrc() {
		engine.setFilter(new PathFilter("testdata/test-data/src"));
		engine.search(root);
		assertThat(ca.getNodes().size(), is(2));
	}
	@Test
	public void testXyz() {
		engine.setFilter(new PathFilter("testdata/test-data/xyz"));
		engine.search(root);
		assertThat(ca.getNodes().size(), is(1));
	}

	@Test
	public void endFoundTest() {
		engine.setFilter(new PathFilter("testdata/test-data/src/main/java/*.java"));
		engine.search(root);
		assertThat(ca.getNodes().size(), is(1));
	}
	@Test
	public void endNotFoundTest() {
		engine.setFilter(new PathFilter("testdata/test-data/src/main/*.java"));
		engine.search(root);
		assertThat(ca.getNodes().size(), is(0));
	}
//	@Test
//	public void noFilterTest() {
//		String root = "./target";
//		SysSearchEngine engine = new SysSearchEngine();
//		CollectionAction ca = new CollectionAction();
//		ca.setUrl(root);
//		engine.addAction(ca);
//		engine.setFilter(new PathFilter("**/*.xml,*.java"));
//		engine.search(new File(root));
//		assertThat(root, is(ca.getUrl()));
//		List<Node> files = ca.getNodes();
//		for(Node node: files)  {
///		}
//			System.out.println(node);
//		// every item has property "ext" which may have value "java" or "class"
////		assertThat(files, everyItem(hasProperty("ext", MatcherFactory.createStringOrMatcher("java, xml"))));
//	}

//	@Test
//	public void testNoAccept() {
//		Match[] matches = MatchFactory.createPathMatch("**/*.java, *.xml");
//		SysFile f = new SysFile(new File("./src"));
//		PathMatcher pfp = new PathMatcher(matches);
//		assertThat(pfp.start(f), is(Filter.ACCEPT));
//	}


}
