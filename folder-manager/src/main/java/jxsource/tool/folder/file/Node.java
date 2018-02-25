package jxsource.tool.folder.file;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public interface Node extends Comparable<JFile>{
	public String getPath();
	public void setPath(String path);
	public String getName();
	public void setName(String name);
	public Object getContent();
	public void setContent(Object content);
	public Node getParent();
	public void setParent(Node node);
	public List<Node> getChildren();
	public void setChildren(List<Node> children);
	public void addChild(Node child);
	public int hashCode();
	public boolean equals(Object node);
	public JsonNode convertToJson();

}
