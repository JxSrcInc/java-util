package jxsource.util.folder.search.zip;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.ZipFile;
import jxsource.util.folder.search.util.Util;

public class ZipReportCollection extends ZipReportAction {
	private static Logger log = LogManager.getLogger(ZipReportCollection.class);
	private String[] names;
	private String[] exts;
	private long start, end;
	private Set<JFile> found = new HashSet<JFile>();
	
	private Matcher<String> contains(final String[] matchs) {
		return new BaseMatcher<String>() {
			public boolean matches(final Object obj) {
				String src = (String) obj;
				for (String match : matchs) {
						if(src.contains(match)) {
							log.debug("Match: "+match+" with "+src);
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

	public Set<JFile> getFound() {
		return found;
	}
	public List<JFile> getFoundFiles() {
		List<JFile> foundFiles = new ArrayList<JFile>();
		for(JFile f: getFound()) {
			if(!f.isDir()) {
				foundFiles.add(f);
			}
		}
		return foundFiles;
	}

	public ZipReportCollection setName(String multiNames) {
		names = Util.toArray(multiNames);
		return this;
	}

	public ZipReportCollection setExt(String multiExts) {
		exts = Util.toArray(multiExts);
		return this;
	}
	public ZipReportCollection setStart(long start) {
		this.start = start;
		return this;
	}
	public ZipReportCollection setEnd(long end) {
		this.end = end;
		return this;
	}

	private boolean inArray(String match, String[] array) {
		for(String ele: array) { 
			if(ele.equals(ele)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void report(String url, List<ZipFile> extractFiles) {
//		assertTrue(extractFiles.size() > 0);
		log.debug("find "+extractFiles.size()+" entries in "+url);
		for (Node node : extractFiles) {
			// TODO: Node/JFile
			JFile f = (JFile) node;
			if (exts != null) {
				if(inArray(f.getExt(), exts)) {
					add(f);
				}
			}
			if (names != null) {
				// don't verify extension
				String name = f.getName();
				int i = name.indexOf('.');
				if (i > 0) {
					name = name.substring(0, i);
				}
				if(inArray(name, names)) {
					add(f);
				}
			}
			if(start > 0) {
				if(f.getLastModified() >= start && (end == 0 ||f.getLastModified() < end)) {
					assertTrue(true);
					add(f);
				} else {
					assertTrue(false);
				}
			}
			if(exts == null && names == null && start <= 0) {
				// default - accept all files
				add(f);
			}
		}
	}

	private void add(JFile f) {
		found.add(f);		
	}
}
