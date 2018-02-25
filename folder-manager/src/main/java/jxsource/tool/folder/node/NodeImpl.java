package jxsource.tool.folder.node;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class NodeImpl extends AbstractNode {

	private Object content;
	@Override
	public Object getContent() {
		return content;
	}
	@Override
	public void setContent(Object node) {
		this.content = content;
		
	}

}
