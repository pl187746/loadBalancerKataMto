package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
	
	@Test
	public void balancingServerWithOneSlotCapacity_andOneSlotVm_fillsServerWithTheVm() {
		Server theServer = a(server().withCapacity(1));
		Vm theVm = a(vm().ofSize(1));

		balance(aServerListWith(theServer), aVmListWith(theVm));

		assertThat(theServer, hasCurrentLoadPercentageOf(100.0));
	}

	private Vm a(VmBuilder builder) {
		return builder.build();
	}

	private VmBuilder vm() {
		return new VmBuilder();
	}

	private Vm[] aVmListWith(Vm... vms) {
		return vms;
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

}
