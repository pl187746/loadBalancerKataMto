package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class VmCountMatcher extends TypeSafeMatcher<Server> {

	private int vmCount;

	public VmCountMatcher(int vmCount) {
		super();
		this.vmCount = vmCount;
	}

	public void describeTo(Description description) {
		description.appendText("a server with vm count of ").appendValue(vmCount);
	}
	
	@Override
	protected void describeMismatchSafely(Server item, Description mismatchDescription) {
		mismatchDescription.appendText("was a server with vm count of ").appendValue(item.getVmCount());
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return server.getVmCount() == vmCount;
	}

	public static Matcher<? super Server> hasVmCountOf(int vmCount) {
		return new VmCountMatcher(vmCount);
	}

}
