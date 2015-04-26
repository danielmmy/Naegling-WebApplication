package br.com.naegling.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.naegling.domain.Role;
import br.com.naegling.repository.RoleRepository;
import br.com.naegling.service.exception.RoleNotFoundException;

@Service
public class RepositoryRoleService implements RoleService {

	
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryAccountService.class);
    
    @Resource
    private RoleRepository roleRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
	@Override
	public Role create(Role created) {
		LOGGER.debug("Creating a new role with information " + created);
		Role role=Role.getBuilder(created.getRole()).build();
		return roleRepository.save(role);
	}

    @Transactional(rollbackFor=RoleNotFoundException.class)
	@Override
	public Role delete(Long roleId) throws RoleNotFoundException {
    	 LOGGER.debug("Deleting role with id: " + roleId);
         
         Role deleted = roleRepository.findOne(roleId);
         
         if (deleted == null) {
             LOGGER.debug("No role found with id: " + roleId);
             throw new RoleNotFoundException();
         }
         
         roleRepository.delete(deleted);
         return deleted;
	}

    @Transactional(readOnly=true)
	@Override
	public List<Role> findAll() {
    	LOGGER.debug("Finding all roles");
    	return roleRepository.findAll();
    }

    @Transactional(readOnly=true)
	@Override
	public Role findById(Long id) {
        LOGGER.debug("Finding role by id: " + id);
        return roleRepository.findOne(id);
	}

    @Transactional(rollbackFor=RoleNotFoundException.class)
	@Override
	public Role update(Role updated)
			throws RoleNotFoundException {
        LOGGER.debug("Updating role with information: " + updated);
        
        Role role = roleRepository.findOne(updated.getId());
        
        if (role == null) {
            LOGGER.debug("No role found with id: " + updated.getId());
            throw new RoleNotFoundException();
        }
        	role.update(updated.getRole());

        return role;
	}

}
