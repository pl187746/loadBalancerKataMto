package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
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
	public void balancingServerWithNoVms_serverStaysEmpty() {
		Server theServer = a(server().withCapacity(1));
		
		balancing(aServerListWith(theServer), anEmptyListOfVms());
		
		assertThat(theServer, hasCurrentLoadOf(0.0d));
	}
	
	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
		Server theServer = a(server().withCapacity(1));
		Vm theVm = a(vm().ofSize(1));
		
		balancing(aServerListWith(theServer), aVmListWith(theVm));
		
		assertThat(theServer, hasCurrentLoadOf(100.0d));
		assertThat("server should contain the vm", theServer.contains(theVm));
	}
	
	@Test
	public void balancingOneServerWithTenSlotCapacity_andOneSlotVm_fillsTheServerWithTenPersent() {
		Server theServer = a(server().withCapacity(10));
		Vm theVm = a(vm().ofSize(1));
		
		balancing(aServerListWith(theServer), aVmListWith(theVm));
		
		assertThat(theServer, hasCurrentLoadOf(10.0d));
		assertThat("server should contain the vm", theServer.contains(theVm));
	}
	
	@Test
	public void balancingAServerWithEnoughRoom_getsFilledWithAllVms() {
		Server theServer = a(server().withCapacity(10));
		Vm theFirstVm = a(vm().ofSize(1));
		Vm theSecondVm = a(vm().ofSize(1));
		
		balancing(aServerListWith(theServer), aVmListWith(theFirstVm));
		balancing(aServerListWith(theServer), aVmListWith(theSecondVm));
		
		assertThat(theServer, hasVmCountOf(2));
		assertThat("server should contain the first vm", theServer.contains(theSecondVm));
		assertThat("server should contain the second vm", theServer.contains(theSecondVm));
	}

	private Matcher<? super Server> hasVmCountOf(int vmCount) {
		return new VmCountMatcher(vmCount);
	}

	private static Vm[] aVmListWith(Vm... vms) {
		return vms;
	}

	private static <T> T a(Builder<T> builder) {
		return builder.build();
	}

	private static void balancing(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
		
	}

	private static Vm[] anEmptyListOfVms() {
		return new Vm[0];
	}

	private static Server[] aServerListWith(Server... servers) {
		return servers;
	}

}
