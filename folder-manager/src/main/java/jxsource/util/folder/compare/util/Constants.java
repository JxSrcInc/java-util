package jxsource.util.folder.compare.util;

import java.text.SimpleDateFormat;

public class Constants {
	public static String srcSymbol = "+";
	public static String cmpSymbol = "-";
	public static String bothSymbol = srcSymbol+cmpSymbol;
	// comparePath - diffType[srcSymbol:srcValue cmpSymbol:cmpValue]
	public static String diffPrintFormat = "%s -> %s[%s:%s %s:%s]\n";
	// comparePath - type[<list of value>]
	public static String arrayPrintFormat = "%s -> %s[%s]\n";
	public static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
