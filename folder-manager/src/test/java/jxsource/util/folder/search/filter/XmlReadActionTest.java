package jxsource.util.folder.search.filter;

import java.io.File;

import org.junit.Test;

import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.action.XmlReadAction;
import jxsource.util.folder.search.filter.FilterFactory;
import jxsource.util.folder.search.filter.pathfilter.ExtFilter;
import jxsource.util.folder.search.filter.pathfilter.FullNameFilter;

public class XmlReadActionTest {

	@Test
	public void test() {
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		engin.addAction(new XmlReadAction());
		engin.setFilter(FilterFactory.create(FilterFactory.FullName, "pom.xml"));
		engin.search(new File(root));

	}
}
