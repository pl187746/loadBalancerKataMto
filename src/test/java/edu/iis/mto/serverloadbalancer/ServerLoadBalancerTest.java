package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void balancingServer_noVm_serverStaysEmpty() {
		Server theServer = a(server().withCapacity(1));
		
		balance(aServerListWith(theServer), anEmptyListOfVms());
		
		assertThat(theServer, hasCurrentLoadPercentageOf(0.0));
	}

	private Matcher<? super Server> hasCurrentLoadPercentageOf(double expectedCurrentLoadPercenatge) {
				return new CurrentLoadPercentageMatcher(expectedCurrentLoadPercenatge);
	}

	private Server a(ServerBuilder builder) {
		return builder.build();
	}

	private void balance(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);		
	}

	private Vm[] anEmptyListOfVms() {
		return new Vm[0];
	}

	private Server[] aServerListWith(Server... servers) {
		return servers;
	}

	private ServerBuilder server() {
		return new ServerBuilder();
	}

}
