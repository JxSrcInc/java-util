package jxsource.util.folder.compare;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jxsource.util.folder.compare.CompareEngine;
import jxsource.util.folder.compare.action.PrintAction;
import jxsource.util.folder.compare.comparator.LastModifiedDiffer;
import jxsource.util.folder.compare.comparator.LeafDiffer;
import jxsource.util.folder.compare.comparator.LengthDiffer;
import jxsource.util.folder.compare.util.JsonUtil;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.node.ZipFile;
import jxsource.util.folder.search.ZipSearchEngine;
import jxsource.util.folder.search.util.NodeUtil;

public class CompareEngineTest {

	@Test
	public void testTrue() {
		CompareEngine engine = new CompareEngine();
		JFile src = new SysFile(new File("./src"));
		JFile toCompare = new SysFile(new File("./src"));
		assertThat(engine.run(new ComparableNode(src, toCompare)), is(false));
	}
	@Test
	public void testFalse() {
		CompareEngine engine = new CompareEngine();
		JFile src = new SysFile(new File("./target"));
		JFile toCompare = new SysFile(new File("./src"));
		assertThat(engine.run(new ComparableNode(src, toCompare)), is(true));
	}
	@Test
	public void testZipSys() throws ZipException, IOException {
		System.setProperty(ZipFile.CachePropertyName, ZipFile.Memory);
		CompareEngine engine = new CompareEngine();
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
		assertThat(engine.run(new ComparableNode(src, toCompare)), is(false));
	}
	@Test
	public void print() throws JsonProcessingException {
		LeafDiffer differ = new LengthDiffer();
		differ.setNext(new LastModifiedDiffer());
		CompareEngine engine = new CompareEngine()
				.setLeafDiffer(differ);
		JFile src = new SysFile(new File("test-data"));
		JFile toCompare = new SysFile(new File("test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);
		assertThat(engine.run(comparableNode), is(true));
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = JsonUtil.build().convertToJson(comparableNode);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));
	}
}
