package jxsource.tool.folder.search.filter;

import java.io.File;

import org.junit.Test;

import jxsource.tool.folder.search.SysSearchEngine;
import jxsource.tool.folder.search.action.CollectionAction;
import jxsource.tool.folder.search.action.XmlReadAction;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;
import jxsource.tool.folder.search.filter.pathfilter.FullNameFilter;

public class XmlReadActionTest {

	@Test
	public void test() {
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		engin.addAction(new XmlReadAction());
		engin.setFilter(new FullNameFilter().add("pom.xml"));
		engin.search(new File(root));

	}
}
