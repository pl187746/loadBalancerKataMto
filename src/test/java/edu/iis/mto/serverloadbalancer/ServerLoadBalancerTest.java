package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerVmsCountMatcher.hasVmsCountOf;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void balancingAServer_noVms_serverStaysEmpty() {
		Server theServer = a(server().withCapacity(1));

		balance(aListOfServersWith(theServer), anEmptyListOfVms());

		assertThat(theServer, hasLoadPercentageOf(0.0d));
	}

	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
		Server theServer = a(server().withCapacity(1));
		Vm theVm = a(vm().ofSize(1));
		balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(100.0d));
		assertThat("the server should contain vm", theServer.contains(theVm));
	}

	@Test 
	public void balancingOneServerWithTenSlotsCapacity_andOneSlotVm_fillTheServerWithTenPercent(){
		Server theServer = a(server().withCapacity(10));
		Vm theVm = a(vm().ofSize(1));
		balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(10.0d));
		assertThat("the server should contain vm", theServer.contains(theVm));
		
	}
	
	@Test
	public void balancingAServerWithEnoughRoom_getsFilledWithAllVms(){
		Server theServer = a(server().withCapacity(100));
		Vm theFirstVm = a(vm().ofSize(1));
		Vm theSecondVm = a(vm().ofSize(1));
		balance(aListOfServersWith(theServer), aListOfVmsWith(theFirstVm, theSecondVm));

		assertThat(theServer, hasVmsCountOf(2));
		assertThat("the server should contain vm", theServer.contains(theFirstVm));
		assertThat("the server should contain vm", theServer.contains(theSecondVm));

	}
	
	@Test
	public void aVm_shouldBeBalanced_onLEssLOadedServerFirst() {
		Server lessLoadedServer = a(server().withCapacity(10).withInitialLoadOf(40.0));
		Server moreLoadedServer = a(server().withCapacity(10).withInitialLoadOf(60.0));
		Vm theVm = a(vm().ofSize(1));
		balance(aListOfServersWith(moreLoadedServer, lessLoadedServer), aListOfVmsWith(theVm));

		assertThat("the less loaded server should contain vm", lessLoadedServer.contains(theVm));
		assertThat("the more loaded server should not contain vm", !moreLoadedServer.contains(theVm));		
	}
	
	@Test
	public void balanceAServerWithNoEnoughRoom_shouldNotBeFilledWithAVm() {
		Server theServer = a(server().withCapacity(2));
		Vm theVm = a(vm().ofSize(4));
		balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

		assertThat("the server should not contain vm", !theServer.contains(theVm));
	}
	
	@Test
	public void balanceServersAndVms() {
		Server server1 = a(server().withCapacity(6));
		Server server2 = a(server().withCapacity(4));
		
		Vm vm1 = a(vm().ofSize(2));
		Vm vm2 = a(vm().ofSize(4));
		Vm vm3 = a(vm().ofSize(2));
		
		balance(aListOfServersWith(server1, server2), aListOfVmsWith(vm1, vm2, vm3));
		
		assertThat("the server 1 should not contain vm 1", server1.contains(vm1));
		assertThat("the server 2 should not contain vm 2", server2.contains(vm2));
		assertThat("the server 1 should not contain vm 3", server1.contains(vm3));
	}
	

	private void balance(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
	}

	private Vm[] aListOfVmsWith(Vm... vms) {
		return vms;
	}

	private Vm[] anEmptyListOfVms() {
		return new Vm[0];
	}

	private Server[] aListOfServersWith(Server... servers) {
		return servers;
	}

	private <T> T a(Builder<T> builder) {
		return builder.build();
	}
}
