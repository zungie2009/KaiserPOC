/**
 * 
 */
package org.kp.poc.demo.repository;

import java.util.Optional;

import org.kp.poc.demo.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Robert Tu  06/18/2017
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

	public Optional<User> findOneByEmail(String email);
	
	public Optional<User> findOneByUsername(String userName);
}
