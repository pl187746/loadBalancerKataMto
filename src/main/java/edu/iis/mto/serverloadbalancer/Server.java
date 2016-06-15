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

	public void addVm(Vm vm) {
		currentLoadPercenatge += 100.0 * vm.getSize() / capacity;		
	}

}
