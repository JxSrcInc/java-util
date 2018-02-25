package jxsource.tool.folder.search.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.Node;

public class Util {
	public static final String[] archiveTypes = new String[] {
			"jar", "zip"
	};
	public static boolean isArchive(JFile f) {
		for(int i=0; i<archiveTypes.length; i++) {
			if(archiveTypes[i].equals(f.getExt().toLowerCase())) {
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
	public static String getContent(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		Reader r = new InputStreamReader(in);
		char[] cbuf = new char[1024*8];
		int i = 0;
		while((i=r.read(cbuf)) != -1) {
			sb.append(cbuf,0,i);
		}
		return sb.toString();
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
	public static String getJson(JFile file) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);		
		try {
			return mapper.writeValueAsString(file);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error when convert to JSON for file "+file.getPath(), e);
		}
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
}
