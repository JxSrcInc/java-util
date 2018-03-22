package jxsource.util.cl.cff;

public class BaseTypeMapper {
	public static final char B = 'B'; // byte
	public static final char C = 'C'; // char
	public static final char D = 'D'; // double
	public static final char F = 'F'; // float
	public static final char I = 'I'; // int
	public static final char J = 'J'; // long
	public static final char ClassType = 'L';
	public static final char S = 'S'; // short
	public static final char Z = 'Z'; // boolean
	public static final char ArrayType = '[';
	
	// additional
	public static final char V = 'V'; // void
	
	public static final String BYTE = "byte";
	public static final String CHAR = "char";
	public static final String DOUBLE = "double";
	public static final String FLOAT = "float";
	public static final String INT = "int";
	public static final String LONG = "long";
	public static final String SHORT = "short";
	public static final String BOOLEAN = "boolean";
	public static final String VOID = "void";
	
	public static String get(String s) {
		return get(s.charAt(0));
	}
	public static String get(char c) {
		switch(c) {
		case B:
			return BYTE;
		case C:
			return CHAR;
		case D:
			return DOUBLE;
		case F:
			return FLOAT;
		case I:
			return INT;
		case J:
			return LONG;
		case S:
			return SHORT;
		case Z:
			return BOOLEAN;
		case V:
			return VOID;
			default:
				throw new RuntimeException("Invalid BaseTYpe: "+c);
		}
	}
	public static boolean isBaseType(final String type) {
		switch(type) {
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FLOAT:
		case INT:
		case LONG:
		case SHORT:
		case BOOLEAN:
		case VOID:
			return true;
		default:
			return false;
		}
		
	}
}
