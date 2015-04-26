package br.com.naegling.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import br.com.naegling.domain.Cluster;
import br.com.naegling.domain.VirtualNode;
import br.com.naegling.service.exception.ClusterNotFoundException;

public interface ClusterService {
	/**
     * Creates a new virtualMachineHost.
     * @param created   The information of the created virtualMachineHost.
     * @return  The created virtualMachineHost.
     */
    public Cluster create(Cluster created);

    /**
     * Deletes a virtualMachineHost.
     * @param virtualMachineHostId  The id of the deleted virtualMachineHost.
     * @return  The deleted virtualMachineHost.
     * @throws ClusterNotFoundException  if no virtualMachineHost is found with the given id.
     */
    public Cluster delete(Long virtualMachineHostId) throws ClusterNotFoundException;

    /**
     * Finds all virtualMachineHosts.
     * @return  A list of virtualMachineHosts.
     */
    public List<Cluster> findAll();

    /**
     * Finds virtualMachineHost by id.
     * @param id    The id of the wanted virtualMachineHost.
     * @return  The found virtualMachineHost. If no virtualMachineHost is found, this method returns null.
     */
    public Cluster findById(Long id);

    /**
     * Updates the information of a virtualMachineHost.
     * @param updated   The information of the updated virtualMachineHost.
     * @return  The updated virtualMachineHost.
     * @throws ClusterNotFoundException  if no virtualMachineHost is found with given id.
     */
    public Cluster update(Cluster updated) throws ClusterNotFoundException;
    
    /**
     * Finds virtualMachineHost by userName
     * @param userName
     * @return
     */
    public Cluster findByName(String hostName);

	public Cluster addNode(Cluster cluster, VirtualNode node)
			throws ClusterNotFoundException, ConstraintViolationException;

	public void removeNode(Cluster clusterById, VirtualNode node) throws ClusterNotFoundException;


}
