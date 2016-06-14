package edu.iis.mto.serverloadbalancer;

public class Server {

	private int capacity;
	private double currentLoadPercentage;
	
	public Server(int capacity) {
		this.capacity = capacity;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}

	public boolean contains(Vm theVm) {
		return true;
	}

	public void setCurrentLoadPercentage(double currentLoadPercentage) {
		this.currentLoadPercentage = currentLoadPercentage;	
	}

}
