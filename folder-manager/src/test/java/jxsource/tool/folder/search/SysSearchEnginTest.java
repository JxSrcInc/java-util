package jxsource.tool.folder.search;

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

import jxsource.tool.folder.node.AbstractNode;
import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.search.action.CollectionAction;
import jxsource.tool.folder.search.filter.Filter;
import jxsource.tool.folder.search.filter.FilterFactory;
import jxsource.tool.folder.search.filter.FilterProperties;
import jxsource.tool.folder.search.filter.pathfilter.ExtFilter;
import jxsource.tool.folder.search.filter.pathfilter.FullNameFilter;
import jxsource.tool.folder.search.filter.pathfilter.NameFilter;
import jxsource.tool.folder.search.filter.pathfilter.PathFilter;
import jxsource.tool.folder.search.hamcrestMatcher.MatcherFactory;
import jxsource.tool.folder.search.hamcrestMatcher.IncludeStringMatcher;

public class SysSearchEnginTest {
	private Matcher<JFile> hasExt(final String exts) {
		   return new BaseMatcher<JFile>() {
		      public boolean matches(final Object item) {
		         final JFile f = (JFile) item;
		         return exts.indexOf(f.getExt()) >= 0 || f.isArray();
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
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(FilterFactory.create(FilterFactory.Ext, "java, class"));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<JFile> files = ca.getFiles();
		assertThat(files, everyItem(hasProperty("ext", MatcherFactory.createIncludeStringMatcher("java, class"))));
		for(JFile f: files) {
			assertThat(f, hasExt("java, class"));			
		}
	}

	@Test
	public void extIgnoreCaseTest() {
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(FilterFactory.create(FilterFactory.Ext, "jaVa, Class", FilterProperties.setIgnoreCase(true)));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<JFile> files = ca.getFiles();
		assertThat(files, everyItem(hasProperty("ext", MatcherFactory.createIncludeStringMatcher("java, class"))));
		for(JFile f: files) {
			assertThat(f, hasExt("java, class"));			
		}
	}
	@Test
	public void extIgnoreCaseLikeTest() {
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(FilterFactory.create(FilterFactory.Ext, "jaV",
				 FilterProperties.setIgnoreCase(true).set(FilterProperties.Like,true)));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<JFile> files = ca.getFiles();
		assertThat(files, everyItem(hasProperty("ext", MatcherFactory.createIncludeStringMatcher("java"))));
		for(JFile f: files) {
			assertThat(f, hasExt("java, class"));			
		}
	}

	@Test
	public void includeFilterTest() {
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(new PathFilter("**/src"));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<JFile> files = ca.getFiles();
		assertThat(files, everyItem(hasProperty("path", MatcherFactory.createIncludeStringMatcher("src"))));
	}

	@Test
	public void rejectFilterTest() {
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		Filter filter = new PathFilter("**/src");
		filter.setReject(true);
		engin.setFilter(filter);
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<JFile> files = ca.getFiles();
		assertThat(files, everyItem(hasProperty("path", MatcherFactory.createExcludeStringMatcher("src"))));
	}

	@Test
	public void fullNameTest() {
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(FilterFactory.create(FilterFactory.FullName, "Data.java"));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<JFile> files = ca.getFiles();
		assertThat(files, everyItem(hasProperty("name", is("Data.java"))));
	}

	@Test
	public void nameTest() {
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setFilter(FilterFactory.create(FilterFactory.Name, "Data"));
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<JFile> files = ca.getFiles();
		assertThat(files, hasSize(2));
		assertThat(files, everyItem(hasProperty("name", containsString("Data"))));
	}

	@Test
	public void rejectAndNameFilterTest() {
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		Filter filter = new PathFilter("**/src");
		filter.setReject(true);
		engin.setFilter(filter);
		filter.setNext(FilterFactory.create(FilterFactory.Name, "Data"));
		engin.search(new File(root));
		List<JFile> files = ca.getFiles();
		assertThat(files, hasSize(1));
		assertThat(files, everyItem(hasProperty("path", MatcherFactory.createExcludeStringMatcher("src"))));
		assertThat(files, everyItem(hasProperty("name", containsString("Data"))));
	}


}
