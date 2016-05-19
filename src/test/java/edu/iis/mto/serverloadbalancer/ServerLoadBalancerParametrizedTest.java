package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ServerLoadBalancerParametrizedTest extends ServerLoadBalancerBaseTest{
	
	private int serverCapacity;
	private int vmSize;
	private double expectedLoadPercentage;
	
	public ServerLoadBalancerParametrizedTest(int serverCapacity, int vmSize, double expectedLoadPercentage) {
		super();
		this.serverCapacity = serverCapacity;
		this.vmSize = vmSize;
		this.expectedLoadPercentage = expectedLoadPercentage;
	}
	
	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
			{ 1, 1, 100.0 },
			{ 2, 1, 50.0 },
			{ 2, 2, 100.0 },
			{ 3, 1, 100.0/3.0 },
			{ 3, 2, 200.0/3.0 },
			{ 3, 3, 100.0 }
		});
	}
	
	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
		Server theServer = a(server().withCapacity(serverCapacity));
		Vm theVm = a(vm().ofSize(vmSize));
		balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(expectedLoadPercentage));
		assertThat("the server should contain vm", theServer.contains(theVm));
	}
	
	
}
