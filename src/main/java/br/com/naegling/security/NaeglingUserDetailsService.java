package br.com.naegling.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.naegling.domain.Account;
import br.com.naegling.domain.Role;
import br.com.naegling.service.AccountService;


/**
 * Custom {@link UserDetailsService} which retrieves the data for the user authenticatiing from the database. When the
 * user exists returns a {@link NaeglingUserDetails} containing all inforamtion for further authentication
 * 
 * @author Marten Deinum
 * @author Koen Serneels
 * 
 */
@Component
public class NaeglingUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		if (StringUtils.isBlank(userName)) {
			throw new UsernameNotFoundException("Username was empty");
		}

		Account account = accountService.findByUserName(userName);

		if (account == null) {
			throw new UsernameNotFoundException("Username not found");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		for (Role role : account.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		
		return new NaeglingUserDetails(accountService.findByUserName(userName), grantedAuthorities);
	}
}
