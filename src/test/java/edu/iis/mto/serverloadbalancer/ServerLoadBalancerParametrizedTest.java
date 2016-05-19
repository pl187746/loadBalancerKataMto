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
public class ServerLoadBalancerParametrizedTest extends ServerLoadBalancerTestBase {
	
	private int[] serverCapacities;
	private double[] serverLoadsAfterBalance;
	private int[] vmSizes;
	private int[][] serverVmPairsAfterBalance;
	
	public ServerLoadBalancerParametrizedTest(int[] serverCapacities, double[] serverLoadsAfterBalance, int[] vmSizes,
			int[][] serverVmPairsAfterBalance) {
		super();
		this.serverCapacities = serverCapacities;
		this.serverLoadsAfterBalance = serverLoadsAfterBalance;
		this.vmSizes = vmSizes;
		this.serverVmPairsAfterBalance = serverVmPairsAfterBalance;
	}

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
			{
				new int[]{ 4, 6 },
				new double[]{ 75.0, 66.66 },
				new int[]{ 1, 4, 2 },
				new int[][]{ { 0, 0 }, { 1, 1 }, { 0, 2 } }
			}
		});
	}

	@Test 
	public void balance_serversAndVms() {
        Server[] servers = new Server[serverCapacities.length];
        for(int i = 0; i < servers.length; ++i)
        	servers[i] = a(server().withCapacity(serverCapacities[i]));
        
        Vm[] vms = new Vm[vmSizes.length];
        for(int i = 0; i < vms.length; ++i)
        	vms[i] = a(vm().ofSize(vmSizes[i]));
        
        balance(servers, vms);
        
        for(int i = 0; i < serverVmPairsAfterBalance.length; ++i) {
        	int serverIdx = serverVmPairsAfterBalance[i][0];
        	int vmIdx = serverVmPairsAfterBalance[i][1];
        	String failMsg = "The server " + (serverIdx + 1) + " should contain the vm " + (vmIdx + 1);
        	assertThat(failMsg, servers[serverIdx].contains(vms[vmIdx]));
        }
        
        for(int i = 0; i < serverLoadsAfterBalance.length; ++i)
        	assertThat(servers[i], hasLoadPercentageOf(serverLoadsAfterBalance[i]));
	}
	
}
