package jxsource.util.folder.search.action;

import jxsource.util.folder.node.Node;

public class XmlReadAction implements Action{

	public void proc(Node file) {
		if(file.isDir()) return;
		System.out.println("XmlReadAction: "+file);		
	}

}
