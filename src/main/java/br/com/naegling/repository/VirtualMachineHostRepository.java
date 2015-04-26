package br.com.naegling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.naegling.domain.VirtualMachineHost;

public interface VirtualMachineHostRepository extends JpaRepository<VirtualMachineHost, Long> {

}
