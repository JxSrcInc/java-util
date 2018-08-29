package jxsource.util.folder.search.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher {

//	final String NotAlphaNumber = "[^a-zA-Z0-9]";
	private Pattern replace;
	private Pattern display;
	private boolean word;
	private String wordValue;
	
	private RegexMatcher() {
	}
	public String getReplacePattern() {
		return replace.pattern();
	}
	public String getDisplayPattern() {
		return display.pattern();
	}
	public List<String> find(String text) {
		Matcher matcher = display.matcher(text);
		List<String> matches = new ArrayList<>();
		while(matcher.find()) {
			matches.add(matcher.group(1) + "#>"+
					matcher.group(2) + "<#" +
					matcher.group(3));
		}
		return matches;
	}
	public String replace(String text, String replacement) {
		if(word) {
			return myReplace(text, replacement);
		} else {
			Matcher matcher = replace.matcher(text);
			return matcher.replaceAll(replacement);
		}
	}
	private String myReplace(String text, String replacement) {
		StringBuilder sb = new StringBuilder();
		Matcher matcher = replace.matcher(text);
		int pos = 0;
		while(matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			if(start == 0) {
				// match at beginning
				sb.append(replacement);
				sb.append(text.charAt(end-1));
				pos = end;
			} else if(start == text.length()-wordValue.length()-1) {
				// match at end
				sb.append(text.substring(pos,start));
				sb.append(text.charAt(start));
				sb.append(replacement);
				pos = end;
			} else {
				// other
				sb.append(text.substring(pos,start));
				sb.append(text.charAt(start));
				sb.append(replacement);
				sb.append(text.charAt(end-1));
				pos = end;
			}
		}
		if(pos < text.length()) {
			sb.append(text.substring(pos));
		}
		return sb.toString();
	}
	public static class RegexUtilBuilder {
		private boolean word;
		private int preLen = 10;
		private int postLen = 10;
		
		public RegexUtilBuilder setWord(boolean word) {
			this.word = word;
			return this;
		}
		public RegexUtilBuilder setPreLen(int preLen) {
			this.preLen = preLen;
			return this;
		}
		public RegexUtilBuilder setPostLen(int postLen) {
			this.postLen = postLen;
			return this;
		}

		public RegexMatcher build(String regex) {
			RegexMatcher matcher =  new RegexMatcher();
			if(word) {
//				String pWord = "(^"+regex+"[^\\w])"+
//						"|([^\\w]"+regex+"[^\\w])"+
//						"|([^\\w]"+regex+"$)";
				String pWord = "(^|[^\\w])"+regex+"([^\\w]|$)";
				matcher.replace = Pattern.compile(pWord);
				matcher.word = true;
				matcher.wordValue = regex;
				String pattern = "(^|.{0,"+Integer.toString(preLen)+"}[^\\w])"+
						"((?i)"+regex+")"+
						"([^\\w].{0,"+Integer.toString(postLen)+"}|$)";
				matcher.display = Pattern.compile(pattern);				
			} else {
				matcher.replace = Pattern.compile(regex);
				String pattern = "(^|.{0,"+Integer.toString(preLen)+"})((?i)"+
						regex+")(.{0,"+Integer.toString(postLen)+"}|$)";
				matcher.display = Pattern.compile(pattern);				
			}			
			return matcher;
		}
	}
	
	public static RegexUtilBuilder builder() {
		return new RegexUtilBuilder();
	}
	
//	public List<String> getRegexMatches(String regex, String text) {
//		return getRegexMatches(regex, text, 10, 10);
//	}
//	public List<String> getRegexMatches(String regex, String text, 
//			int preLen, int postLen) {
//		String pattern = "(^|.{0,"+Integer.toString(preLen)+"})((?i)"+
//			regex+")(.{0,"+Integer.toString(postLen)+"}|$)";
//		Pattern p = Pattern.compile(pattern);
//		Matcher matcher = p.matcher(text);
////		System.out.println(p.pattern());
//		List<String> matches = new ArrayList<>();
//		while(matcher.find()) {
//			matches.add(matcher.group(1) + " ->"+
//					matcher.group(2) + "<- " +
//					matcher.group(3));
//		}
//		return matches;
//	}
//
}
