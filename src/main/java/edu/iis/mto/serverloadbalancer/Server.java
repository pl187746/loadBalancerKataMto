package edu.iis.mto.serverloadbalancer;


public class Server {

	private static final double MAXIMUM_LOAD = 100.0d;
	public double currentLoadPecentage;
	public int capacity;
	private int vmCount;

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public boolean contains(Vm vm) {
		return true;
	}

	public void addVm(Vm vm) {
		++vmCount;
		currentLoadPecentage = MAXIMUM_LOAD * vm.size / capacity;		
	}

	public int countVms() {
		return vmCount;
	}

}
