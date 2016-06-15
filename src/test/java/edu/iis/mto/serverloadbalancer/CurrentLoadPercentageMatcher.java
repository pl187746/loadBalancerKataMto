package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {

	private double expectedCurrentLoadPercenatge;

	public CurrentLoadPercentageMatcher(double expectedCurrentLoadPercenatge) {
		this.expectedCurrentLoadPercenatge = expectedCurrentLoadPercenatge;
	}

	public void describeTo(Description descripition) {
		descripition.appendText("a server with current load percentage of ").appendValue(expectedCurrentLoadPercenatge);
	}

	@Override
	protected boolean matchesSafely(Server server) {
		double serverLoad = server.getCurrentLoadPercentage();
		return serverLoad == expectedCurrentLoadPercenatge || Math.abs(serverLoad - expectedCurrentLoadPercenatge) < 0.01;
	}

}
