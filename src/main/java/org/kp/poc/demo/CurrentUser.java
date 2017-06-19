/**
 * 
 */
package org.kp.poc.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.kp.poc.demo.model.Role;
import org.kp.poc.demo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author Robert Tu
 *
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

private static final long serialVersionUID = 6837195266359858742L;
	
	private User user;

    public CurrentUser(User user) {
        //super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(RoleEnum.USER.toString()));
        //super(user.getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
    	super(user.getUsername(), 
    			user.getPassword(), 
    			user.getEnabled(), 
    			user.getAccountNonExpired(), 
    			user.getCredentialsNonExpired(), 
    			user.getAccountNonLocked(),
    			AuthorityUtils.createAuthorityList("USER"));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public Collection<Role> getRole() {
        return user.getRole();
    }
    
    public static List<GrantedAuthority> getAuthorities(List<Role> roles) {
		List<Role> roleList = roles.stream().collect(Collectors.toList());
		List<GrantedAuthority> gaList = new ArrayList<>();
		roleList.stream().forEach(role -> gaList.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase())));
		return gaList;
	}
}
