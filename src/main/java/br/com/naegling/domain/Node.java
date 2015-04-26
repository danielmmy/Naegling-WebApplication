package br.com.naegling.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@MappedSuperclass
public abstract class Node implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	@Column(nullable=false)
	protected String domain;
	@Column(nullable=false)
	protected String uuid;
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	protected List<String> macs;
	@Column(nullable=false)
	protected int ramMemory;
	@Column(nullable=false)
	protected int cpuQuantity;
	@Column
	protected int graphicalAccessPort;
	@Transient
	protected int status;
	


	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public List<String> getMacs(){
		return macs;
	}
	
	public void setMacs(List<String> macs){
		this.macs=macs;
	}
	
	public String getMac(int index) {
		if(index<macs.size())
			return macs.get(index);
		else
			return null;
		
	}
	
	public void setMac(String mac, int index) {
		if(index<macs.size())
			this.macs.set(index, mac);
	}
	
	public int getRamMemory() {
		return ramMemory;
	}
	
	public void setRamMemory(int ramMemory) {
		this.ramMemory = ramMemory;
	}
	
	public int getCpuQuantity() {
		return cpuQuantity;
	}
	
	public void setCpuQuantity(int cpuQuantity) {
		this.cpuQuantity = cpuQuantity;
	}

	public int getGraphicalAccessPort() {
		return graphicalAccessPort;
	}
	
	public void setGraphicalAccessPort(int port) {
		this.graphicalAccessPort = port;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
