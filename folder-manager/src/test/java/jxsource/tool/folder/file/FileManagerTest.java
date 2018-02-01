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
		FileManager fileManager = FileManagerHolder.get();
		fileManager.reset();
		String root = "./test-data";
		SysSearchEngine engin = new SysSearchEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<JFile> files = ca.getFiles();
		Map<String, JFile> map = fileManager.getMap();
		assertThat(files.size(), is(map.size()));
	}
}
