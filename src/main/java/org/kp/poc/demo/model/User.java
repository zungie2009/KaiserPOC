/**
 * 
 */
package org.kp.poc.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * @author Robert Tu  06/18/2017
 *
 */
@Entity
@Table(name="USER")
public class User extends BaseEntity {

	private static final long serialVersionUID = -5423384564271547600L;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String email;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	private String password;
	private String member_type;
	private Boolean displayEmailFlag;
	private Boolean reviewer_flag;
	private Boolean enabled = true;
	
	
	 @ManyToMany(cascade=CascadeType.MERGE ,fetch = FetchType.LAZY )
	    @JoinTable(uniqueConstraints=@UniqueConstraint(columnNames={"USER_ID","ROLE_ID"}) , name="USER_ROLE", 
	                joinColumns=@JoinColumn(name="USER_ID")
	                ,inverseJoinColumns=@JoinColumn(name="ROLE_ID"))
	private Collection<Role>  role = new ArrayList<Role>();
	 
	private Date login_date;
	private Boolean accountNonExpired = true;
	private Boolean accountNonLocked = true;
	private String accountLockedReason;
	private Boolean credentialsNonExpired = true;
	private Boolean active;
	
	@Transient
	private long activityTime = 0;   // To store activity time

	public User() {
		// empty Constructor
	}
	
	public User(long id, String firstName, String middleName, String lastName, String email, String username, String password, String memberType) {
		this.id = id;
		this.first_name = firstName;
		this.last_name = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.member_type = memberType;
		this.dateCreated = new Date();
		this.dateModified = new Date();
	}
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}
	
	public Boolean getDisplayEmailFlag() {
		return displayEmailFlag;
	}

	public void setDisplayEmailFlag(Boolean displayEmailFlag) {
		this.displayEmailFlag = displayEmailFlag;
	}

	public Boolean getReviewer_flag() {
		return reviewer_flag;
	}

	public void setReviewer_flag(Boolean reviewer_flag) {
		this.reviewer_flag = reviewer_flag;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLogin_date() {
		return login_date;
	}
	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public String getAccountLockedReason() {
		return accountLockedReason;
	}

	public void setAccountLockedReason(String accountLockedReason) {
		this.accountLockedReason = accountLockedReason;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	
	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
   
	/*public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}*/

	public Collection<Role> getRole() {
		return role;
	}

	public void setRole(Collection<Role> role) {
		this.role = role;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public String getFullName() {
        return first_name + " " +
               last_name;
    }

	public long getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(long activityTime) {
		this.activityTime = activityTime;
	}
	
	public void refreshActivity() {
		this.activityTime = System.currentTimeMillis();
	}
	
	public boolean isSessionValid(long sessionTimeout) {
		long curTime = System.currentTimeMillis();
		return ((curTime - activityTime) > sessionTimeout) ? false : true;
	}
	
}
