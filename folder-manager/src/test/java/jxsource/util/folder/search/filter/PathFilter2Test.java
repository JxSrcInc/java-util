package jxsource.util.folder.search.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.pathfilter.PathFilter;

public class PathFilter2Test {
	File root = new File("test-data");
	SysSearchEngine engine = new SysSearchEngine();
	CollectionAction ca = new CollectionAction();
	boolean ready = false;
	@Before
	public void init() {
		if(!ready) {
			ca.setUrl(root.getName());
			ca.setLeafOnly(false);
			engine.addAction(ca);
		}
		ca.reset();
	}
	@Test
	public void testRoot() {
		engine.setFilter(new PathFilter("test-data/resources"));
		engine.search(root);
		for(Node node: ca.getNodes()) {
			assertThat(node.getPath().contains("test-data/resources"), is(true));
		}
	}
	@Test
	public void testAcceptData_Java() {
		engine.setFilter(new PathFilter("**/Data.*"));
		engine.search(root);
		assertThat(ca.getNodes().size()>0, is(true));
		for(Node node: ca.getNodes()) {
			assertThat(node.getPath().contains("Data"), is(true));
		}
	}

}
