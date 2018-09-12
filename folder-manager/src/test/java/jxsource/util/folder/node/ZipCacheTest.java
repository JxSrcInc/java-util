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
		System.setProperty(ZipFile.ZipExtract, "true");
	}

	@Test
	public void test() throws ZipException, IOException {
		ZipSearchEngine engine = new ZipSearchEngine();
		Filter zipfilter = new PathFilter("test-data/src");
		engine.setFilter(zipfilter);
		CollectionAction<ZipFile> action = new CollectionAction<ZipFile>();
		engine.addAction(action);
		engine.search(new File("testdata/test-data.jar"));
		List<ZipFile> zipResults = action.getNodes();

		SysSearchEngine sysEngine = new SysSearchEngine();
		Filter sysFilter = new PathFilter("testdata/test-data/src");
		sysEngine.setFilter(sysFilter);
		CollectionAction<SysFile> sysAction = new CollectionAction<SysFile>();
		sysEngine.addAction(sysAction);
		sysEngine.search(new File("testdata/test-data"));
		List<SysFile> sysResults = sysAction.getNodes();

		assertThat(sysResults.size() == zipResults.size(), is(true));
		assertThat("no match", sysResults.size(), greaterThan(0));

		log.debug("matched files: " + sysResults.size());
		for (int i = 0; i < zipResults.size(); i++) {
			ZipFile zipFile = zipResults.get(i);
			String zipContent = getContent(zipFile);
			String sysContent = null;
			for (int k = 0; k < sysResults.size(); k++) {
				SysFile sysFile = sysResults.get(k);
				if (sysFile.getName().equals(zipFile.getName())) {
					sysContent = getContent(sysFile);
					break;
				}
			}
			assertThat(zipContent, is(sysContent));
		}
	}

	private String getContent(Node node) throws IOException {
		Reader in = new InputStreamReader(((JFile) node).getInputStream());
		char[] buf = new char[1024];
		int i;
		StringBuilder sb = new StringBuilder();
		while ((i = in.read(buf)) != -1) {
			sb.append(buf, 0, i);
		}
		return sb.toString();
	}
}
