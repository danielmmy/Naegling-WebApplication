package br.com.naegling.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Marten Deinum
 * @author Koen Serneels
 *
 */
@Entity
@Table(name="roles")//,uniqueConstraints = { @UniqueConstraint(columnNames = { "role" }) })
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String role;



	Role() {
		// For ORM
	}

	public Role(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static Builder getBuilder(String role) {
        return new Builder(role);
    }
	
	
	public void update(String role){
		this.role=role;
	}
	public static class Builder{
		Role built;
		Builder(String role) {
			built=new Role();
	        built.role=role;


	    }
		
		public Role build(){
			return built;
		}
	}
}
