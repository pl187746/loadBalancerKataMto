package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for (Vm vm : vms) {
			Server lessLoadedServer = findLessLoadedServer(servers);
			lessLoadedServer.addVm(vm);
		}

	}

	private Server findLessLoadedServer(Server[] servers) {
		Server lessLoadedServer = null;
		for(Server server : servers) {
			if(lessLoadedServer == null || server.currentLoadPecentage < lessLoadedServer.currentLoadPecentage) {
				lessLoadedServer = server;
			}
		}
		return lessLoadedServer;
	}

}
