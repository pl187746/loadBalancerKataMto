package edu.iis.mto.serverloadbalancer;

class ServerParamsBuilder implements Builder<ServerParams> {

	private int capacity;
	private double loadAfterBalance;
	
	public ServerParams build() {
		return new ServerParams(capacity, loadAfterBalance);
	}

	public ServerParamsBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public ServerParamsBuilder withLoadAfterBalance(double loadAfterBalance) {
		this.loadAfterBalance = loadAfterBalance;
		return this;
	}

	static ServerParamsBuilder serverParams() {
		return new ServerParamsBuilder();
	}
	
}