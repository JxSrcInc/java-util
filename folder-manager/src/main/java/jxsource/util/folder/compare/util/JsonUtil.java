package jxsource.util.folder.compare.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jxsource.util.folder.compare.ComparableNode;
import jxsource.util.folder.node.JFile;
import jxsource.util.folder.node.Node;

public class JsonUtil {
	private static SimpleDateFormat format = Constants.timeFormat;
	private static ObjectMapper om = new ObjectMapper();
	private String srcPath;
	private String cmpPath;

	private JsonUtil() {
		
	}
	
	public static JsonUtil build() {
		return new JsonUtil();
	}
//	public static JsonUtilBuilder getBuilder() {
//		return new JsonUtilBuilder();
//	}
//	public static class JsonUtilBuilder {
//		private String srcPath;
//		private String cmpPath;
//		public JsonUtilBuilder setSrcPath(String srcPath) {
//			this.srcPath = srcPath;
//			return this;
//		}
//		public JsonUtilBuilder setToComparePath(String cmpPath) {
//			this.cmpPath = cmpPath;
//			return this;
//		}
//		public JsonUtil build() {
//			JsonUtil jsonUtil = new JsonUtil();
//			jsonUtil.cmpPath = cmpPath;
//			jsonUtil.srcPath = srcPath;
//			return jsonUtil;
//		}
//	}

	public ArrayNode convertNodeCollectionToJsonArrau(Collection<Node> src) {
		ArrayNode arrayNode = om.createArrayNode();
		if (src.size() > 0) {
			for (Node node : src) {
				arrayNode.add(node.getName());
			}
		}
		return arrayNode;
	}

	public JsonUtil addMissingJson(ObjectNode parent, ComparableNode cmpNode) {
		if(cmpNode.getMissing().size() > 0) {
		parent.set(Constants.srcSymbol+".missing", 
			convertNodeCollectionToJsonArrau(cmpNode.getMissing()));
		}
		return this;
	}

	public JsonUtil addExtraJson(ObjectNode parent, ComparableNode cmpNode) {
		if(cmpNode.getExtra().size() > 0) {
		parent.set(Constants.srcSymbol+".addition", 
			convertNodeCollectionToJsonArrau(cmpNode.getExtra()));
		}
		return this;
	}

	public JsonUtil addSameJson(ObjectNode parent, ComparableNode cmpNode) {
		if(cmpNode.getSame().size() > 0) {
		ArrayNode array = om.createArrayNode();
		for (ComparableNode sameNode : cmpNode.getSame()) {
			array.add(sameNode.getSrc().getName());
		}
		 parent.set(Constants.srcSymbol+Constants.cmpSymbol+".have", array);
		}
		return this;
	}

	public JsonUtil addDiffJson(ObjectNode parent, ComparableNode cmpNode) {
		String diffType = cmpNode.getDiffType();
		if (diffType != null) {
			JFile sFile = (JFile) cmpNode.getSrc();
			JFile cFile = (JFile) cmpNode.getToCompare();
			String diff = null;
			if (diffType.equals(ComparableNode.LengthDiff)) {
				diff = createDiff("Length Diff", sFile.getLength(), cFile.getLength());
			} else if (diffType.equals(ComparableNode.LastModifiedDiff)) {
				diff = createDiff("LastModified Diff", format.format(new Date(sFile.getLastModified())),
						format.format(new Date(cFile.getLastModified())));
			} else if (diffType.equals(ComparableNode.NodeDiff)) {
				String sType = (sFile.isDir() ? "Dir" : "File");
				String cType = (cFile.isDir() ? "Dir" : "File");
				diff = createDiff("Type Diff", sType, cType);
			}
			parent.put(Constants.srcSymbol+Constants.cmpSymbol+"."+diffType, diff);
		}
		return this;
	}

	private String createDiff(String diffType, Object srcValue, Object cmpValue) {
		ObjectNode node = om.createObjectNode();
		return Constants.srcSymbol + ": " + srcValue + ", " + Constants.cmpSymbol + ": " + cmpValue;
	}
	
	public JsonNode convertToJson(ComparableNode startNode) {
		srcPath = startNode.getSrc().getPath();
		cmpPath = startNode.getToCompare().getPath();
		ObjectNode root = om.createObjectNode();
		root.put(Constants.srcSymbol, srcPath);
		root.put(Constants.cmpSymbol, cmpPath);
		convertToJson(root, startNode);
		return root;
	}
	
	private void convertToJson(ObjectNode parent, ComparableNode cmpNode) {
		addDiffJson(parent, cmpNode);
		addMissingJson(parent, cmpNode);
		addExtraJson(parent, cmpNode);
		Set<ComparableNode> diff = cmpNode.getDiff();
		if (diff.size() > 0) {
			ArrayNode diffArray = om.createArrayNode();
			for (ComparableNode diffNode : diff) {
				ObjectNode childNode = om.createObjectNode();
				convertToJson(childNode, diffNode);
				ObjectNode child = om.createObjectNode();
				child.set(Constants.srcSymbol+Constants.cmpSymbol+"."+diffNode.getComparePath(), childNode);
				diffArray.add(child);
			}
			parent.set("diff-nodes", diffArray);
		}		
	}
}
