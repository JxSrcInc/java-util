package jxsource.util.folder.search.filter;

import java.io.File;

import org.junit.Test;

import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.action.XmlReadAction;
import jxsource.util.folder.search.filter.leaffilter.LeafFilterFactory;

public class XmlReadActionTest {

	@Test
	public void test() {
		String root = "./testdata/test-data";
		SysSearchEngine engin = new SysSearchEngine();
		engin.addAction(new XmlReadAction<SysFile>());
		engin.setFilter(LeafFilterFactory.create(LeafFilterFactory.FullName, "pom.xml"));
		engin.search(new File(root));

	}
}
