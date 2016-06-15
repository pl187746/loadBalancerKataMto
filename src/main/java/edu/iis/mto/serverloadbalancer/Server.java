package edu.iis.mto.serverloadbalancer;

public class Server {

	private static final double MAXIMUM_LOAD = 100.0;
	private int capacity;
	private double currentLoadPercenatge;

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercenatge;
	}

	public void addVm(Vm vm) {
		currentLoadPercenatge += MAXIMUM_LOAD * vm.getSize() / capacity;		
	}

}
