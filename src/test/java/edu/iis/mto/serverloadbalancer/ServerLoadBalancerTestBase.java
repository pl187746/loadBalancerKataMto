package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancerTestBase {

	public ServerLoadBalancerTestBase() {
		super();
	}

	protected void balance(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
	}

	protected Vm[] aListOfVmsWith(Vm... vms) {
		return vms;
	}

	protected Vm[] anEmptyListOfVms() {
		return new Vm[0];
	}

	protected Server[] aListOfServersWith(Server... servers) {
		return servers;
	}

	protected <T> T a(Builder<T> builder) {
		return builder.build();
	}

}