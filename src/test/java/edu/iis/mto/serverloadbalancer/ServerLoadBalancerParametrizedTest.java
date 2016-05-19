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
	
	private static class ServerParamsBuilder implements Builder<ServerParams> {

		private int capacity;
		private double loadAfterBalance;
		
		public ServerParams build() {
			return new ServerParams(capacity, loadAfterBalance);
		}

		public ServerParamsBuilder withCapacity(int capacity) {
			this.capacity = capacity;
			return this;
		}

		public ServerParamsBuilder withLoadAfterBalance(double loadAfterBalance) {
			this.loadAfterBalance = loadAfterBalance;
			return this;
		}
		
	}
	
	private static ServerParamsBuilder serverParams() {
		return new ServerParamsBuilder();
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
	
	private static class ServerVmPairBuilder implements Builder<ServerVmPair> {
		private int serverIdx;
		private int vmIdx;
		public ServerVmPair build() {
			return new ServerVmPair(serverIdx, vmIdx);
		}
		public ServerVmPairBuilder withServerIdx(int serverIdx) {
			this.serverIdx = serverIdx;
			return this;
		}
		public ServerVmPairBuilder withVmIdx(int vmIdx) {
			this.vmIdx = vmIdx;
			return this;
		}
	}
	
	private static ServerVmPairBuilder serverVmPair() {
		return new ServerVmPairBuilder();
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
	
	private static int[] vmsOfSizes(int... sizes) {
		return sizes;
	}
	
	private static ServerParams[] serverParamsList(ServerParams... serverParams) {
		return serverParams;
	}
	
	private static ServerVmPair[] serverVmPairList(ServerVmPair... svp) {
		return svp;
	}

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
			{
				serverParamsList(a(serverParams().withCapacity(4).withLoadAfterBalance(75.0)), a(serverParams().withCapacity(6).withLoadAfterBalance(66.66))),
				vmsOfSizes( 1, 4, 2 ),
				serverVmPairList(a(serverVmPair().withServerIdx(0).withVmIdx(0)), a(serverVmPair().withServerIdx(1).withVmIdx(1)), a(serverVmPair().withServerIdx(0).withVmIdx(2)))
			},
			{
				serverParamsList(a(serverParams().withCapacity(6).withLoadAfterBalance(50.0)), a(serverParams().withCapacity(4).withLoadAfterBalance(100.0))),
				vmsOfSizes( 1, 4, 2 ),
				serverVmPairList(a(serverVmPair().withServerIdx(0).withVmIdx(0)), a(serverVmPair().withServerIdx(1).withVmIdx(1)), a(serverVmPair().withServerIdx(0).withVmIdx(2)))
			},
			{
				serverParamsList(a(serverParams().withCapacity(2).withLoadAfterBalance(100.0)), a(serverParams().withCapacity(4).withLoadAfterBalance(100.0))),
				vmsOfSizes( 4, 2 ),
				serverVmPairList(a(serverVmPair().withServerIdx(0).withVmIdx(1)), a(serverVmPair().withServerIdx(1).withVmIdx(0)))
			},
			{
				serverParamsList(a(serverParams().withCapacity(2).withLoadAfterBalance(100.0)), a(serverParams().withCapacity(4).withLoadAfterBalance(100.0)), a(serverParams().withCapacity(6).withLoadAfterBalance(66.666))),
				vmsOfSizes( 4, 4, 1, 1 ),
				serverVmPairList(a(serverVmPair().withServerIdx(0).withVmIdx(3)), a(serverVmPair().withServerIdx(1).withVmIdx(0)), a(serverVmPair().withServerIdx(2).withVmIdx(1)))
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
