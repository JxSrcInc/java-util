package jxsource.tool.folder.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jxsource.tool.folder.search.util.Util;

public abstract class AbstractJFile implements JFile{
	public static final String Root = "";
	protected String name;
	protected String path;
	protected long length;
	protected boolean directory;
	protected char fileSeparator;
	protected List<JFile> children; 
	protected long lastModified;
	protected String parentPath;
	private static ObjectMapper mapper = new ObjectMapper();

	/*
	 * Null for parent has two means
	 * 1. this JFile has no parent.
	 * 2. this JFile hasn't been set.
	 *  - parent may not be setup when creating this JFile. e.g. ZipFile
	 *    It may be set by TreeFactory
	 */
	protected JFile parent;
	protected AbstractJFile(char fileSeparator) {
		this.fileSeparator = fileSeparator;
	}

	private ObjectNode initJsonNode(ObjectNode node) {
		node.put("path", path.replaceAll("\\\\", "/"));
		node.put("name", name);
		node.put("dir", directory);
		node.put("len", length);
		node.put("time",lastModified);
		return node;
	}

	public JsonNode convertToJson() {
		ObjectNode node = mapper.createObjectNode();
		node = initJsonNode(node);
		if(children != null) {
			ArrayNode childrenNode = node.putArray("children");//node.arrayNode();
			for(JFile child: children) {
				childrenNode.add(child.convertToJson());
			}
		}
		return node;
	}
	public void addChild(JFile child) {
		// children may be null
		// because it is lazy load for performance consideration
		if(children == null) {
			children = new ArrayList<JFile>();
		}
		if(!children.contains(child)) {
			children.add(child);
		}
	}
	public void setParent(JFile parent) {
		this.parent = parent;
	}
	// see parent definition for meaning of returned NULL value
	public JFile getParent() {
		return parent;
	}
	public String getParentPath() {
		if(parentPath == null) {
			String[] ele = path.split("\\"+fileSeparator);
			parentPath = "";
			for(int i=0; i<ele.length-2; i++) {
				parentPath += ele[i] + fileSeparator;
			}
			if(ele.length > 1) {
				parentPath += ele[ele.length-2];
			} else {
				parentPath = Root;
			}
		}
		return parentPath;
//		int index = path.indexOf(name);
//		if(index > 0) {
//			index--;
//		}
//		return path.substring(0, index);
	}
	public char getFileSeparator() {
		return fileSeparator;
	}
	public void setChildren(List<JFile> children) {
		this.children = children;
	}
	public List<JFile> getChildren() {
		return children;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
		int i = path.lastIndexOf(fileSeparator);
		if(i > 0) {
			name = path.substring(i+1);
		} else {
			name = path;
		}
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public boolean isDirectory() {
		return directory;
	}
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}
	public String getExt() {
		int i = name.lastIndexOf('.');
		return name.substring(i+1);
	}
//	@Override
//	public String toString() {
//		return path + ',' + length;
//	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "AbstractJFile [name=" + name + ", path=" + path + ", directory=" + directory
				+ ", parent=" + parentPath + ", children=" + children + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractJFile other = (AbstractJFile) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	@Override
	public int compareTo(JFile o) {
		if(o == null) {
			return -1;
		}
		return path.compareTo(o.getPath());
	}
	
}
