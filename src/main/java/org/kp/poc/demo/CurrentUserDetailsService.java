/**
 * 
 */
package org.kp.poc.demo;

import java.util.Optional;

import org.kp.poc.demo.model.User;
import org.kp.poc.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Robert Tu
 *
 */
@Service
public class CurrentUserDetailsService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final UserService userService;

    @Autowired
    public CurrentUserDetailsService(UserService userService) {
        this.userService = userService;
    }
    
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> curUser = userService.getUserByUsername(username);
		if(curUser.isPresent()) {
			User signonMember = curUser.get();
			logger.info("***********************************Found Login User: " + username + " with Password " + signonMember.getPassword());
			return new CurrentUser(signonMember);
		} else {
			throw new UsernameNotFoundException(String.format("User with user name =%s was not found", username));
		}
        
	}
}
