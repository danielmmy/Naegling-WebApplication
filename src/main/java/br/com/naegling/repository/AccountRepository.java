package br.com.naegling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.naegling.domain.Account;


public interface AccountRepository extends JpaRepository<Account, Long>{
	
}
