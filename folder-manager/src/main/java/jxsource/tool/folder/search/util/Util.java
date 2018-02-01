package jxsource.tool.folder.search.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import jxsource.tool.folder.file.JFile;

public class Util {
	public static final String[] archiveTypes = new String[] {
			"jar", "zip"
	};
	public static boolean isArchive(JFile f) {
		for(int i=0; i<archiveTypes.length; i++) {
			if(archiveTypes[i].equals(f.getExt().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	public static String[] toArray(String src) {
		String[] array = src.split(",");
		for(int i=0; i<array.length; i++) {
			array[i] = array[i].trim();
		}
		return array;
	}
	// not thread safe
	public static String getContent(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		Reader r = new InputStreamReader(in);
		char[] cbuf = new char[1024*8];
		int i = 0;
		while((i=r.read(cbuf)) != -1) {
			sb.append(cbuf,0,i);
		}
		return sb.toString();
	}
}
