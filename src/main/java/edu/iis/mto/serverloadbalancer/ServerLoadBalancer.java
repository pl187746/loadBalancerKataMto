package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for (Vm vm : vms) {
			Server lessLoadedServer = findLessLOadedServer(servers);
			lessLoadedServer.addVm(vm);
		}

	}

	private Server findLessLOadedServer(Server[] servers) {
		Server lessLoadedServer = null;
		for(Server server : servers) {
			if(lessLoadedServer == null || server.currentLoadPecentage < lessLoadedServer.currentLoadPecentage) {
				lessLoadedServer = server;
			}
		}
		return lessLoadedServer;
	}

}
