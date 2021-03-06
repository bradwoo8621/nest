/**
 * 
 */
package com.github.nest.quelea.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.nest.quelea.model.Organization;

/**
 * organization repository
 * 
 * @author brad.wu
 */
public interface OrganizationRepository extends CrudRepository<Organization, Long> {
	/**
	 * find by given id number and id type code
	 * 
	 * @param idNumber
	 * @param idTypeCode
	 * @return
	 */
	Organization findByIdNumberAndIdTypeCode(String idNumber, String idTypeCode);
}
