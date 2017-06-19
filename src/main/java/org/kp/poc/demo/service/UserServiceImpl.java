/**
 * 
 */
package org.kp.poc.demo.service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.kp.poc.demo.model.User;
import org.kp.poc.demo.repository.RoleRepository;
import org.kp.poc.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Robert Tu  06/18/2017
 *
 */
@Service
public class UserServiceImpl implements UserService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected final UserRepository userRepository;
	protected final RoleRepository roleRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	@Override
	public Optional<User> getUserById(long id) {
		return Optional.ofNullable(userRepository.findOne(id));
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}
	
	@Override
	public Optional<User> getUserByUsername(String userName) {
		return userRepository.findOneByUsername(userName);
	}

	@Override
	public Collection<User> getAllUsers() {
		return (Collection<User>) userRepository.findAll();
	}

	@Override
	public User create(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public void recordLastLogin(Long userId, Date loginDate, boolean fromWebService) {
        logger.info("In record LastLogin with User ID = " + userId);
        User user = userRepository.findOne(userId);
        if (!fromWebService) {
            user.setActive(true);
            
            user.setAccountNonLocked(true);
            user.setAccountLockedReason(null);
        }
        user.setLogin_date(loginDate);
        user = userRepository.save(user);
    }
	
	public void recordLastLogin(Long userId, Date loginDate) {
        recordLastLogin(userId, loginDate, false);
    }

	@Override
	public void deleteUser(long id) {
		userRepository.delete(id);
	}

}
