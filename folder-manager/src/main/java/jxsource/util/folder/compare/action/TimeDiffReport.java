package jxsource.util.folder.compare.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import jxsource.util.folder.compare.ComparableNode;
import jxsource.util.folder.compare.util.Constants;
import jxsource.util.folder.node.JFile;

public class TimeDiffReport extends BaseReport {
	private static SimpleDateFormat format = Constants.timeFormat;

	@Override
	public void proc(ComparableNode comparableNode) {
		String diffType = comparableNode.getDiffType();
		if(diffType != null && diffType.equals(ComparableNode.LastModifiedDiff)) {
			printer.printf(Constants.diffPrintFormat,
					comparableNode.getComparePath(),
					diffType,
					Constants.srcSymbol,
					format.format(new Date(((JFile)comparableNode.getSrc()).getLastModified())),
					Constants.cmpSymbol,
					format.format(new Date(((JFile)comparableNode.getToCompare()).getLastModified()))
					);
		}
		
	}

}
