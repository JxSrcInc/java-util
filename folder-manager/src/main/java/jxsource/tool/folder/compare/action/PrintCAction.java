package jxsource.tool.folder.compare.action;

import jxsource.tool.folder.compare.CFile;

public class PrintCAction implements CAction
{
	public void proc(CFile src, CFile target) {
		System.out.println("PRintCAction: "+src);
		System.out.println("PRintCAction: "+target);
	}

}
