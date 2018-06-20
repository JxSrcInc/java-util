package jxsource.util.folder.search.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.search.util.NodeUtil;
import jxsource.util.folder.search.util.TreeFactory;
import jxsource.util.folder.search.util.Util;

public class TreeFactoryTest {

	private static Logger log = LogManager.getLogger(TreeFactoryTest.class);
	private static Set<Node> roots;
	
	@BeforeClass
	public static void init() {
		List<Node> files = new ArrayList<Node>();
		files.add(new SysFile(new File("root")));
		files.add(new SysFile(new File("root/src")));
		files.add(new SysFile(new File("root/src/abc.java")));
		files.add(new SysFile(new File("root/src/xyz.java")));
		files.add(new SysFile(new File("root/resources")));
		files.add(new SysFile(new File("root1/x")));
		roots = TreeFactory.build().createTrees(files);
		for(Node node: roots) {
			try {
				log.debug(Util.getJson((JFile)node));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Test
	public void sizeTest() {
		int size = roots.size();
		assertThat(size, is(2));
	}
	
	@Test
	public void rootTest() {
		for(Node root: roots) {
			if(root.getName().equals("root")) {
				Node node = NodeUtil.getPath(root, "src/abc.java");
				assertThat(node.getName(), is("abc.java"));
				node = NodeUtil.getPath(root, "resourcs/abc.java");
				assertThat(node, nullValue());
			}
		}
	}
}
