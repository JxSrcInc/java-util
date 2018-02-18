package jxsource.tool.folder.search.action;

import jxsource.tool.folder.file.FileManagerHolder;
import jxsource.tool.folder.file.JFile;
import jxsource.tool.folder.search.util.Util;

public class ProcessWatchAction implements Action {
	private String root;
	private int rootLevel;
	private int watchLevel = 0;
	private String fileSeparator;
	public ProcessWatchAction(JFile root) {
		this.root = root.getPath();
		fileSeparator = Util.getFileSeparator(root);
//		fileSeparator = root.getFileSeparator();
//		if(fileSeparator.equals("\\"))
//			fileSeparator += '\\';
		rootLevel = root.getPath().split(fileSeparator).length;
	}
	public ProcessWatchAction setWatchLevel(int watchLevel) {
		this.watchLevel = watchLevel;
		return this;
	}
	public void proc(JFile f) {
			if(f.getPath().split(fileSeparator).length < (rootLevel+watchLevel)) {
				System.out.println("FilePrintAction: "+f.getPath());
				
			}
	}
}