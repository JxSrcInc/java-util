package jxsource.util.cl.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jxsource.util.cl.cff.CFFormat;

public class ClassRegistory {
	private static Logger log = LogManager.getLogger(ClassRegistory.class);
	// key: classpath@uri, value: CFFormat
	private Map<String, CFFormat> cffRegistory = new ConcurrentHashMap<String, CFFormat>();
	// key: classpath, value: set of uri - it may be a simple file path
	private Map<String, Set<String>> uriRegistory = new ConcurrentHashMap<String, Set<String>>();
	private final String Symbol = "@";
	private static ClassRegistory me;

	private ClassRegistory() {
	}

	public static ClassRegistory getInstance() {
		if (me == null) {
			me = new ClassRegistory();
		}
		return me;
	}

	public synchronized void add(CFFormat cff, String uri) {
		String classPath = cff.getClassName();
		cffRegistory.put(classPath + Symbol + uri, cff);
		addUri(classPath, uri);
	}

	private synchronized void addUri(String classPath, String uri) {
		log.debug(classPath+" - "+uri);
		Set<String> uriSet = uriRegistory.get(classPath);
		if (uriSet == null) {
			uriSet = new HashSet<String>();
			uriRegistory.put(classPath, uriSet);
		}
		uriSet.add(uri);
	}

	/*
	 * add by input stream
	 */
	public synchronized void add(InputStream is, String uri) throws IOException {
		add(new CFFormat(is), uri);
	}

	/*
	 * add by File
	 */
	public synchronized void add(File path) throws FileNotFoundException, IOException {
		if (Util.isClassFile(path.getPath())) {
		CFFormat cff = new CFFormat(path);
		add(cff, path.getPath());
		}
	}


	/*
	 * add by file path
	 */
	public synchronized void addFile(String path) throws FileNotFoundException, IOException {
		CFFormat cff = new CFFormat(path);
		add(cff, path);
	}

	public synchronized void addZip(String path) throws IOException {
		ZipFile zipFile = new ZipFile(path);
		// System.out.println(_zipFile);
		for (Enumeration e = zipFile.entries(); e.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) e.nextElement();
			String name = entry.getName();
			if (!entry.isDirectory() && Util.isClassFile(name)) {
				InputStream in = zipFile.getInputStream(entry);
				CFFormat cff = new CFFormat(in);
				// TODO: what is a better char than '!'?
				add(cff, path + '!' + name);
			}
		}
		zipFile.close();
	}
	public synchronized void addDir(File dir) throws FileNotFoundException, IOException {
		if(dir.isDirectory()) {
			for(File f: dir.listFiles()) {
				addDir(f);
			}
		} else {
			add(dir);
		}
	}
	public synchronized void addDir(String dir) throws FileNotFoundException, IOException {
		addDir(new File(dir));
	}
	/*
	 * all get methods below are not synchronized because both registories are thread safe
	 */
	public Map<String, Set<String>> getUriRegistory() {
		return this.uriRegistory;
	}
	public Map<String, CFFormat> getCFFormatRegistory() {
		return this.cffRegistory;
	}
	public ClassInfo getClassInfo(String classPath) {
		if(!uriRegistory.containsKey(classPath)) {
			return null;
		}
		String filePath = uriRegistory.get(classPath).iterator().next();
		CFFormat cff = cffRegistory.get(classPath+Symbol+filePath);
		return new ClassInfo(cff, filePath);
	}
}
