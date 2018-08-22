package jxsource.util.folder.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode implements Node, Serializable {
	private static final long serialVersionUID = 1L;
	public static final String Root = "";
	protected String name;
	protected String path;
	protected String absolutePath;
	protected long length;
	protected boolean directory;
	protected char fileSeparator = '/';
	protected List<Node> children = new ArrayList<Node>(); 
	protected long lastModified;
	protected String parentPath;

	/*
	 * Null for parent has two means
	 * 1. this JFile has no parent.
	 * 2. this JFile hasn't been set.
	 *  - parent may not be setup when creating this JFile. e.g. ZipFile
	 *    It may be set by TreeFactory
	 */
	protected Node parent;

	
	@Override
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
		
	}

	@Override
	public String getAbsolutePath() {
		return absolutePath;
	}

	@Override
	public void addChild(Node child) {
		// children may be null
		// because it is lazy load for performance consideration
		if(children == null) {
			children = new ArrayList<Node>();
		}
		if(!children.contains(child)) {
			children.add(child);
		}
		// Added to fix bug when TreeFactory adds child in case directory is not initialed 
		// in real Node/JFile instance. For example, in testing, a real java.io.File is created 
		// in fly and does not exist in file system
		if(!directory) {
			directory = true;
		}
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	// see parent definition for meaning of returned NULL value
	public Node getParent() {
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
	}
	public char getPathSeparator() {
		return fileSeparator;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	public List<Node> getChildren() {
		return children;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		setPath(name);
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
	public boolean isDir() {
		return directory;
	}
	public void setArray(boolean directory) {
		this.directory = directory;
	}
	public String getExt() {
		int i = name.lastIndexOf('.');
		return name.substring(i+1);
	}
	
	@Override
	public String toString() {
		return "AbstractJFile [name=" + name + ", path=" + path + ", directory=" + directory
				+ ", parent=" + parentPath + ", children=" + children + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractNode other = (AbstractNode) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public int compareTo(Node o) {
		if(o == null) {
			return -1;
		}
		return path.compareTo(o.getPath());
	}
	
}
