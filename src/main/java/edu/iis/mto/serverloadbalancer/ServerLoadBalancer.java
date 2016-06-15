package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for (Vm vm : vms) {
			addToLessLoadedServer(servers, vm);
		}
	}

	private void addToLessLoadedServer(Server[] servers, Vm vm) {
		List<Server> capableServevrs = new ArrayList<Server>();
		for (Server server : servers) {
			if (server.canFit(vm)) {
				capableServevrs.add(server);
			}
		}
		Server lessLoadedServer = extractLessLoadedServer(capableServevrs);
		if (lessLoadedServer != null) {
			lessLoadedServer.addVm(vm);
		}
	}

	private Server extractLessLoadedServer(List<Server> capableServevrs) {
		Server lessLoadedServer = null;
		for (Server server : capableServevrs) {
			if (lessLoadedServer == null
					|| server.getCurrentLoadPercentage() < lessLoadedServer.getCurrentLoadPercentage())
				lessLoadedServer = server;
		}
		return lessLoadedServer;
	}
}
