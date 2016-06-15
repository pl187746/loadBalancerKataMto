package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	public static final double MAXIMUM_LOAD = 100.0;
	private int capacity;
	private double currentLoadPercentage;
	private List<Vm> vmList = new ArrayList<Vm>();

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public double getCurrentLoadPercentage() {
		return currentLoadPercentage;
	}
	
	public void addVm(Vm vm) {
		vmList.add(vm);
		currentLoadPercentage += calcLoadPercentage(vm);
	}

	private double calcLoadPercentage(Vm vm) {
		return MAXIMUM_LOAD * vm.getSize() / capacity;
	}

	public boolean contains(Vm vm) {
		return vmList.contains(vm);
	}

	public int getCapacity() {
		return capacity;
	}

	public int getVmCount() {
		return vmList.size();
	}

	public boolean canFit(Vm vm) {
		return currentLoadPercentage + calcLoadPercentage(vm) <= MAXIMUM_LOAD;
	}

}
