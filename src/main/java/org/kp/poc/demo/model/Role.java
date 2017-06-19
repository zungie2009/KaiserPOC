/**
 * 
 */
package org.kp.poc.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author turo
 *
 */
@Entity
@Table(name="ROLE")
public class Role implements Serializable {

	private static final long serialVersionUID = 3901929164761273238L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id = 0L;
	
	@Column(nullable = false, unique = true)
	private String roleName = "";
	private String description = "";
	
	@ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL , mappedBy = "role" , targetEntity = User.class)
	private Set<User> users = new HashSet<User>();
	
	public Role() {
		// Empty Constructor
	}

	public Role(String name, String description) {
		this.roleName = name;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
