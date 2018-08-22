package jxsource.util.folder.search.hamcrestMatcher;

import java.util.Arrays;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ExcludeStringMatcher extends BaseMatcher<String> {

	private String[] matchArray;
	
	public ExcludeStringMatcher(String[] matchValue) {
		this.matchArray = matchValue;
	}
    public boolean matches(final Object item) {
    	if(item == null) {
    		return false;
    	}
    	for(String value: matchArray) {
    		if(item.toString().contains(value)) {
    			return false;
    		}
    	}
        return true;
     }
  
     public void describeTo(final Description description) {
        description.appendText("should not return ").appendValue(Arrays.asList(matchArray));
     }

}
