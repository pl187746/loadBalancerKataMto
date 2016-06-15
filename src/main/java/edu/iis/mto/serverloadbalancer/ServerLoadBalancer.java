package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for (Vm vm : vms) {
			addToCapableLessLoadedServer(servers, vm);
		}
	}

	private void addToCapableLessLoadedServer(Server[] servers, Vm vm) {
		List<Server> capableServers = findCapableServers(servers, vm);
		addToLessLoadedServer(vm, capableServers);
	}

	private void addToLessLoadedServer(Vm vm, List<Server> capableServers) {
		Server lessLoadedServer = extractLessLoadedServer(capableServers);
		if (lessLoadedServer != null) {
			lessLoadedServer.addVm(vm);
		}
	}

	private List<Server> findCapableServers(Server[] servers, Vm vm) {
		List<Server> capableServevrs = new ArrayList<Server>();
		for (Server server : servers) {
			if (server.canFit(vm)) {
				capableServevrs.add(server);
			}
		}
		return capableServevrs;
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
