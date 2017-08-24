package com.lotto.controller;

import com.lotto.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
     * @param purchaserUser User object of the user who purchased the ticket.  Note: you probably don't need the @requestparm.
     * @return A string describing if the user is succesfully created or not.
     */
    @RequestMapping(value="/ticket/create", method=POST )
    @ResponseBody
    public String create(Integer cost, Date purchaseDate, @RequestParam(value="purchaserUser") User purchaserUser) {
        Ticket ticket = null;
        try {
            //ticket = new Ticket(cost, purchaseDate, purchaserUser);
            ticketDao.save(ticket);

            // This is how it's done in the initial data load. This is likely the same way tickets will be created here.
            /*
            // save the ticket without picks or players
            Ticket ticket = new Ticket(cost, randomDate(), powerPlay, user);
            ticketDao.save(ticket);

            // use the ticket to create and save picks
            Iterable<Pick> picks = randomPicks(ticket);
            pickDao.save(picks);

            // use the ticket to create and save players
            Iterable<Player> players = randomPlayers(ticket, allUsers);
            playerDao.save(players);

            // add picks and players to the ticket. Save again
            ticket.addPicks(picks);
            ticket.addPlayers(players);
            ticketDao.save(ticket);
            */
        }
        catch (Exception ex) {
            return "Error creating the ticket: " + ex.toString();
        }
        return "Ticket successfully created! (id = " + ticket.getId() + ")";
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