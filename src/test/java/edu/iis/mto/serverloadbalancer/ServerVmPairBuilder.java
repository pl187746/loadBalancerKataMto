package edu.iis.mto.serverloadbalancer;

class ServerVmPairBuilder implements Builder<ServerVmPair> {
	private int serverIdx;
	private int vmIdx;
	public ServerVmPair build() {
		return new ServerVmPair(serverIdx, vmIdx);
	}
	public ServerVmPairBuilder withServerIdx(int serverIdx) {
		this.serverIdx = serverIdx;
		return this;
	}
	public ServerVmPairBuilder withVmIdx(int vmIdx) {
		this.vmIdx = vmIdx;
		return this;
	}
	static ServerVmPairBuilder serverVmPair() {
		return new ServerVmPairBuilder();
	}
}