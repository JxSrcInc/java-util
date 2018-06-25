package jxsource.util.folder.compare;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import org.junit.Ignore;
import org.junit.Test;

import jxsource.util.folder.compare.CompareEngine;
import jxsource.util.folder.compare.action.PrintAction;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.node.ZipFile;
import jxsource.util.folder.search.ZipSearchEngine;
import jxsource.util.folder.search.util.NodeUtil;

public class CompareEngineTest {

	@Test
	public void testTrue() {
		CompareEngine engine = new CompareEngine()
				.setAction(new PrintAction());
		JFile src = new SysFile(new File("./src"));
		JFile toCompare = new SysFile(new File("./src"));
		assertThat(engine.isDiff(new ComparableNode(src, toCompare)), is(false));
	}
	@Test
	public void testFalse() {
		CompareEngine engine = new CompareEngine()
				.setAction(new PrintAction());
		JFile src = new SysFile(new File("./target"));
		JFile toCompare = new SysFile(new File("./src"));
		assertThat(engine.isDiff(new ComparableNode(src, toCompare)), is(true));
	}
	@Test
	public void testZipSys() throws ZipException, IOException {
		System.setProperty(ZipFile.CachePropertyName, ZipFile.Memory);
		CompareEngine engine = new CompareEngine()
				.setAction(new PrintAction());
		JFile src = new SysFile(new File("test-data"));
		ZipSearchEngine zipEngine = new ZipSearchEngine();
		zipEngine.search(new SysFile(new File("test-data.jar")));
		Node root = null;
		for(Node node: zipEngine.getTrees()) {
			if(node.getName().equals("test-data")) {
				root = node;
				break;
			}
		}
		JFile toCompare = (JFile)root;
		assertThat(engine.isDiff(new ComparableNode(src, toCompare)), is(false));
	}

}