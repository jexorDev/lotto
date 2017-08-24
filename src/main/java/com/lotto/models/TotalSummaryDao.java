package com.lotto.models;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * A DAO for the entity TotalSummary is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 *
 * @author des
 */
@Transactional
public interface TotalSummaryDao extends CrudRepository<User, Long> {


}