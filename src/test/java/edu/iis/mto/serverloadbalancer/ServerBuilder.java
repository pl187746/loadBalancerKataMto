package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;

public class ServerBuilder implements Builder<Server> {

	private int capacity;
	private double initialLoadPercentage;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		Server server = new Server(capacity);
		addInitialLoad(server);
		return server;
	}

	private void addInitialLoad(Server server) {
		int initLoadVmSize = (int) (initialLoadPercentage * capacity / Server.MAXIMUM_LOAD);
		if(initLoadVmSize > 0) {
			server.addVm(vm().ofSize(initLoadVmSize).build());
		}
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public ServerBuilder withInitialLoadOf(double initialLoadPercentage) {
		this.initialLoadPercentage = initialLoadPercentage;
		return this;
	}
}
