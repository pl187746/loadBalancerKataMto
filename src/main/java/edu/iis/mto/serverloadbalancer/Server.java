package edu.iis.mto.serverloadbalancer;

public class Server {

	private static final double MXIMUM_LOAD = 100.0;
	public double currentLoadPecentage;
	private int capacity;

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public void addVm(Vm vm) {
		currentLoadPecentage += MXIMUM_LOAD * vm.getSize() / capacity;
	}

}
