package jxsource.util.folder.file;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import jxsource.util.folder.cachefile.SysCacheEngine;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.NodeManager;
import jxsource.util.folder.node.NodeManagerHolder;
import jxsource.util.folder.search.SysSearchEngine;
import jxsource.util.folder.search.action.CollectionAction;

public class FileManagerTest {

	@Test
//	@Ignore
	public void test() {
		NodeManager fileManager = NodeManagerHolder.get();
		fileManager.reset();
		String root = "./test-data";
		SysCacheEngine engin = new SysCacheEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
//		engin.setCache(true);
		engin.search(new File(root));
		assertThat(root, is(ca.getUrl()));
		List<Node> files = ca.getNodes();
		Map<String, Node> cacheFiles = fileManager.getFiles();
		int count = 0;
		for(Node f: files) {
			if(!((JFile)f).isDir()) {
				count++;
			}
		}
		assertThat(cacheFiles.size(), is(count));
	}
}
