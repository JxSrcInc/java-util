package jxsource.util.folder.search.action;

import jxsource.util.folder.node.Node;

public class XmlReadAction<T extends Node> implements Action<T>{

	public void proc(T file) {
		if(file.isDir()) return;
		System.out.println("XmlReadAction: "+file);		
	}

}
