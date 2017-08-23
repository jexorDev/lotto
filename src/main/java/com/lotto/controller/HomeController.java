package com.lotto.controller;

import com.lotto.models.Ticket;
import com.lotto.models.TicketDao;
import com.lotto.models.User;
import com.lotto.models.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@RestController
//@RequestMapping("api/v1/")
public class HomeController {

    @RequestMapping("/home")
    //Method-level request mapping appends its value onto the end of the class-level annotation
    //@RequestMapping(value = "shipwrecks", method = RequestMethod.GET)
    public Model home(Model model) {
        loadTickets();

        model.addAttribute("currentUser", userDao.findOne((long)3));
        model.addAttribute("name", "jacob");
        model.addAttribute("tickets", ticketDao.findAll());
        model.addAttribute("users", userDao.findAll());
        return model;
    }

    // another example with a specific id on the URL
    // @RequestMapping(value = shipwrecks/{id}", method = RequestMethod.PUT)
    // public Shipwreck update(@PathVariable Long id, @RequestBody Shipwreck shipwreck) }
    //     return ShipwreckStub.update(id, shipwreck);
    // }

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private UserDao userDao;

    private void loadTickets(){
        User testUser = new User("Dustin",true, "Welcome123", "", null);
        userDao.save(testUser);
        User testUser2 = new User("Anthony",true, "Welcome123", "", null);
        userDao.save(testUser2);
        User testUser3 = new User("Sean",true, "Welcome123", "", null);
        userDao.save(testUser3);

        ticketDao.save(new Ticket(7, new Date(2017, 3, 28), testUser2));
        ticketDao.save(new Ticket(7, new Date(2017, 7, 12), testUser3));
        ticketDao.save(new Ticket(8, new Date(2017, 5, 11), testUser));
        ticketDao.save(new Ticket(5, new Date(2017, 1, 30), testUser2));
        ticketDao.save(new Ticket(5, new Date(2016, 9, 25), testUser));
        ticketDao.save(new Ticket(9, new Date(2017, 1, 6), testUser2));
        return;
    }

}