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
		int initVmSize = (int)(currentLoadPercentage * capacity / 100.0);
		if(initVmSize > 0)
			server.addVm(VmBuilder.vm().ofSize(initVmSize).build());
		return server;
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public ServerBuilder withCurrentLoadPercentageOf(double currentLoadPercentage) {
		this.currentLoadPercentage = currentLoadPercentage;
		return this;
	}
	
}
