package jxsource.util.folder.compare.action;

import java.io.PrintStream;

import jxsource.util.folder.compare.ComparableNode;

public abstract class BaseReport implements Action{

	protected PrintStream printer;
	
	protected BaseReport() {
		printer = System.out;
	}
	public void setPrintStream(PrintStream printer) {
		this.printer = printer;
	}
}
