package edu.iis.mto.serverloadbalancer;

class ServerVmPair {
	public int serverIdx;
	public int vmIdx;
	public ServerVmPair(int serverIdx, int vmIdx) {
		super();
		this.serverIdx = serverIdx;
		this.vmIdx = vmIdx;
	}
}