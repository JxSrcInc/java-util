package jxsource.tool.folder.file;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import jxsource.tool.folder.search.SysSearchEngine;
import jxsource.tool.folder.search.action.CollectionAction;

public class FileManagerTest {

	@Test
	public void test() {
		CacheFileManager fileManager = CacheFileManagerHolder.get();
		fileManager.reset();
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.setCache(true);
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<JFile> files = ca.getFiles();
		Map<String, CacheFile> cacheFiles = fileManager.getCacheFiles();
		int count = 0;
		for(JFile f: files) {
			if(!f.isDirectory()) {
				count++;
			}
		}
		assertThat(cacheFiles.size(), is(count));
	}
}
