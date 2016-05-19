package edu.iis.mto.serverloadbalancer;

class ServerParams {
	public int capacity;
	public double loadAfterBalance;
	public ServerParams(int capacity, double loadAfterBalance) {
		super();
		this.capacity = capacity;
		this.loadAfterBalance = loadAfterBalance;
	}
}