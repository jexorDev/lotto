package com.lotto.models;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A DAO for the entity Ticket is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 *
 * @author des
 */
@Transactional
public interface TicketDao extends CrudRepository<Ticket, Long> {

    /**
     * Return the user having the passed email or null if no user is found.
     *
     * @param email the user email.
     */
    List<Ticket> findByPurchaseDateAfter(Date date);

} // class TicketDao