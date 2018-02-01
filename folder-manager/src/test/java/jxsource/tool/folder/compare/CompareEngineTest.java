package jxsource.tool.folder.compare;

import java.io.File;

import org.junit.Test;

import jxsource.tool.folder.compare.action.PrintCAction;
import jxsource.tool.folder.compare.comparator.PathDiffer;

public class CompareEngineTest {

	@Test
	public void test() {
		CompareEngine engine = new CompareEngine(new PathDiffer(), new PrintCAction());
		CFile src = new SysCFile(new File("./src"));
		CFile compareTo = new SysCFile(new File("./src"));
		engine.compare(src, compareTo);
	}
}
