package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for (Vm vm : vms) {
			List<Server> capableServers = new ArrayList<Server>();
			for (Server server : servers) {
				if (server.canFit(vm)) {
					capableServers.add(server);
				}
			}
			Server lessLoadedServer = findLessLOadedServer(capableServers);
			if (lessLoadedServer != null) {
				lessLoadedServer.addVm(vm);
			}
		}

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
