package jxsource.util.folder.compare;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jxsource.util.folder.compare.comparator.LastModifiedDiffer;
import jxsource.util.folder.compare.comparator.LeafDiffer;
import jxsource.util.folder.compare.comparator.LengthDiffer;
import jxsource.util.folder.compare.util.JsonUtil;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.SysFile;

public class JsonReportTest {
	@Test
	public void jsonReportTest() throws JsonProcessingException {
		LeafDiffer differ = new LengthDiffer();
		differ.setNext(new LastModifiedDiffer());
		CompareEngine engine = new CompareEngine()
				.setLeafDiffer(differ);
		JFile src = new SysFile(new File("test-data"));
		JFile toCompare = new SysFile(new File("test-compare"));
		ComparableNode comparableNode = new ComparableNode(src, toCompare);
		assertThat(engine.run(comparableNode), is(true));
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = JsonUtil.build().convertToJson(comparableNode);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));
	}

}
