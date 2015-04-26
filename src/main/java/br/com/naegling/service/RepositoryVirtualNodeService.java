package br.com.naegling.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.naegling.domain.VirtualNode;
import br.com.naegling.repository.VirtualNodeRepository;
import br.com.naegling.service.exception.VirtualNodeNotFoundException;

@Service
public class RepositoryVirtualNodeService implements VirtualNodeService {
	   private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryAccountService.class);
	    
	    @Resource
	    private VirtualNodeRepository virtualNodeRepository;
	    
	    @PersistenceContext
	    private EntityManager entityManager;

	    @Transactional
		@Override
		public VirtualNode create(VirtualNode created) {
			LOGGER.debug("Creating a new virtualVirtualNode with information " + created);
			VirtualNode virtualVirtualNode=VirtualNode.getBuilder(created.getDomain(),created.getUuid(),created.getMacs(),created.getRamMemory(),created.getCpuQuantity(),created.getGraphicalAccessPort(),created.getHost()).build();
			return virtualNodeRepository.save(virtualVirtualNode);
		}

	    @Transactional(rollbackFor=VirtualNodeNotFoundException.class)
		@Override
		public VirtualNode delete(Long virtualVirtualNodeId) throws VirtualNodeNotFoundException {
	    	 LOGGER.debug("Deleting virtualVirtualNode with id: " + virtualVirtualNodeId);
	         
	         VirtualNode deleted = virtualNodeRepository.findOne(virtualVirtualNodeId);
	         
	         if (deleted == null) {
	             LOGGER.debug("No virtualVirtualNode found with id: " + virtualVirtualNodeId);
	             throw new VirtualNodeNotFoundException();
	         }
	         
	         virtualNodeRepository.delete(deleted);
	         return deleted;
		}

	    @Transactional(readOnly=true)
		@Override
		public List<VirtualNode> findAll() {
	    	LOGGER.debug("Finding all virtualVirtualNodes");
	    	return virtualNodeRepository.findAll();
	    }

	    @Transactional(readOnly=true)
		@Override
		public VirtualNode findById(Long id) {
	        LOGGER.debug("Finding virtualVirtualNode by id: " + id);
	        return virtualNodeRepository.findOne(id);
		}

	    @Transactional(rollbackFor=VirtualNodeNotFoundException.class)
		@Override
		public VirtualNode update(VirtualNode updated)
				throws VirtualNodeNotFoundException {
	        LOGGER.debug("Updating virtualNode with information: " + updated);
	        
	        VirtualNode virtualNode = virtualNodeRepository.findOne(updated.getId());
	        
	        if (virtualNode == null) {
	            LOGGER.debug("No virtualVirtualNode found with id: " + updated.getId());
	            throw new VirtualNodeNotFoundException();
	        }
	        	virtualNode.update(updated.getDomain(),updated.getUuid(),updated.getMacs(),updated.getRamMemory(),updated.getCpuQuantity(),updated.getGraphicalAccessPort(),updated.getHost());

	        return virtualNode;
		}


}
