package edu.iis.mto.serverloadbalancer;

public class ServerBuilder implements Builder<Server> {
	
	private int capacity;
	private double currentLoadPercentage;

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
		int initVmSize = (int)(currentLoadPercentage * capacity / Server.MAXIMUM_LOAD);
		if(initVmSize > 0)
			server.addVm(VmBuilder.vm().ofSize(initVmSize).build());
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public ServerBuilder withCurrentLoadPercentageOf(double currentLoadPercentage) {
		this.currentLoadPercentage = currentLoadPercentage;
		return this;
	}
	
}
