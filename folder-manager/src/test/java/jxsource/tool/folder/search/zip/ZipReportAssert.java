package jxsource.tool.folder.search.zip;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import jxsource.tool.folder.file.AbstractJFile;
import jxsource.tool.folder.file.JFile;
import jxsource.tool.folder.search.filter.Filter;
import jxsource.tool.folder.search.util.Util;
import jxsource.tool.folder.search.zip.ZipReportAction;

import static org.junit.Assert.*;

public class ZipReportAssert extends ZipReportAction {
	private static Logger log = LogManager.getLogger(ZipReportAssert.class);
	private String url;
	private String[] paths;
	private String[] names;
	private String[] exts;
	private long start, end;
	private List<JFile> found = new ArrayList<JFile>();
	
	private Matcher<String> contains(final String[] matchs) {
		return new BaseMatcher<String>() {
			public boolean matches(final Object obj) {
				String src = (String) obj;
				for (String match : matchs) {
						if(src.contains(match)) {
							log.debug("Match: "+match+" with "+src);
//							found.add(src);
							return true;
						}
				}
				return false;
			}

			public void describeTo(final Description description) {
				description.appendText("contains should return ").appendValue(matchs);
			}
		};
	}

	public List<JFile> getFound() {
		return found;
	}
	public List<JFile> getFoundFiles() {
		List<JFile> foundFiles = new ArrayList<JFile>();
		for(JFile f: getFound()) {
			if(!f.isDirectory()) {
				foundFiles.add(f);
			}
		}
		return foundFiles;
	}
	public ZipReportAssert setPath(String multiPaths) {
		paths = Util.toArray(multiPaths);
		return this;
	}

	public ZipReportAssert setName(String multiNames) {
		names = Util.toArray(multiNames);
		return this;
	}

	public ZipReportAssert setExt(String multiExts) {
		exts = Util.toArray(multiExts);
		return this;
	}
	public ZipReportAssert setStart(long start) {
		this.start = start;
		return this;
	}
	public ZipReportAssert setEnd(long end) {
		this.end = end;
		return this;
	}

	@Override
	public void report(String url, List<JFile> extractFiles) {
//		assertTrue(extractFiles.size() > 0);
		log.debug("find "+extractFiles.size()+" entries in "+url);
		for (JFile f : extractFiles) {
			if (exts != null) {
				assertThat(f.getExt(), contains(exts));
				found.add(f);
			}
			if (names != null) {
				// don't verify extension
				String name = f.getName();
				int i = name.indexOf('.');
				if (i > 0) {
					name = name.substring(0, i);
				}
				assertThat(name, contains(names));
				found.add(f);
			}
			if(start > 0) {
				if(f.getLastModified() >= start && (end == 0 ||f.getLastModified() < end)) {
					assertTrue(true);
					found.add(f);
				} else {
					assertTrue(false);
				}
			}
			if(exts == null && names == null && start <= 0) {
				// default - accept all files
				found.add(f);
			}
		}
	}


}
