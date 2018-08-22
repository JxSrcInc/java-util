package jxsource.util.folder.compare.action;

import java.io.PrintStream;

import jxsource.util.folder.compare.ComparableNode;

public class NodeReport extends BaseReport {
	private LenDiffReport diff = new LenDiffReport();
	private TimeDiffReport time = new TimeDiffReport();
	private MissingReport missing = new MissingReport();
	private AdditionReport addition = new AdditionReport();
	
	@Override
	public void setPrintStream(PrintStream printer) {
		diff.setPrintStream(printer);
		time.setPrintStream(printer);
		missing.setPrintStream(printer);
		addition.setPrintStream(printer);
	}

	@Override
	public void proc(ComparableNode comparableNode) {
		diff.proc(comparableNode);
		time.proc(comparableNode);
		missing.proc(comparableNode);
		addition.proc(comparableNode);
	}

}
