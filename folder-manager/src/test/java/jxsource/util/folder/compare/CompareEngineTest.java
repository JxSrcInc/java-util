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
import jxsource.util.folder.search.util.Util;

public class CompareEngineTest {

	@Test
	public void testTrue() {
		CompareEngine engine = new CompareEngine();
		JFile src = new SysFile(new File("./src"));
		JFile toCompare = new SysFile(new File("./src"));
		assertThat(engine.isDiff(new ComparableNode(src, toCompare)), is(false));
		assertThat(engine.isDiff(new ComparableNode(src, toCompare)), is(false));
	}
	@Test
	public void testFalse() {
		CompareEngine engine = new CompareEngine();
		JFile src = new SysFile(new File("./target"));
		JFile toCompare = new SysFile(new File("./src"));
		assertThat(engine.isDiff(new ComparableNode(src, toCompare)), is(true));
		assertThat(engine.isDiff(new ComparableNode(src, toCompare)), is(true));
	}
	@Test
	public void testZipSys() throws ZipException, IOException {
		System.setProperty(ZipFile.CachePropertyName, ZipFile.Memory);
		CompareEngine engine = new CompareEngine();
		engine.setLeafDiffer(null);
		JFile src = new SysFile(new File("testdata/test-data"));
		ZipSearchEngine zipEngine = new ZipSearchEngine();
		zipEngine.search(new File("testdata/test-data.jar"));
		Node jar = null;
		for(Node node: zipEngine.getTrees()) {
			if(node.getName().equals("test-data")) {
				jar = node;
				break;
			}
		}
//		String jsonReport  = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jar.convertToJson());
//		System.out.println(jsonReport);
		JFile jarFile = (JFile)jar;
		ComparableNode cmpNode = new ComparableNode(src, jarFile);
		String jsonStr  = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(Util.convertToJson(jarFile));
		System.out.println(jsonStr);
		
		System.out.println(engine.isDiff(cmpNode));
		System.out.println(engine.isDiff(cmpNode));
//		JsonNode jsonNode = JsonUtil.build().convertToJson(cmpNode);
//		try {
//		jsonStr  = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jarFile.convertToJson());
//		} catch(Throwable t) {
//			t.printStackTrace();
//		}
//		System.out.println("***** "+jsonStr);
//		
////		System.out.println(engine.isDiff(cmpNode));
////		jsonNode = JsonUtil.build().convertToJson(cmpNode);
////		jsonStr  = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(cmpNode.getToCompare().convertToJson());
////		System.out.println(jsonStr);
//		
		assertThat(engine.isDiff(cmpNode), is(false));
	}
}
