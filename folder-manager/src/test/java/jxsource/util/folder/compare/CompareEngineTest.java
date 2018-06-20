package jxsource.util.folder.compare;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import jxsource.util.folder.compare.CompareEngine;
import jxsource.util.folder.compare.action.PrintAction;
import jxsource.util.folder.compare.comparator.PathDiffer;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.SysFile;

public class CompareEngineTest {

	@Test
	@Ignore
	public void test() {
		CompareEngine engine = new CompareEngine(new PathDiffer(), new PrintAction());
		JFile src = new SysFile(new File("./src"));
		JFile compareTo = new SysFile(new File("./src"));
		engine.compare(src, compareTo);
	}
}
