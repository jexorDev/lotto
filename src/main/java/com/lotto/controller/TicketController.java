package com.lotto.controller;

import com.lotto.models.Ticket;
import com.lotto.models.TicketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * A class to test interactions with the MySQL database using the TicketDao class.
 *
 * @author des
 */
@Controller
public class TicketController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * /create  --> Create a new user and save it in the database.
     *
     * @param cost Ticket cost
     * @param purchaseDate Date ticket was purchased
     * @param purchaserUserId Id of the user who purchased the ticket.  Note that this can be sent in the body of the HTTP request.
     * @return A string describing if the user is succesfully created or not.
     */
    @RequestMapping("/ticket/create")
    @ResponseBody
    public String create(Integer cost, Date purchaseDate, @RequestParam(value="purchaserUserId") long purchaserUserId) {
        Ticket ticket = null;
        try {
            ticket = new Ticket(cost, purchaseDate, purchaserUserId);
            ticketDao.save(ticket);
        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created! (id = " + ticket.getId() + ")";
    }


    /**
     * /get-by-email  --> Return the id for the user having the passed email.
     *
     * @param email The email to search in the database.
     * @return The user id or a message error if the user is not found.

    @RequestMapping("/get-by-email")
    @ResponseBody
    public String getByEmail(String email) {
        String userId;
        try {
            User user = userDao.findByEmail(email);
            userId = String.valueOf(user.getId());
        }
        catch (Exception ex) {
            return "User not found";
        }
        return "The user id is: " + userId;
    }*/



    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    private TicketDao ticketDao;

}