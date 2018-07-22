package jxsource.util.folder.compare;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jxsource.util.folder.compare.action.AdditionReport;
import jxsource.util.folder.compare.action.LenDiffReport;
import jxsource.util.folder.compare.action.MissingReport;
import jxsource.util.folder.compare.action.NodeReport;
import jxsource.util.folder.compare.action.TimeDiffReport;
import jxsource.util.folder.compare.comparator.LastModifiedDiffer;
import jxsource.util.folder.compare.comparator.LeafDiffer;
import jxsource.util.folder.compare.comparator.LengthDiffer;
import jxsource.util.folder.compare.util.JsonUtil;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.SysFile;

public class CompareReportTest {

	@Test
	public void testLen() {
		LeafDiffer differ = new LengthDiffer();
		CompareEngine engine = new CompareEngine()
				.setLeafDiffer(differ);
		JFile src = new SysFile(new File("test-data"));
		JFile toCompare = new SysFile(new File("test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);
		
		assertThat(engine.run(comparableNode), is(true));
	}
	@Test
	public void testTime() {
		LeafDiffer differ = new LastModifiedDiffer();
		CompareEngine engine = new CompareEngine()
				.addAction(new TimeDiffReport())
				.setLeafDiffer(differ);
		JFile src = new SysFile(new File("test-data"));
		JFile toCompare = new SysFile(new File("test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);
		
		assertThat(engine.run(comparableNode), is(true));
	}
	@Test
	public void testMissing() {
		CompareEngine engine = new CompareEngine()
				.addAction(new MissingReport());
		JFile src = new SysFile(new File("test-data"));
		JFile toCompare = new SysFile(new File("test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);
		
		assertThat(engine.run(comparableNode), is(true));
	}
	@Test
	public void testAddition() {
		CompareEngine engine = new CompareEngine()
				.addAction(new AdditionReport());
		JFile src = new SysFile(new File("test-data"));
		JFile toCompare = new SysFile(new File("test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);
		
		assertThat(engine.run(comparableNode), is(true));
	}
	@Test
	public void testNode() {
		LeafDiffer differ = new LengthDiffer();
		differ.setNext(new LastModifiedDiffer());
		CompareEngine engine = new CompareEngine()
				.setLeafDiffer(differ)
				.addAction(new NodeReport());
		JFile src = new SysFile(new File("test-data"));
		JFile toCompare = new SysFile(new File("test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);
		
		assertThat(engine.run(comparableNode), is(true));
	}


}
