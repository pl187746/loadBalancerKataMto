package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	public static final double MAXIMUM_LOAD = 100.0;
	private int capacity;
	private double currentLoadPercenatge;
	private List<Vm> vms = new ArrayList<Vm>();

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercenatge;
	}

	public void addVm(Vm vm) {
		vms.add(vm);
		currentLoadPercenatge += loadOf(vm);		
	}

	public int getVmCount() {
		return vms.size();
	}

	public boolean contains(Vm vm) {
		return vms.contains(vm);
	}

	public boolean canFit(Vm vm) {
		return currentLoadPercenatge + loadOf(vm) <= MAXIMUM_LOAD;
	}

	private double loadOf(Vm vm) {
		return MAXIMUM_LOAD * vm.getSize() / capacity;
	}

}
