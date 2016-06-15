package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {

	private static final double EPSILON = 0.01;
	private double expectedCurrentLoadPercenatge;

	public CurrentLoadPercentageMatcher(double expectedCurrentLoadPercenatge) {
		this.expectedCurrentLoadPercenatge = expectedCurrentLoadPercenatge;
	}

	public void describeTo(Description description) {
		description.appendText("a server with current load percentage of ").appendValue(expectedCurrentLoadPercenatge);
	}
	
	@Override
	protected void describeMismatchSafely(Server server, Description description) {
		description.appendText("was a server with current load percentage of ").appendValue(server.getCurrentLoadPercentage());
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return doublesAreEqual(server.getCurrentLoadPercentage(), expectedCurrentLoadPercenatge);
	}

	private boolean doublesAreEqual(double d1, double d2) {
		return d1 == d2 || Math.abs(d1 - d2) < EPSILON;
	}

	public static CurrentLoadPercentageMatcher hasCurrentLoadPercentageOf(double expectedCurrentLoadPercenatge) {
		return new CurrentLoadPercentageMatcher(expectedCurrentLoadPercenatge);
	}

}
