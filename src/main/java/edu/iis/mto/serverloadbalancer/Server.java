package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	private static final double MXIMUM_LOAD = 100.0;
	public double currentLoadPecentage;
	private int capacity;
	private List<Vm> vms = new ArrayList<Vm>();

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public void addVm(Vm vm) {
		vms.add(vm);
		currentLoadPecentage += MXIMUM_LOAD * vm.getSize() / capacity;
	}

	public int getVmCount() {
		return vms.size();
	}

}
