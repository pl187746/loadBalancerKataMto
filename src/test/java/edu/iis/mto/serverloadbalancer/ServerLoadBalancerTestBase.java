package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancerTestBase {

	public ServerLoadBalancerTestBase() {
		super();
	}

	protected static void balance(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
	}

	protected static Vm[] aListOfVmsWith(Vm... vms) {
		return vms;
	}

	protected static Vm[] anEmptyListOfVms() {
		return new Vm[0];
	}

	protected static Server[] aListOfServersWith(Server... servers) {
		return servers;
	}

	protected static <T> T a(Builder<T> builder) {
		return builder.build();
	}

}