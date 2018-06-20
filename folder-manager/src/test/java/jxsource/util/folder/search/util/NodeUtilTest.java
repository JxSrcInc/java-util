package jxsource.util.folder.search.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.util.NodeUtil;
import jxsource.util.folder.search.util.TreeFactory;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NodeUtilTest {

	Node root;
	@Before
	public void init() {
		List<Node> files = new ArrayList<Node>();
		files.add(new SysFile(new File("root")));
		files.add(new SysFile(new File("root/src")));
		files.add(new SysFile(new File("root/src/abc.java")));
		files.add(new SysFile(new File("root/src/xyz")));
		files.add(new SysFile(new File("root/src/xyz/f")));
		files.add(new SysFile(new File("root/resources")));
		root = TreeFactory.build().createTree(files);		
	}
	
	@Test
	public void childTest() {
		Node src = NodeUtil.getChild(root, "src");
		assertThat(src.getName(), is("src"));
	}
	
	@Test
	public void srcTest() {
		Node child = NodeUtil.getPath(root, "src");
		assertThat(child.getName(), is("src"));
	}

	@Test
	public void xyzTest() {
		Node node = NodeUtil.getPath(root, "src/xyz");
		assertThat(node.getName(), is("xyz"));
	}
	@Test
	public void fTest() {
		Node node = NodeUtil.getPath(root, "src/xyz/f");
		assertThat(node.getName(), is("f"));
	}
	@Test
	public void abcTest() {
		Node node = NodeUtil.getPath(root, "src/abc.java");
		assertThat(node.getName(), is("abc.java"));
	}
	@Test
	public void nullTest() {
		Node node = NodeUtil.getPath(root, "not-exist");
		assert(node == null);
		node = NodeUtil.getPath(root, "resources/child");
		assert(node == null);
	}
	@Test
	public void convertTreeToListTest() {
		List<Node> list = NodeUtil.convertTreeToList(root);
		assertThat(list.size(), is(6));
		for(Node node: list) assertThat(node.getPath().contains("root"), is(true));
	}
}
