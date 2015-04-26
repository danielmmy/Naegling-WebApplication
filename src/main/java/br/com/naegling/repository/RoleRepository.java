package br.com.naegling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.naegling.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
