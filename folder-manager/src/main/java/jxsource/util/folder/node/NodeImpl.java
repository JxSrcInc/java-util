package jxsource.util.folder.node;

/**
 * Internal Node which is not mapped to external file - system file or
 * entry of a zip file.
 * 
 * It used when NodeManager build a tree from zip file but all entries in
 * the zip file is not a complete tree.
 * @author JiangJxSrc
 *
 */
public class NodeImpl extends AbstractNode {

	private Object content;
	
	//note really used but need to make complete Node object
	@Override
	public Object getContent() {
		return content;
	}
	//note really used but need to make complete Node object
	@Override
	public void setContent(Object node) {
		this.content = content;
		
	}

}
