package jxsource.tool.folder.search.action;

import jxsource.tool.folder.node.Node;

public class XmlReadAction implements Action{

	public void proc(Node file) {
		System.out.println("XmlReadAction: "+file);		
	}

}
