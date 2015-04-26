package br.com.naegling.controller;

import java.io.Serializable;
import java.util.List;

import br.com.naegling.domain.VirtualMachineHost;
import br.com.naegling.domain.VirtualNode;

public class EditNodeForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<VirtualMachineHost> hosts;
	private Long clusterId;
	private VirtualNode virtualNode;
	
	public List<VirtualMachineHost> getHosts() {
		return hosts;
	}
	public void setHosts(List<VirtualMachineHost> hosts) {
		this.hosts = hosts;
	}
	public Long getClusterId() {
		return clusterId;
	}
	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}
	public VirtualNode getVirtualNode() {
		return virtualNode;
	}
	public void setVirtualNode(VirtualNode virtualNode) {
		this.virtualNode = virtualNode;
	}
	
	

}
