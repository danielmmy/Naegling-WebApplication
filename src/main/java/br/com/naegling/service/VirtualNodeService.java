package br.com.naegling.service;

import java.util.List;

import br.com.naegling.domain.VirtualNode;
import br.com.naegling.service.exception.VirtualNodeNotFoundException;

public interface VirtualNodeService {
	  /**
     * Creates a new virtualNode.
     * @param created   The information of the created virtualNode.
     * @return  The created virtualNode.
     */
    public VirtualNode create(VirtualNode created);

    /**
     * Deletes a virtualNode.
     * @param virtualNodeId  The id of the deleted virtualNode.
     * @return  The deleted virtualNode.
     * @throws VirtualNodeNotFoundException  if no virtualNode is found with the given id.
     */
    public VirtualNode delete(Long virtualNodeId) throws VirtualNodeNotFoundException;

    /**
     * Finds all virtualNodes.
     * @return  A list of virtualNodes.
     */
    public List<VirtualNode> findAll();

    /**
     * Finds virtualNode by id.
     * @param id    The id of the wanted virtualNode.
     * @return  The found virtualNode. If no virtualNode is found, this method returns null.
     */
    public VirtualNode findById(Long id);

    /**
     * Updates the information of a virtualNode.
     * @param updated   The information of the updated virtualNode.
     * @return  The updated virtualNode.
     * @throws VirtualNodeNotFoundException  if no virtualNode is found with given id.
     */
    public VirtualNode update(VirtualNode updated) throws VirtualNodeNotFoundException;

}
