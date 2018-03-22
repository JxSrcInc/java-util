package jxsource.util.cl.test.util;

import java.util.List;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class SetMatcher extends TypeSafeMatcher<String> {

	    private final Set<String> regex;

	    public SetMatcher(final Set<String> regex) {
	        this.regex = regex;
	    }

	    @Override
	    public void describeTo(final Description description) {
	        description.appendText("matches regular expression=`" + regex + "`");
	    }

		@Override
	    public boolean matchesSafely(final String string) {
	        return regex.contains(string);
	    }


	     // matcher method you can call on this matcher class
	    public static SetMatcher matchesSet(final Set<String> regex) {
	        return new SetMatcher(regex);
	    }

}
