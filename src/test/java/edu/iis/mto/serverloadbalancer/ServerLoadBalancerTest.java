package edu.iis.mto.serverloadbalancer;

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
	public void balancingServerWithNoVms_serverStaysEmpty() {
		Server theServer = a(server().withCapacity(1));
		
		balancing(aServerListWith(theServer), anEmptyListOfVms());
		
		assertThat(theServer, CurrentLoadPercentageMatcher.hasCurrentLoadOf(0.0d));
	}
	
	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
		Server theServer = a(server().withCapacity(1));
		Vm theVm = a(vm().ofSize(1));
		
		balancing(aServerListWith(theServer), aVmListWith(theVm));
		
		assertThat(theServer, CurrentLoadPercentageMatcher.hasCurrentLoadOf(100.0d));
		assertThat("server should contain the vm", theServer.contains(theVm));
	}

	private Vm[] aVmListWith(Vm... vms) {
		return vms;
	}

	private Vm a(VmBuilder builder) {
		return builder.build();
	}

	private VmBuilder vm() {
		return new VmBuilder();
	}

	private void balancing(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
		
	}

	private Vm[] anEmptyListOfVms() {
		return new Vm[0];
	}

	private Server[] aServerListWith(Server... servers) {
		return servers;
	}

	private Server a(ServerBuilder builder) {
		return builder.build();
	}

}
