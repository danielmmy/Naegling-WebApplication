package br.com.naegling.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
@Entity
@Table(name="clusters")
public class Cluster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@OneToMany( fetch=FetchType.EAGER)
    @Cascade({CascadeType.ALL})
    @JoinColumn(name="cluster")
    @Fetch(FetchMode.SELECT)
	private List<VirtualNode> nodes;
	
	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	private Account account;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<VirtualNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<VirtualNode> nodes) {
		this.nodes = nodes;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public static Builder getBuilder(String name, List<VirtualNode> nodes,Account account){
		return new Builder(name,nodes,account);
	}
	
	public void update(String name){
		this.name=name;
	}
	
	
	public static class Builder{
		Cluster built;
		Builder(String name,List<VirtualNode> nodes,Account account){
			built=new Cluster();
			built.name=name;
			built.nodes=nodes;
			built.account=account;
		}
		
		public Cluster build(){
			return built;
		}
	}
	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	public void addNode(VirtualNode node) {
		if(nodes==null){
			this.nodes=new ArrayList<VirtualNode>();
		}
		this.nodes.add(node);
	}

	public Node removeNodeById(Long nodeId) {
		Iterator<VirtualNode> i=nodes.iterator();
		VirtualNode v;
		while (i.hasNext()) {
			v=i.next();
			if(v.getId().compareTo(nodeId)==0){
				i.remove();
				return v;
			}
		}		
		return null;
		
	}

	public void removeNode(VirtualNode node) {
		this.nodes.remove(node);
		
	}

	public VirtualNode getNodeById(Long nodeId) {
		Iterator<VirtualNode> i=nodes.iterator();
		VirtualNode v;
		while (i.hasNext()) {
			v=i.next();
			if(v.getId().compareTo(nodeId)==0){
				return v;
			}
		}		
		return null;
	}
	
	public void updateNode(VirtualNode updated) {
    	Iterator<VirtualNode> i=nodes.iterator();
    	VirtualNode v;
    	while (i.hasNext()) {
			v=i.next();
			if(v.getId().compareTo(updated.getId())==0){
				v.update(updated.getDomain(),updated.getUuid(),updated.getMacs(),updated.getRamMemory(),updated.getCpuQuantity(),updated.getGraphicalAccessPort(),updated.getHost());
				break;
			}
		}
	}
}
