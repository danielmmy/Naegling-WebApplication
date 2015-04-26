package br.com.naegling.service;

import java.util.List;

import br.com.naegling.domain.Role;
import br.com.naegling.service.exception.RoleNotFoundException;

public interface RoleService {
	
    /**
     * Creates a new role.
     * @param created   The information of the created role.
     * @return  The created role.
     */
    public Role create(Role created);

    /**
     * Deletes a role.
     * @param roleId  The id of the deleted role.
     * @return  The deleted role.
     * @throws RoleNotFoundException  if no role is found with the given id.
     */
    public Role delete(Long roleId) throws RoleNotFoundException;

    /**
     * Finds all roles.
     * @return  A list of roles.
     */
    public List<Role> findAll();

    /**
     * Finds role by id.
     * @param id    The id of the wanted role.
     * @return  The found role. If no role is found, this method returns null.
     */
    public Role findById(Long id);

    /**
     * Updates the information of a role.
     * @param updated   The information of the updated role.
     * @return  The updated role.
     * @throws RoleNotFoundException  if no role is found with given id.
     */
    public Role update(Role updated) throws RoleNotFoundException;
    

}
