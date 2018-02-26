package jxsource.tool.folder.search.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jxsource.tool.folder.cachefile.SysCacheEngine;
import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.Node;
import jxsource.tool.folder.search.action.CollectionAction;

public class TreeFactoryTest {

	@Test
	public void treeFactoryTest() {
		String root = "test-data";
		SysCacheEngine engin = new SysCacheEngine();
		CollectionAction ca = new CollectionAction();
		ca.setUrl(root);
		engin.addAction(ca);
		engin.search(new File(root));
		List<Node> files = ca.getNodes();
		ObjectMapper mapper = new ObjectMapper();
		Set<Node> roots = TreeFactory.build().createTrees(files);
		assertThat(roots.size(), is(1));
		Node rootFile = TreeFactory.build().createTree(files);
		
		assertThat(rootFile.getChildren().size(), is(3));
		JsonNode node = rootFile.convertToJson();
		// test-data
		assertThat(node.findPath("path").textValue(), is(root));
		// test-data/resources
		JsonNode resources = ((ArrayNode)node.findPath("children")).get(0);
		assertThat(resources.get("name").textValue(), is("resources"));
		assertThat((resources.get("children")).size(), is(2));
//		JsonNode json = ((ArrayNode)node.get("children")).get(1).get("children");
//		try {
//			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// test-data/src
		JsonNode src = ((ArrayNode)node.findPath("children")).get(0);
		assertThat((src.get("children")).size(), is(2));
		// test-data/xyz
		JsonNode xyz = ((ArrayNode)node.get("children")).get(2);
		assertThat((xyz.get("children")).size(), is(1));
		JsonNode emptyDir = ((ArrayNode)xyz.get("children")).get(0);
		assertThat(emptyDir.get("name").textValue(), is("empty-dir"));

//		try {
//			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
