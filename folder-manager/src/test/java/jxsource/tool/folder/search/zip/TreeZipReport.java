package jxsource.tool.folder.search.zip;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jxsource.tool.folder.node.JFile;
import jxsource.tool.folder.node.Node;

public class TreeZipReport extends ZipReportAction{
	private static Logger log = LogManager.getLogger(TreeZipReport.class);
	@Override
	public void report(String url, List<Node> trees) {
		log.debug("url: "+url);
		ObjectMapper mapper = new ObjectMapper();
		for(Node f: trees) {
			JsonNode node = f.convertToJson();
			log.debug(f.getPath()+" ****************************************************");
			try {
				log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
