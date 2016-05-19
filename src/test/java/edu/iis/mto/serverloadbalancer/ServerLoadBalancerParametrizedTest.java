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
	
	private static class ServerParams {
		public int capacity;
		public double loadAfterBalance;
		public ServerParams(int capacity, double loadAfterBalance) {
			super();
			this.capacity = capacity;
			this.loadAfterBalance = loadAfterBalance;
		}
	}
	
	private static class ServerVmPair {
		public int serverIdx;
		public int vmIdx;
		public ServerVmPair(int serverIdx, int vmIdx) {
			super();
			this.serverIdx = serverIdx;
			this.vmIdx = vmIdx;
		}
	}
	
	private ServerParams[] serverParams;
	private int[] vmSizes;
	private ServerVmPair[] serverVmPairsAfterBalance;
	

	public ServerLoadBalancerParametrizedTest(ServerParams[] serverParams, int[] vmSizes,
			ServerVmPair[] serverVmPairsAfterBalance) {
		super();
		this.serverParams = serverParams;
		this.vmSizes = vmSizes;
		this.serverVmPairsAfterBalance = serverVmPairsAfterBalance;
	}

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
			{
				new ServerParams[] { new ServerParams(4, 75.0), new ServerParams(6, 66.66) },
				new int[]{ 1, 4, 2 },
				new ServerVmPair[]{ new ServerVmPair( 0, 0 ), new ServerVmPair( 1, 1 ), new ServerVmPair( 0, 2 ) }
			},
			{
				new ServerParams[] { new ServerParams(6, 50.0), new ServerParams(4, 100.0) },
				new int[]{ 1, 4, 2 },
				new ServerVmPair[]{ new ServerVmPair( 0, 0 ), new ServerVmPair( 1, 1 ), new ServerVmPair( 0, 2) }
			},
			{
				new ServerParams[] { new ServerParams(2, 100.0), new ServerParams(4,100.0) },
				new int[]{ 4, 2 },
				new ServerVmPair[]{ new ServerVmPair( 0, 1 ), new ServerVmPair( 1, 0 ) }
			},
			{
				new ServerParams[] { new ServerParams(2, 100.0), new ServerParams(4, 100.0), new ServerParams(6, 66.666) },
				new int[]{ 4, 4, 1, 1 },
				new ServerVmPair[]{ new ServerVmPair( 0, 2 ), new ServerVmPair( 0, 3 ), new ServerVmPair( 1, 0 ), new ServerVmPair(2, 1) }
			}
		});
	}

	@Test 
	public void balance_serversAndVms() {
        Server[] servers = new Server[serverParams.length];
        for(int i = 0; i < servers.length; ++i)
        	servers[i] = a(server().withCapacity(serverParams[i].capacity));
        
        Vm[] vms = new Vm[vmSizes.length];
        for(int i = 0; i < vms.length; ++i)
        	vms[i] = a(vm().ofSize(vmSizes[i]));
        
        balance(servers, vms);
        
        for(int i = 0; i < serverVmPairsAfterBalance.length; ++i) {
        	int serverIdx = serverVmPairsAfterBalance[i].serverIdx;
        	int vmIdx = serverVmPairsAfterBalance[i].vmIdx;
        	String failMsg = "The server " + (serverIdx + 1) + " should contain the vm " + (vmIdx + 1);
        	assertThat(failMsg, servers[serverIdx].contains(vms[vmIdx]));
        }
        
        for(int i = 0; i < serverParams.length; ++i)
        	assertThat(servers[i], hasLoadPercentageOf(serverParams[i].loadAfterBalance));
	}
	
}
