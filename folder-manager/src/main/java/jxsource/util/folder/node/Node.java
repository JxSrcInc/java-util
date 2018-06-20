package jxsource.util.folder.node;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Interface to build a tree
 * @author JiangJxSrc
 *
 */
public interface Node extends Comparable<Node>{
	public void setAbsolutePath(String absolutePath);
	public String getAbsolutePath();
	public char getPathSeparator();
	public String getPath();
	public void setPath(String path);
	public String getName();
	public void setName(String name);
	public Object getContent();
	public void setContent(Object content);
	public String getParentPath();
	public Node getParent();
	public void setParent(Node node);
	public List<Node> getChildren();
	public void setChildren(List<Node> children);
	public void addChild(Node child);
	public boolean isDir();
	public void setArray(boolean directory);
	public int hashCode();
	public boolean equals(Object node);
	public JsonNode convertToJson();

}
