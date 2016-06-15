package edu.iis.mto.serverloadbalancer;

public class Server {

	public double currentLoadPecentage;
	private int capacity;

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public void addVm(Vm vm) {
		currentLoadPecentage += 100.0 * vm.getSize() / capacity;
	}

}
