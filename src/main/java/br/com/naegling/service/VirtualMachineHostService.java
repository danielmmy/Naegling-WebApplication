package br.com.naegling.service;

import java.util.List;

import br.com.naegling.domain.VirtualMachineHost;
import br.com.naegling.service.exception.VirtualMachineHostNotFoundException;

public interface VirtualMachineHostService {
	
	/**
     * Creates a new virtualMachineHost.
     * @param created   The information of the created virtualMachineHost.
     * @return  The created virtualMachineHost.
     */
    public VirtualMachineHost create(VirtualMachineHost created);

    /**
     * Deletes a virtualMachineHost.
     * @param virtualMachineHostId  The id of the deleted virtualMachineHost.
     * @return  The deleted virtualMachineHost.
     * @throws VirtualMachineHostNotFoundException  if no virtualMachineHost is found with the given id.
     */
    public VirtualMachineHost delete(Long virtualMachineHostId) throws VirtualMachineHostNotFoundException;

    /**
     * Finds all virtualMachineHosts.
     * @return  A list of virtualMachineHosts.
     */
    public List<VirtualMachineHost> findAll();

    /**
     * Finds virtualMachineHost by id.
     * @param id    The id of the wanted virtualMachineHost.
     * @return  The found virtualMachineHost. If no virtualMachineHost is found, this method returns null.
     */
    public VirtualMachineHost findById(Long id);

    /**
     * Updates the information of a virtualMachineHost.
     * @param updated   The information of the updated virtualMachineHost.
     * @return  The updated virtualMachineHost.
     * @throws VirtualMachineHostNotFoundException  if no virtualMachineHost is found with given id.
     */
    public VirtualMachineHost update(VirtualMachineHost updated) throws VirtualMachineHostNotFoundException;
    
    /**
     * Finds virtualMachineHost by userName
     * @param userName
     * @return
     */
    public VirtualMachineHost findByHostName(String hostName);

}
