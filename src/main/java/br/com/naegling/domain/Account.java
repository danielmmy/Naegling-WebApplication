package br.com.naegling.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;





import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="accounts")
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name="user_name", nullable= false, unique=true)
    private String userName;
    
    @Column(name="email", nullable = false)
    private String email;
    
    @Column(name = "passwd", nullable = false)
    private String passwd;
    
    @ManyToMany(fetch=FetchType.EAGER)
    @Fetch(FetchMode.SELECT)//Avoid multiple bag exception fetching with separated query.
    @JoinTable(name="accounts_roles", joinColumns={@JoinColumn(name="account")}, inverseJoinColumns={@JoinColumn(name="role")})
    @Cascade({CascadeType.DELETE})
    private List<Role> roles = new ArrayList<Role>();
    
    
    @OneToMany( fetch=FetchType.EAGER)
    @Cascade({CascadeType.ALL})
    @JoinColumn(name="account")
    @Fetch(FetchMode.SELECT)//Avoid multiple bag exception fetching with separated query.
    private List<Cluster> clusters;
    
    @Version
    private long version = 0;


    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPasswd() {
		return passwd;
	}


	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	public List<Cluster> getClusters() {
		return clusters;
	}


	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}


	public long getVersion() {
		return version;
	}


	public void setVersion(long version) {
		this.version = version;
	}


	/**
     * Gets a builder which is used to create Account objects.
     * @param firstName The first name of the created user.
     * @param lastName  The last name of the created user.
     * @return  A new Builder instance.
     */
    public static Builder getBuilder(String firstName, String lastName, String userName,String email, String passwd, List<Role> roles,List<Cluster> clusters) {
        return new Builder(firstName, lastName, userName, email, passwd, roles,clusters);
    }


    /**
     * Gets the full name of the user.
     * @return  The full name of the user.
     */
    @Transient
    public String getName() {
        StringBuilder name = new StringBuilder();
        
        name.append(firstName);
        name.append(" ");
        name.append(lastName);
        
        return name.toString();
    }


    public void update(String firstName, String lastName, String userName,String email, String passwd, List<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName=userName;
        this.email=email;
        this.passwd= DigestUtils.sha256Hex(passwd + "{" + userName + "}");
        this.roles=roles;
    }
    
    public void addRole(Role role){
    	this.roles.add(role);
    }
    
    public void removeRole(Role role){
    	this.roles.remove(role);
    }
    
    public void addCluster(Cluster cluster){
    	this.clusters.add(cluster);
    }
    
    public void removeCluster(Cluster cluster){
    	this.clusters.remove(cluster);
    }
    
    public Cluster getClusterById(Long id){
    	Iterator<Cluster> i=clusters.iterator();
    	Cluster c;
    	while (i.hasNext()) {
			c=i.next();
			if(c.getId().compareTo(id)==0){
				return c;
			}
		}
    	return null;
    }
    
    public Cluster removeClusterById(Long id){
    	Iterator<Cluster> i=clusters.iterator();
    	Cluster c;
    	while (i.hasNext()) {
			c=i.next();
			if(c.getId().compareTo(id)==0){
				i.remove();
				return c;
			}
		}
    	return null;
    }
    
    public VirtualNode getNodeById(Long id){
    	Iterator<Cluster> i=clusters.iterator();
    	Cluster c;
    	while (i.hasNext()) {
			c=i.next();
			List<VirtualNode> nodes=c.getNodes();
			if(nodes!=null){
				Iterator<VirtualNode> i2=nodes.iterator();
				VirtualNode n;
				while(i2.hasNext()){
					n=i2.next();
					if(n.id.equals(id))
						return n;
				}
			}
		}
    	return null;
    }
    
    @PreUpdate
    public void preUpdate() {

    }
    
    @PrePersist
    public void prePersist() {

    }


    /**
     * A Builder class used to create new Account objects.
     */
    public static class Builder {
        Account built;

        /**
         * Creates a new Builder instance.
         * @param firstName The first name of the created Account object.
         * @param lastName  The last name of the created Account object.
         */
        Builder(String firstName, String lastName, String userName, String email,String passwd, List<Role> roles,List<Cluster> clusters) {
            built = new Account();
            built.firstName = firstName;
            built.lastName = lastName;
            built.userName=userName;
            built.email=email;
            built.passwd=DigestUtils.sha256Hex(passwd + "{" + userName + "}");
            built.roles=roles;
            built.clusters=clusters;
        }

        /**
         * Builds the new Account object.
         * @return  The created Account object.
         */
        public Account build() {
            return built;
        }
    }


	public void updateCluster(Cluster updated) {
    	Iterator<Cluster> i=clusters.iterator();
    	Cluster c;
    	while (i.hasNext()) {
			c=i.next();
			if(c.getId().compareTo(updated.getId())==0){
				c.update(updated.getName());
				break;
			}
		}
		
	}
    
//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }
}
