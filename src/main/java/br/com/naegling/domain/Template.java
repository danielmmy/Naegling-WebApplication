package br.com.naegling.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="templates")
public class Template implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="path", nullable=false)
	private String path;
	
	@Column(name="md5_sum", nullable=true)
	private String md5Sum;


	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMd5Sum() {
		return md5Sum;
	}

	public void setMd5Sum(String md5Sum) {
		this.md5Sum = md5Sum;
	}

	public void update(String name, String path, String md5Sum){
		this.name=name;
		this.path=path;
		this.md5Sum=md5Sum;
	}
	
    public static Builder getBuilder(String name, String path, String md5Sum) {
        return new Builder(name, path, md5Sum);
    }
	
	public static class Builder {
	        Template built;

	        /**
	         * Creates a new Builder instance.
	         * @param firstName The first name of the created Account object.
	         * @param lastName  The last name of the created Account object.
	         */
	        Builder(String name, String path, String md5Sum) {
	            built = new Template();
	            built.name = name;
	            built.path = path;
	            built.md5Sum=md5Sum;

	        }

	        /**
	         * Builds the new Account object.
	         * @return  The created Account object.
	         */
	        public Template build() {
	            return built;
	        }
	}

}
