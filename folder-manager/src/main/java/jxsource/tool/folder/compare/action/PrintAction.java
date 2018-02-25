package jxsource.tool.folder.compare.action;

import jxsource.tool.folder.file.Node;

public class PrintAction implements Action
{
	public void proc(Node src, Node target) {
		System.out.println("PRintCAction: "+src);
		System.out.println("PRintCAction: "+target);
	}

}
