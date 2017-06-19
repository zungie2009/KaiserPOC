/**
 * 
 */
package org.kp.poc.demo.service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.kp.poc.demo.model.User;


/**
 * @author Robert Tu
 *
 */
public interface UserService {

	Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);
    
    Optional<User> getUserByUsername(String userName);

    Collection<User> getAllUsers();

    User create(User user);
    
    User saveUser(User user);
    
    void recordLastLogin(Long userId, Date loginDate);

    void deleteUser(long id);
}
