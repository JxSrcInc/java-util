package jxsource.tool.folder.search.action;

import jxsource.tool.folder.file.AbstractJFile;

public class XmlReadAction implements Action{

	public void proc(AbstractJFile file) {
		System.out.println("XmlReadAction: "+file);		
	}

}
