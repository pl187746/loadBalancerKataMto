package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for (Vm vm : vms) {
			addVmToCapableLessLoadedServer(servers, vm);
		}

	}

	private void addVmToCapableLessLoadedServer(Server[] servers, Vm vm) {
		List<Server> capableServers = findCapableServers(servers, vm);
		addVmToLessLoadedServer(vm, capableServers);
	}

	private void addVmToLessLoadedServer(Vm vm, List<Server> capableServers) {
		Server lessLoadedServer = findLessLOadedServer(capableServers);
		if (lessLoadedServer != null) {
			lessLoadedServer.addVm(vm);
		}
	}

	private List<Server> findCapableServers(Server[] servers, Vm vm) {
		List<Server> capableServers = new ArrayList<Server>();
		for (Server server : servers) {
			if (server.canFit(vm)) {
				capableServers.add(server);
			}
		}
		return capableServers;
	}

	private Server findLessLOadedServer(List<Server> capableServers) {
		Server lessLoadedServer = null;
		for (Server server : capableServers) {
			if (lessLoadedServer == null || server.currentLoadPecentage < lessLoadedServer.currentLoadPecentage) {
				lessLoadedServer = server;
			}
		}
		return lessLoadedServer;
	}

}
