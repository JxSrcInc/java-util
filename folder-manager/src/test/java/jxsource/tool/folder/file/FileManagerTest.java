package jxsource.tool.folder.file;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import jxsource.tool.folder.cachefile.SysCacheEngine;
import jxsource.tool.folder.node.NodeManager;
import jxsource.tool.folder.node.NodeManagerHolder;
import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.Node;
import jxsource.tool.folder.search.SysSearchEngine;
import jxsource.tool.folder.search.action.CollectionAction;

public class FileManagerTest {

	@Test
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
			if(!((JFile)f).isArray()) {
				count++;
			}
		}
		assertThat(cacheFiles.size(), is(count));
	}
}
