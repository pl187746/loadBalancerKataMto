package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;

public class ServerBuilder implements Builder<Server> {

	private int capacity;
	private double initialServerLoad;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		Server server = new Server(capacity);
		addInitalLoad(server);
		return server;
	}

	private void addInitalLoad(Server server) {
		int initialVmSize = (int)(initialServerLoad / Server.MAXIMUM_LOAD * capacity);
		if(initialVmSize > 0)
			server.addVm(vm().ofSize(initialVmSize).build());
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public ServerBuilder withInitialLoadOf(double initialServerLoad) {
		this.initialServerLoad = initialServerLoad;
		return this;
	}

}
