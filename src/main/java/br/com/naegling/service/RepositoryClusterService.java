package br.com.naegling.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.naegling.domain.Cluster;
import br.com.naegling.domain.VirtualNode;
import br.com.naegling.repository.ClusterRepository;
import br.com.naegling.service.exception.ClusterNotFoundException;

@Service
public class RepositoryClusterService implements ClusterService {
	

	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryClusterService.class);

	@Resource
    private ClusterRepository clusterRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
	@Override
	public Cluster create(Cluster created) {
		LOGGER.debug("Creating a new Cluster with information: " + created);
        
        Cluster cluster = Cluster.getBuilder(created.getName(), created.getNodes(),created.getAccount()).build();
        
        return clusterRepository.save(cluster);
	}

    @Transactional(rollbackFor = ClusterNotFoundException.class)
	@Override
	public Cluster delete(Long clusterId)	throws ClusterNotFoundException {
        LOGGER.debug("Deleting Cluster with id: " + clusterId);
        
        Cluster deleted = clusterRepository.findOne(clusterId);
        
        if (deleted == null) {
            LOGGER.debug("No Cluster found with id: " + clusterId);
            throw new ClusterNotFoundException();
        }
        
        clusterRepository.delete(deleted);
        return deleted;
	}

    @Transactional(readOnly=true)
	@Override
	public List<Cluster> findAll() {
		LOGGER.debug("Finding all Clusters");
		return clusterRepository.findAll();
	}

    @Transactional(readOnly=true)
	@Override
	public Cluster findById(Long id) {
		LOGGER.debug("Finding Cluster with id "+id);
		return clusterRepository.findOne(id);
	}

    @Transactional(rollbackFor=ClusterNotFoundException.class)
	@Override
	public Cluster update(Cluster updated)	throws ClusterNotFoundException {
		LOGGER.debug("Updating Cluster with information: " + updated);
        
        Cluster cluster = clusterRepository.findOne(updated.getId());
        
        if (cluster == null) {
            LOGGER.debug("No Cluster found with id: " + updated.getId());
            throw new ClusterNotFoundException();
        }
        	cluster.update(updated.getName());

        return cluster;
	}

    @Transactional(readOnly=true)
	@Override
	public Cluster findByName(String hostName) {
    	String hql = "select v from virtual_machine_host v where v.hostName=:hostName";
        TypedQuery<Cluster> query = entityManager.createQuery(hql, Cluster.class).setParameter("hostName",
                hostName);
        List<Cluster> hosts = query.getResultList();

        return hosts.size() == 1 ? hosts.get(0) : null;
	}
    
    @Transactional(rollbackFor = ClusterNotFoundException.class)
    @Override
    public Cluster addNode(Cluster cluster, VirtualNode node) throws ClusterNotFoundException , ConstraintViolationException{
        
        Cluster c=clusterRepository.findOne(cluster.getId());
        
        if (c == null) {
            LOGGER.debug("No cluster found with id: " + cluster.getId());
            throw new ClusterNotFoundException();
        }
        	c.addNode(node);

        return c;
    }

	@Override
	public void removeNode(Cluster cluster, VirtualNode node) throws ClusterNotFoundException {
		Cluster c=clusterRepository.findOne(cluster.getId());
        if (c == null) {
            LOGGER.debug("No cluster found with id: " + cluster.getId());
            throw new ClusterNotFoundException();
        }
        c.removeNode(node);
	}

}
