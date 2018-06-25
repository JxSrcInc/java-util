package jxsource.util.folder.node;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThan;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.ZipSearchEngine;
import jxsource.util.folder.search.action.CollectionAction;
import jxsource.util.folder.search.filter.Filter;
import jxsource.util.folder.search.filter.pathfilter.PathFilter;

public class ZipCacheTest {
	private Logger log = LogManager.getLogger(ZipCacheTest.class);

	@BeforeClass
	public static void init() {
		System.setProperty(ZipFile.CachePropertyName, ZipFile.Memory);		
	}
	@Test
	public void test() throws ZipException, IOException {
		ZipSearchEngine engine = new ZipSearchEngine();
		Filter filter = new PathFilter("test-data/src");
		engine.setFilter(filter);
		CollectionAction action = new CollectionAction();
		engine.addAction(action);
		engine.search(new SysFile(new File("test-data.jar")));
		List<Node> zipResults = action.getNodes();

		SysSearchEngine sysEngine = new SysSearchEngine();
		sysEngine.setFilter(filter);
		CollectionAction sysAction = new CollectionAction();
		sysEngine.addAction(sysAction);
		sysEngine.search(new File("test-data"));
		List<Node> sysResults = action.getNodes();
		
		assertThat(sysResults.size()==zipResults.size(), is(true));
		assertThat("no match", sysResults.size(), greaterThan(0));

		log.debug("matched files: "+sysResults.size());
		for(int i=0; i<sysResults.size(); i++) {
			String zipContent = getContent(zipResults.get(i));
			String sysContent = getContent(sysResults.get(i));
			assertThat(zipContent, is(sysContent));
		}
	}
	
	private String getContent(Node node) throws IOException {
			Reader in = new InputStreamReader(((JFile)node).getInputStream());
			char[] buf = new char[1024];
			int i;
			StringBuilder sb = new StringBuilder();
			while((i=in.read(buf)) != -1) {
				sb.append(buf, 0, i);
			}
			return sb.toString();
	}
}
