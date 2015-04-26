package br.com.naegling.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.naegling.domain.VirtualMachineHost;
import br.com.naegling.repository.VirtualMachineHostRepository;
import br.com.naegling.service.exception.VirtualMachineHostNotFoundException;

@Service
public class RepositoryVirtualMachineHostService implements VirtualMachineHostService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryVirtualMachineHostService.class);
	
	@Resource
    private VirtualMachineHostRepository virtualMachineHostRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
	@Override
	public VirtualMachineHost create(VirtualMachineHost created) {
		LOGGER.debug("Creating a new Virtual Machine Host with information: " + created);
        
        VirtualMachineHost virtualMachineHost = VirtualMachineHost.getBuilder(created.getHostName(), created.getIp(), created.getNaeglingPort(),created.getHypervisor(), created.getBridgeNetworkInterface(),created.getNodes()).build();
        
        return virtualMachineHostRepository.save(virtualMachineHost);
	}

    @Transactional(rollbackFor = VirtualMachineHostNotFoundException.class)
	@Override
	public VirtualMachineHost delete(Long virtualMachineHostId)	throws VirtualMachineHostNotFoundException {
        LOGGER.debug("Deleting Virtual Machine Host with id: " + virtualMachineHostId);
        
        VirtualMachineHost deleted = virtualMachineHostRepository.findOne(virtualMachineHostId);
        
        if (deleted == null) {
            LOGGER.debug("No Virtual Machine Host found with id: " + virtualMachineHostId);
            throw new VirtualMachineHostNotFoundException();
        }
        
        virtualMachineHostRepository.delete(deleted);
        return deleted;
	}

    @Transactional(readOnly=true)
	@Override
	public List<VirtualMachineHost> findAll() {
		LOGGER.debug("Finding all Virtual Machine Hosts");
		return virtualMachineHostRepository.findAll();
	}

    @Transactional(readOnly=true)
	@Override
	public VirtualMachineHost findById(Long id) {
		LOGGER.debug("Finding Virtual Machine Host with id "+id);
		return virtualMachineHostRepository.findOne(id);
	}

    @Transactional(rollbackFor=VirtualMachineHostNotFoundException.class)
	@Override
	public VirtualMachineHost update(VirtualMachineHost updated)	throws VirtualMachineHostNotFoundException {
		LOGGER.debug("Updating Virtual Machine Host with information: " + updated);
        
        VirtualMachineHost host = virtualMachineHostRepository.findOne(updated.getId());
        
        if (host == null) {
            LOGGER.debug("No Virtual Machine Host found with id: " + updated.getId());
            throw new VirtualMachineHostNotFoundException();
        }
        	host.update(updated.getHostName(), updated.getIp(), updated.getNaeglingPort(), updated.getHypervisor(), updated.getBridgeNetworkInterface(), updated.getNodes());

        return host;
	}

    @Transactional(readOnly=true)
	@Override
	public VirtualMachineHost findByHostName(String hostName) {
    	
    	String hql = "select v from VirtualMachineHost v where v.hostName=:hostName";
        TypedQuery<VirtualMachineHost> query = entityManager.createQuery(hql, VirtualMachineHost.class).setParameter("hostName", hostName);
        List<VirtualMachineHost> hosts = query.getResultList();
        return hosts.size() == 1 ? hosts.get(0) : null;
	}

}
