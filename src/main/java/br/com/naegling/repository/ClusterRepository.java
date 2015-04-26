package br.com.naegling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.naegling.domain.Cluster;

public interface ClusterRepository extends JpaRepository<Cluster, Long>{

}
