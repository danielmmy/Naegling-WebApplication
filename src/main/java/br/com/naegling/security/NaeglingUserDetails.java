package br.com.naegling.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.naegling.domain.Account;


/**
 * Customer {@link UserDetails} holding our authentication object {@link Account} and keeping track of the list of
 * {@link GrantedAuthority} for the current authenticated user
 * 
 * @author Marten Deinum
 * @author Koen Serneels
 * 
 */
public class NaeglingUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Account account;
	private List<? extends GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	public NaeglingUserDetails(Account account, List<? extends GrantedAuthority> authorities) {
		this.account = account;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return account.getPasswd();
	}

	@Override
	public String getUsername() {
		return account.getUserName();
	}

	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account=account;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
