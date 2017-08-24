package com.lotto.controller;

import com.lotto.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
//@RequestMapping("api/v1/")
public class HomeController {

    @RequestMapping("/home")
    //Method-level request mapping appends its value onto the end of the class-level annotation
    //@RequestMapping(value = "shipwrecks", method = RequestMethod.GET)
    public Model home(Model model) {
        //loadData();

        // TODO remove this and make history.processTransactions be dynamic
        User dustin = userDao.findOne((long)1);
        User anthony = userDao.findOne((long)2);
        User sean = userDao.findOne((long)3);

        // TODO replace the "3" to get the current user from the DB
        model.addAttribute("currentUser", dustin);

        // get the tickets and sort them
        List<Ticket> tickets = new ArrayList<Ticket>();
        for (Ticket t : ticketDao.findAll()){
            tickets.add(t);
        }
        Collections.sort(tickets);
        model.addAttribute("tickets", tickets);

        History history = new History(userDao);
        history.AddPayments(paymentDao.findAll());
        history.AddTickets(tickets);
        history.processTransactions(dustin, anthony, 5);
        model.addAttribute("history", history);

        // get the users and sort them
        List<User> users = new ArrayList<User>();
        for (User u : userDao.findAll()){
            users.add(u);
        }
        Collections.sort(users);
        model.addAttribute("users", users);

        // render the template
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

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private TotalSummaryDao totalSummaryDao;

    private void loadData(){
        User dustin = new User("Dustin",true, "Welcome123", "", null);
        userDao.save(dustin);
        User anthony = new User("Anthony",true, "Welcome123", "", null);
        userDao.save(anthony);
        User sean = new User("Sean",true, "Welcome123", "", null);
        userDao.save(sean);

        // TODO: delete this while removing random picks
        for (int i = 0; i <= randomNumber(6); i++) {
            int cost = randomNumber(5) * 3;
            int year = 2017;
            int month = randomNumber(12) - 1;
            int day = randomNumber(28);
            User user = userDao.findOne((long)randomNumber(3));

            // TODO: delete this while removing random picks
            List<Pick>picks = new ArrayList<Pick>();
            for (int j = 0; j <= randomNumber(5); j++) {
                picks.add(randomPick());
            }

            List<Player> players = new ArrayList<Player>();

            ticketDao.save(new Ticket(cost, new Date(year, month, day), user, picks, players));
        }

        /*
        ticketDao.save(new Ticket(6, new Date(2017, 3, 28), anthony));
        ticketDao.save(new Ticket(3, new Date(2017, 7, 12), sean));
        ticketDao.save(new Ticket(9, new Date(2017, 5, 11), dustin));
        ticketDao.save(new Ticket(15, new Date(2017, 1, 30), anthony));
        ticketDao.save(new Ticket(12, new Date(2017, 9, 25), dustin));
        ticketDao.save(new Ticket(9, new Date(2017, 1, 6), anthony));
         */

        paymentDao.save(new Payment(sean, anthony, new Date(2017,1,6), 3));
        paymentDao.save(new Payment(anthony, dustin, new Date(2017,2,6), 3));
        paymentDao.save(new Payment(anthony, sean, new Date(2017,2,6), 2));
        paymentDao.save(new Payment(dustin, anthony, new Date(2017,3,6), 5));
        paymentDao.save(new Payment(anthony, sean, new Date(2017,4,6), 3));
        paymentDao.save(new Payment(anthony, dustin, new Date(2017,7,6), 6));

        totalSummaryDao.save(new TotalSummary(dustin, anthony, 12));
        totalSummaryDao.save(new TotalSummary(anthony, dustin, -12));
        totalSummaryDao.save(new TotalSummary(dustin, sean, 6));
        totalSummaryDao.save(new TotalSummary(sean, dustin, -6));

        return;
    }


    // TODO: delete this while removing random picks
    private Pick randomPick(){
        ArrayList<Integer> picks = new ArrayList<Integer>();

        int needed = 5;

        while (needed > 0){
            int newNumber = randomNumber(69);
            if (!picks.contains(newNumber)){
                picks.add(newNumber);
                needed--;
            }
        }

        int powerBall = randomNumber(26);

        return new Pick(picks.get(0), picks.get(1), picks.get(2), picks.get(3), picks.get(4), powerBall);
    }

    // TODO: delete this while removing random picks
    private Integer randomNumber(Integer max){
        Random r = new Random();
        int result = r.nextInt(max + 1);

        if (result > 0 && result <= max){
            return result;
        } else {
            return randomNumber(max);
        }
    }



}