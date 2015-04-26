package br.com.naegling.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="VirtualNode")
public class VirtualNode extends Node implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	protected VirtualMachineHost host;
	
	public VirtualMachineHost getHost(){
		return host;
	}

	public void setHost(VirtualMachineHost host) {
		this.host = host;
	}
	
	public void update(String domain, String uuid, List<String> macs, int ramMemory, int cpuQuantity, int graphicalAccessPort, VirtualMachineHost host){
		 this.domain=domain;
         this.uuid=uuid;
         this.macs=macs;
         this.ramMemory=ramMemory;
         this.cpuQuantity=cpuQuantity;
         this.graphicalAccessPort=graphicalAccessPort;
         this.host=host;
	}
	
	public static Builder getBuilder(String domain, String uuid, List<String> macs, int ramMemory, int cpuQuantity, int graphicalAccessPort, VirtualMachineHost host){
		return new Builder(domain, uuid, macs, ramMemory, cpuQuantity, graphicalAccessPort, host);
	}
	

    /**
     * A Builder class used to create new VirtualNode objects.
     */
    public static class Builder {
        VirtualNode built;

        /**
         * Creates a new Builder instance.
         * @param firstName The first name of the created Account object.
         * @param lastName  The last name of the created Account object.
         */
        Builder(String domain, String uuid, List<String> macs, int ramMemory, int cpuQuantity, int graphicalAccessPort, VirtualMachineHost host) {
            built = new VirtualNode();
            built.domain=domain;
            built.uuid=uuid;
            built.macs=macs;
            built.ramMemory=ramMemory;
            built.cpuQuantity=cpuQuantity;
            built.graphicalAccessPort=graphicalAccessPort;
            built.host=host;
            
        }

        /**
         * Builds the new Account object.
         * @return  The created Account object.
         */
        public VirtualNode build() {
            return built;
        }
    }
	
	
}
