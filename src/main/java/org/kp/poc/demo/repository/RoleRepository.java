/**
 * 
 */
package org.kp.poc.demo.repository;

import org.kp.poc.demo.model.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Robert Tu 06/18/2017
 *
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

	public Role findRoleByRoleName(String roleName);
}
