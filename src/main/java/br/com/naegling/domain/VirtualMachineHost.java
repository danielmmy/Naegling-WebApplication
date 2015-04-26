package br.com.naegling.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="virtual_machine_hosts")
public class VirtualMachineHost implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="host_name",nullable=false)
	private String hostName;
	@Column(nullable=false)
	private String ip;
	@Column(name="naegling_port",nullable=false)
	private String naeglingPort;
	@Column(nullable=false)
	private String hypervisor;
	@Column(name="bridge_network_interface",nullable=false)
	private String bridgeNetworkInterface;
	
	@OneToMany( fetch=FetchType.EAGER)
	@JoinColumn(name="host")
	private List<VirtualNode> nodes;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNaeglingPort() {
		return naeglingPort;
	}

	public void setNaeglingPort(String naeglingPort) {
		this.naeglingPort = naeglingPort;
	}

	public String getHypervisor() {
		return hypervisor;
	}

	public void setHypervisor(String hypervisor) {
		this.hypervisor = hypervisor;
	}

	public String getBridgeNetworkInterface() {
		return bridgeNetworkInterface;
	}

	public void setBridgeNetworkInterface(String bridgeNetworkInterface) {
		this.bridgeNetworkInterface = bridgeNetworkInterface;
	}

	public List<VirtualNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<VirtualNode> nodes) {
		this.nodes = nodes;
	}

	public static Builder getBuilder(String hostName, String ip, String naeglingPort, String hypervisor, String bridgeNetworkInterface, List<VirtualNode> nodes) {
		return new Builder(hostName, ip, naeglingPort, hypervisor, bridgeNetworkInterface, nodes);
	}
	
	public void update(String hostName, String ip, String naeglingPort, String hypervisor, String bridgeNetworkInterface, List<VirtualNode> nodes){
		this.hostName=hostName;
		this.ip=ip;
		this.naeglingPort=naeglingPort;
		this.hypervisor=hypervisor;
		this.bridgeNetworkInterface=bridgeNetworkInterface;
		this.nodes=nodes;
	}
	
    public static class Builder {
        VirtualMachineHost built;

        Builder(String hostName, String ip, String naeglingPort, String hypervisor, String bridgeNetworkInterface, List<VirtualNode> nodes) {
            built = new VirtualMachineHost();
            built.hostName=hostName;
            built.ip=ip;
            built.naeglingPort=naeglingPort;
            built.hypervisor=hypervisor;
            built.bridgeNetworkInterface=bridgeNetworkInterface;
            built.nodes=nodes;
            
        }

        /**
         * Builds the new Account object.
         * @return  The created Account object.
         */
        public VirtualMachineHost build() {
            return built;
        }
    }

}
