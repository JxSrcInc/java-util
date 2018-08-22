package jxsource.util.folder.search.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.pathfilter.PathFilter;

public class PathFilterRejectTest {
	static Logger log = LogManager.getLogger(PathFilterRejectTest.class);
	File root = new File("testdata/test-data");
	SysSearchEngine engine = new SysSearchEngine();
	CollectionAction<SysFile> ca = new CollectionAction<SysFile>();
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
	public void test() {
		PathFilter filter = new PathFilter("testdata/test-data/resources"); 
		filter.setReject(true);
		engine.setFilter(filter);
		engine.search(root);
		for(Node node: ca.getNodes()) {
			assertThat(!node.getPath().contains("testdata/test-data/resources"), is(true));
		}
	}
	@Test
	public void testRejectData() {
		PathFilter filter = new PathFilter("**/Data.*"); 
		filter.setReject(true);
		engine.setFilter(filter);		
		engine.search(root);
		assertThat(ca.getNodes().size()>0, is(true));
		for(Node node: ca.getNodes()) {
			log.trace(node.getPath());
			assertThat(!node.getPath().contains("Data"), is(true));
		}
	}

}
