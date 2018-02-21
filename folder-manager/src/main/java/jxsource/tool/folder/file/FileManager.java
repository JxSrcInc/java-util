package jxsource.tool.folder.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jxsource.tool.folder.search.util.TreeFactory;

/**
 * Keep map between file path and its content CacheFile
 * it is thread-local instance
 * 
 */
public class FileManager {
	// key: JFile path, value: JFile
	private Map<String, JFile> map = new HashMap<String, JFile>();
	
	FileManager() {
		
	}
	public void add(JFile file) {
		map.put(file.getPath(), file);
	}
	public JFile get(String path) {
		return map.get(path);
	}
	public Map<String, JFile> getFiles() {
		return map;
	}
	public void reset() {
		map.clear();
	}
	
	public Set<JFile> buildTrees() {
		List<JFile> list = new ArrayList<JFile>(map.size());
		for(JFile f: map.values()) {
			list.add(f);
		}
		// added code below to normalize ZipFiles creation.
		Set<JFile> trees = new HashSet<JFile>();
		for(JFile f: TreeFactory.build().createTrees(list)) {
			JFile tree = addSegmentToRoot(f);
//			ObjectMapper mapper = new ObjectMapper();
//			try {
//				System.out.println("new Tree ##############################");
//				System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree.convertToJson()));
//			} catch (JsonProcessingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			trees.add(tree);
		}
		return trees;
	}
	
	/**
	 * zip file may contain entries in format segment-1/segment-2 and no entry for segment-1.
	 * It is OK for most zip search but cannot create a standard normal tree using only entries zip file.
	 * addSegmentToRoot will add missing segments up to root
	 * @param f
	 * @return
	 */
	private JFile addSegmentToRoot(JFile f) {
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			System.out.println("addSegmentToRoot ------------------");
//			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(f.convertToJson()));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if(f.getPath().equals(f.getName())) {
			// root sibling
			if(!map.containsKey(f.getPath())) {
				map.put(f.getPath(), f);
			}
			return f;
		}
		if(map.containsKey(f.getParentPath())) {
			JFile parent = map.get(f.getParentPath());
			if(!parent.getChildren().contains(f)) {
				parent.addChild(f);
			}
			return parent;
		}
		int i = f.getPath().lastIndexOf(f.getFileSeparator());
		if(i > 0) {
			String parentPath = f.getPath().substring(0, i);
			XFile xFile = new XFile(f.getFileSeparator());
			xFile.setPath(parentPath);
			xFile.setDirectory(true);
			xFile.addChild(f);
			f.setParent(xFile);
			map.put(parentPath, xFile);
			return addSegmentToRoot(xFile);
		} else {
			// this shouldn't happen
			throw new RuntimeException(f.getPath()+" dose not miss path to root");
		}
	}

}
