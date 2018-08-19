package jxsource.util.folder.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsString;
import java.io.File;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Test;

import jxsource.util.folder.node.AbstractNode;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.leaffilter.ExtFilter;
import jxsource.util.folder.search.filter.leaffilter.FilterFactory;
import jxsource.util.folder.search.filter.leaffilter.FilterProperties;
import jxsource.util.folder.search.filter.leaffilter.FullNameFilter;
import jxsource.util.folder.search.filter.leaffilter.NameFilter;
import jxsource.util.folder.search.filter.pathfilter.PathFilter;
import jxsource.util.folder.search.hamcrestMatcher.IncludeStringMatcher;
import jxsource.util.folder.search.hamcrestMatcher.MatcherFactory;

public class SysSearchEngineTest {
	private Matcher<JFile> hasExt(final String exts) {
		   return new BaseMatcher<JFile>() {
		      public boolean matches(final Object item) {
		         final JFile f = (JFile) item;
		         return exts.indexOf(f.getExt()) >= 0 || f.isDir();
		      }
		   
		      public void describeTo(final Description description) {
		         description.appendText("getName should return ").appendValue(exts);
		      }
		      public void describeMismatch(final Object item, final
		Description description) {
		         description.appendText("was").appendValue(((AbstractNode) item).getName());
		     }
		   };
		}

	@Test
	public void extTest() {
		String root = "./testdata/test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(FilterFactory.create(FilterFactory.Ext, "java, class"));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<Node> files = ca.getNodes();
		assertThat(files, everyItem(hasProperty("ext", MatcherFactory.createIncludeStringMatcher("java, class"))));
		for(Node f: files) {
			assertThat((JFile)f, hasExt("java, class"));			
		}
	}

	@Test
	public void extIgnoreCaseTest() {
		String root = "./testdata/test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(FilterFactory.create(FilterFactory.Ext, "jaVa, Class", FilterProperties.setIgnoreCase(true)));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<Node> files = ca.getNodes();
		assertThat(files, everyItem(hasProperty("ext", MatcherFactory.createIncludeStringMatcher("java, class"))));
		for(Node f: files) {
			assertThat((JFile)f, hasExt("java, class"));			
		}
	}
	@Test
	public void extIgnoreCaseLikeTest() {
		String root = "./testdata/test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(FilterFactory.create(FilterFactory.Ext, "jaV",
				 FilterProperties.setIgnoreCase(true).set(FilterProperties.Like,true)));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<Node> files = ca.getNodes();
		assertThat(files, everyItem(hasProperty("ext", MatcherFactory.createIncludeStringMatcher("java"))));
		for(Node f: files) {
			assertThat((JFile)f, hasExt("java, class"));			
		}
	}

	@Test
	public void includeFilterTest() {
		String root = "./testdata/test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(new PathFilter("**/src"));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<Node> files = ca.getNodes();
		assertThat(files.size()>0, is(true));
		assertThat(files, everyItem(hasProperty("path", MatcherFactory.createIncludeStringMatcher("src"))));
	}

	@Test
	public void rejectFilterTest() {
		String root = "./testdata/test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		PathFilter filter = new PathFilter("**/src");
		filter.setReject(true);
		engin.setFilter(filter);
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<Node> files = ca.getNodes();
		assertThat(files.size()>0, is(true));
		assertThat(files, everyItem(hasProperty("path", MatcherFactory.createExcludeStringMatcher("src"))));
	}

	@Test
	public void fullNameTest() {
		String root = "./testdata/test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(FilterFactory.create(FilterFactory.FullName, "Data.java"));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<Node> files = ca.getNodes();
		assertThat(files, everyItem(hasProperty("name", is("Data.java"))));
	}

	@Test
	public void nameTest() {
		String root = "./testdata/test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(FilterFactory.create(FilterFactory.Name, "Data"));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<Node> files = ca.getNodes();
		assertThat(files, hasSize(2));
		assertThat(files, everyItem(hasProperty("name", containsString("Data"))));
	}

	@Test
	public void rejectAndNameFilterTest() {
		String root = "./testdata/test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		PathFilter filter = new PathFilter("**/src");
		filter.setReject(true);
		engin.setFilter(filter);
		filter.setNext(FilterFactory.create(FilterFactory.Name, "Data"));
		engin.search(new File(root));
		List<Node> files = ca.getNodes();
		assertThat(files, hasSize(1));
		assertThat(files, everyItem(hasProperty("path", MatcherFactory.createExcludeStringMatcher("src"))));
		assertThat(files, everyItem(hasProperty("name", containsString("Data"))));
	}


}
