package edu.iis.mto.serverloadbalancer;

public class Server {

	private int capacity;
	private double currentLoadPercenatge;

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercenatge;
	}

}
