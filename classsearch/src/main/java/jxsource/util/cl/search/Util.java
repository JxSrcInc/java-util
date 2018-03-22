package jxsource.util.cl.search;

import java.util.ArrayList;
import java.util.List;

import jxsource.util.cl.cff.Method_Info;

public class Util {
	public static boolean isClassFile(String path) {
		int i = path.lastIndexOf(".class");
		return i == path.length() - 6;
	}
}
