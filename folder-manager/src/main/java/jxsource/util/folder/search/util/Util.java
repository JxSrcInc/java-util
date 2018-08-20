package jxsource.util.folder.search.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;
import jxsource.util.folder.node.SysFile;

public class Util {
	static ObjectMapper mapper = new ObjectMapper();
//	public static final String[] archiveTypes = new String[] {
//			"jar", "zip"
//	};
	public static final String archiveTypes = "jar,zip";
	
	public static boolean isArchive(File f) {
		return isArchive(new SysFile(f));
	}
	public static boolean isArchive(JFile f) {
		String[] types = archiveTypes.split(",");
		for(int i=0; i<types.length; i++) {
			if(types[i].equals(f.getExt().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	public static String[] toArray(String src) {
		String[] array = src.split(",");
		for(int i=0; i<array.length; i++) {
			array[i] = array[i].trim();
		}
		return array;
	}
	// not thread safe
	public static StringBuilder getContent(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		Reader r = new InputStreamReader(in);
		char[] cbuf = new char[1024*8];
		int i = 0;
		while((i=r.read(cbuf)) != -1) {
			sb.append(cbuf,0,i);
		}
		return sb;
	}
	public static String getFileSeparator(JFile f) {
		String fileSeparator = ""+f.getPathSeparator();
		if(fileSeparator.equals("\\"))
			fileSeparator += '\\';
		return fileSeparator;
	}
	/**
	 * 
	 * @param f
	 * @param fileSeparator
	 * @return
	 */
	public static int getFileSeparatorCount(JFile f, String...fileSeparator) {
		String separator = fileSeparator.length>0?fileSeparator[0]:getFileSeparator(f);
		return f.getPath().split(separator).length;
	}
	public static String getJson(Node file) {
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		ObjectNode node = mapper.createObjectNode();
		node.put("name", file.getName());
		node.put("path", file.getPath());
		node.put("parent",  file.getParentPath());
		if(file.getChildren().size() > 0) {
		ArrayNode children = mapper.createArrayNode();
		for(Node child: file.getChildren()) {
			children.add(convertNodeToJsonNode(child));
		}
		node.set("children", children);
		}
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error when convert to JSON for file ", e);
		}
	}

	public static JsonNode convertNodeToJsonNode(Node file) {
		ObjectNode jsonNode = mapper.createObjectNode();
		jsonNode.put("name", file.getName());
		jsonNode.put("path", file.getPath());
		jsonNode.put("parent",  file.getParentPath());
		if(file.getChildren().size() > 0) {
		ArrayNode children = mapper.createArrayNode();
		for(Node child: file.getChildren()) {
			children.add(convertNodeToJsonNode(child));
		}
		jsonNode.set("children", children);
		}
		
		return jsonNode;
	}
	public static List<JFile> convertNodeToJFile(List<Node> nodes) {
		List<JFile> files = new ArrayList<JFile>();
		for(Node f: nodes) {
			if(f == null || f instanceof JFile) {
				nodes.add((JFile)f);
			}
		}
		return files;
	}
	public static List<Node> convertJFileToNode(List<JFile> files) {
		List<Node> nodes = new ArrayList<Node>();
		for(JFile f: files) {
			nodes.add(f);
		}
		return nodes;
	}
	public static JsonNode convertToJson(Node src) {
		ObjectNode node = mapper.createObjectNode();
		node.put("path", src.getPath().replaceAll("\\\\", "/"));
		node.put("name", src.getName());
		node.put("dir", src.isDir());
		if(src instanceof JFile) {
			node.put("len", ((JFile)src).getLength());
			node.put("time",((JFile)src).getLastModified());
		}
		List<Node> children = src.getChildren();
		if(children != null) {
			ArrayNode childrenNode = node.putArray("children");//node.arrayNode();
			for(Node child: children) {
				childrenNode.add(Util.convertToJson(child));
			}
		}
		return node;
	}
}
