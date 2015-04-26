package br.com.naegling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.naegling.domain.VirtualNode;

public interface VirtualNodeRepository extends JpaRepository<VirtualNode, Long> {

}
