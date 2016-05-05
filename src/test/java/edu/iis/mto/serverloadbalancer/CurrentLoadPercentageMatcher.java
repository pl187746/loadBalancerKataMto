package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {

	private static final double EPSILON = 0.001;
	private double expectedLoadPercentage;

	public CurrentLoadPercentageMatcher(double expectedLoadPercentage) {
		this.expectedLoadPercentage = expectedLoadPercentage;
	}

	public void describeTo(Description description) {
		description.appendText("a server with load percentage of ").appendValue(expectedLoadPercentage);		
	}
	
	@Override
	protected void describeMismatchSafely(Server server, Description description) {
		description.appendText("was a server with load percentage of ").appendValue(server.getCurrentLoadPercentage());
	}
	

	@Override
	protected boolean matchesSafely(Server server) {
		return doublesAreEqual(expectedLoadPercentage, server.getCurrentLoadPercentage());
	}

	private boolean doublesAreEqual(double a, double b) {
		return b == a || Math.abs(b - a) < EPSILON;
	}

}
