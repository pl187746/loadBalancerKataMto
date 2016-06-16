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
		int initLoadVmSize = (int) (initialLoadPercentage * capacity / 100.0);
		if(initLoadVmSize > 0) {
			server.addVm(vm().ofSize(initLoadVmSize).build());
		}
		return server;
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public ServerBuilder withInitialLoadOf(double initialLoadPercentage) {
		this.initialLoadPercentage = initialLoadPercentage;
		return this;
	}
}
