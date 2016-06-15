package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class VmCountMatcher extends TypeSafeMatcher<Server> {

	private int expectedVmCount;

	public VmCountMatcher(int expectedVmCount) {
		this.expectedVmCount = expectedVmCount;
	}

	public void describeTo(Description description) {
		description.appendText("a server with vm count of ").appendValue(expectedVmCount);
	}
	
	@Override
	protected void describeMismatchSafely(Server server, Description description) {
		description.appendText("was a server with vm count of ").appendValue(server.getVmCount());
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return server.getVmCount() == expectedVmCount;
	}

	public static VmCountMatcher hasVmCountOf(int expectedVmCount) {
		return new VmCountMatcher(expectedVmCount);
	}

}
