package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		if (vms.length > 0) {
			servers[0].currentLoadPecentage = 100.0d * vms[0].size / servers[0].capacity;
		}
	}

}
