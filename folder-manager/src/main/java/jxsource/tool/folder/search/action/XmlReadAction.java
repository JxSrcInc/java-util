package jxsource.tool.folder.search.action;

import jxsource.tool.folder.file.JFile;

public class XmlReadAction implements Action{

	public void proc(JFile file) {
		System.out.println("XmlReadAction: "+file);		
	}

}
