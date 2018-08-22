package jxsource.util.folder.compare;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jxsource.util.folder.compare.action.AdditionReport;
import jxsource.util.folder.compare.action.BaseReport;
import jxsource.util.folder.compare.action.LenDiffReport;
import jxsource.util.folder.compare.action.MissingReport;
import jxsource.util.folder.compare.action.NodeReport;
import jxsource.util.folder.compare.action.TimeDiffReport;
import jxsource.util.folder.compare.comparator.LastModifiedDiffer;
import jxsource.util.folder.compare.comparator.LeafDiffer;
import jxsource.util.folder.compare.comparator.LengthDiffer;
import jxsource.util.folder.compare.util.Constants;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.SysFile;
import jxsource.util.folder.node.ZipCacheTest;

public class CompareReportTest {
	private Logger log = LogManager.getLogger(ZipCacheTest.class);
	private PrintStream printer;
	private ByteArrayOutputStream out;
	private String[] expect;

	@Before
	public void init() {
		out = new ByteArrayOutputStream();
		printer = new PrintStream(out);
	}

	@After
	public void verify() {
		printer.close();
		String result = out.toString();
		// log.debug(result);
		for (String expect : this.expect) {
			String[] values = expect.split("@");
			boolean ok = false;
			for (String value : values) {
				if (result.contains(value)) {
					ok = true;
					break;
				}
			}
			log.debug(ok);
			log.debug(result + "," + expect);
			assertThat(ok, is(true));
			// assertThat(result, containsString(expect));
		}
	}

	@Test
	public void testLen() {
		LeafDiffer differ = new LengthDiffer();
		LenDiffReport report = new LenDiffReport();
		report.setPrintStream(printer);
		CompareEngine engine = new CompareEngine().addAction(report).setLeafDiffer(differ);
		JFile src = new SysFile(new File("testdata/test-data"));
		JFile toCompare = new SysFile(new File("testdata/test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);

		assertThat(engine.isDiff(comparableNode), is(true));
		expect = new String[] { String.format(Constants.diffPrintFormat, "/src/_pom_.txt", "diff-len",
				Constants.srcSymbol, "51", Constants.cmpSymbol, "0") };
	}

	@Test
	public void testTime() {
		LeafDiffer differ = new LastModifiedDiffer();
		TimeDiffReport report = new TimeDiffReport();
		report.setPrintStream(printer);
		CompareEngine engine = new CompareEngine().addAction(report).setLeafDiffer(differ);
		JFile src = new SysFile(new File("testdata/test-data"));
		JFile toCompare = new SysFile(new File("testdata/test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);

		assertThat(engine.isDiff(comparableNode), is(true));
		expect = new String[] { "/src/main/java/Data.java", "diff-time" };
	}

	@Test
	public void testMissing() {
		MissingReport report = new MissingReport();
		report.setPrintStream(printer);
		CompareEngine engine = new CompareEngine().addAction(report);
		JFile src = new SysFile(new File("testdata/test-data"));
		JFile toCompare = new SysFile(new File("testdata/test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);

		assertThat(engine.isDiff(comparableNode), is(true));
		expect = new String[] { String.format(Constants.arrayPrintFormat, "/src/main/java",
				Constants.srcSymbol + ".missing", "Date.java") };
	}

	@Test
	public void testAddition() {
		AdditionReport report = new AdditionReport();
		report.setPrintStream(printer);
		CompareEngine engine = new CompareEngine().addAction(report);
		JFile src = new SysFile(new File("testdata/test-data"));
		JFile toCompare = new SysFile(new File("testdata/test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);

		assertThat(engine.isDiff(comparableNode), is(true));
		expect = new String[] {
				String.format(Constants.arrayPrintFormat, "/src", Constants.srcSymbol + ".addition", "test"),
				String.format(Constants.arrayPrintFormat, "/", Constants.srcSymbol + ".addition", "resources, xyz")
						+ "@" + String.format(Constants.arrayPrintFormat, "/", Constants.srcSymbol + ".addition", "xyz, resources") };
	}

	@Test
	public void testNode() {
		LeafDiffer differ = new LengthDiffer();
		differ.setNext(new LastModifiedDiffer());
		BaseReport report = new NodeReport();
		report.setPrintStream(printer);
		CompareEngine engine = new CompareEngine().setLeafDiffer(differ).addAction(report);
		JFile src = new SysFile(new File("testdata/test-data"));
		JFile toCompare = new SysFile(new File("testdata/test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);

		assertThat(engine.isDiff(comparableNode), is(true));
		expect = new String[] {
				// String.format(Constants.diffPrintFormat,"/src/_pom_.txt","diff-len",Constants.srcSymbol,"51",Constants.cmpSymbol,"0"),
				String.format(Constants.arrayPrintFormat, "/src/main/java", Constants.srcSymbol + ".missing",
						"Date.java"),
				String.format(Constants.arrayPrintFormat, "/src", Constants.srcSymbol + ".addition", "test"),
				String.format(Constants.arrayPrintFormat, "/", Constants.srcSymbol + ".addition", "resources, xyz")
				+ "@" + String.format(Constants.arrayPrintFormat, "/", Constants.srcSymbol + ".addition", "xyz, resources"),
				"/src/main/java/Data.java", "diff-time" };
	}

}
