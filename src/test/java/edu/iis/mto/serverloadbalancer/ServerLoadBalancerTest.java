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

		balancing(aServerListWith(theServer), anEmptyListOfVms());

		assertThat(theServer, hasLoadPercentageOf(0.0));
	}

	private Server a(ServerBuilder builder) {
		return builder.build();
	}

	private void balancing(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
	}

	private Server[] aServerListWith(Server... servers) {
		return servers;
	}

	private CurrentLoadPercentageMathcer hasLoadPercentageOf(double expectedLoadPercentage) {
		return new CurrentLoadPercentageMathcer(expectedLoadPercentage);
	}

	private Vm[] anEmptyListOfVms() {
		return new Vm[0];
	}

	private ServerBuilder server() {
		return new ServerBuilder();
	}

}
