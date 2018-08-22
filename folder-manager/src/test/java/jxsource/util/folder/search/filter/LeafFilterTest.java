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
import jxsource.util.folder.search.filter.leaffilter.LeafFilterFactory;
import jxsource.util.folder.search.filter.leaffilter.FilterProperties;

public class LeafFilterTest {
	static Logger log = LogManager.getLogger(LeafFilterTest.class);
	File root = new File("testdata");
	SysSearchEngine engine = new SysSearchEngine();
	CollectionAction<SysFile> ca = new CollectionAction<SysFile>();
	boolean ready = false;
	@Before
	public void init() {
		if(!ready) {
			ca.setUrl(root.getName());
			ca.setLeafOnly(true);
			engine.addAction(ca);
		}
		ca.reset();
	}
	
	@Test
	public void excludeTest() {
		FilterProperties fp = FilterProperties.setExclude(true);
		Filter filter = LeafFilterFactory.create(LeafFilterFactory.Ext, "java", fp); 
		engine.setFilter(filter);
		engine.search(root);
		for(Node node: ca.getNodes()) {
			assertThat(!node.getName().contains(".java"), is(true));
		}
		
	}

}
