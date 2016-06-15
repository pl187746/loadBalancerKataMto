package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for(Vm vm : vms) {
			addToLessLoadedServer(servers, vm);
		}
	}

	private void addToLessLoadedServer(Server[] servers, Vm vm) {
		Server lessLoadedServer = extractLessLoadedServer(servers);
		lessLoadedServer.addVm(vm);
	}

	private Server extractLessLoadedServer(Server[] servers) {
		Server lessLoadedServer = null;
		for(Server server : servers) {
			if(lessLoadedServer == null || server.getCurrentLoadPercentage() < lessLoadedServer.getCurrentLoadPercentage())
				lessLoadedServer = server;
		}
		return lessLoadedServer;
	}
}
