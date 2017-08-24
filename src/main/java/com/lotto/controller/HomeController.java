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

    @Autowired
    private PlayerDao playerDao;

    private void loadData(){
        playerDao.deleteAll();
        ticketDao.deleteAll();
        paymentDao.deleteAll();
        totalSummaryDao.deleteAll();
        userDao.deleteAll();

        User dustin = new User("Dustin",true, "Welcome123", "", null);
        userDao.save(dustin);
        User anthony = new User("Anthony",true, "Welcome123", "", null);
        userDao.save(anthony);
        User sean = new User("Sean",true, "Welcome123", "", null);
        userDao.save(sean);
        List<User> allUsers = new ArrayList<User>();
        allUsers.add(dustin);
        allUsers.add(anthony);
        allUsers.add(sean);

        // TODO: delete this while removing random picks
        for (int i = 0; i <= randomNumber(10); i++) {
            int cost = randomNumber(5) * 3;
            long userId = (long)randomNumber(3);
            User user = userDao.findOne(userId);
            Boolean powerPlay = randomNumber(2) == 2;

            Ticket ticket = new Ticket(cost, randomDate(), powerPlay, user, randomPicks());
            Iterable<Player> players = randomPlayers(ticket, allUsers);

            ticketDao.save(ticket);
            playerDao.save(players);
        }

        paymentDao.save(new Payment(sean, anthony, randomDate(), 3));
        paymentDao.save(new Payment(anthony, dustin, randomDate(), 3));
        paymentDao.save(new Payment(anthony, sean, randomDate(), 2));
        paymentDao.save(new Payment(dustin, anthony, randomDate(), 5));
        paymentDao.save(new Payment(anthony, sean, randomDate(), 3));
        paymentDao.save(new Payment(anthony, dustin, randomDate(), 6));

        totalSummaryDao.save(new TotalSummary(dustin, anthony, 12));
        totalSummaryDao.save(new TotalSummary(anthony, dustin, -12));
        totalSummaryDao.save(new TotalSummary(dustin, sean, 6));
        totalSummaryDao.save(new TotalSummary(sean, dustin, -6));

        return;
    }

    private List<Player> randomPlayers(Ticket ticket, List<User> allUsers){
        List<Player> players = new ArrayList<Player>();
        List<User> possibleUsers = new ArrayList<User>();
        Integer playerCount = randomNumber(3) - 1;

        for (User user : allUsers){
            if (user != ticket.getPurchaserUser()){
                possibleUsers.add(user);
            }
        }

        if (playerCount == 0){
            return players;
        } else if (playerCount == 1) {
            possibleUsers.remove(randomNumber(2) - 1);
        }

        for (User user : possibleUsers){
            players.add(new Player(ticket, user));
        }

        return players;
    }

    // TODO: delete this while removing random picks
    private List<Pick> randomPicks(){
        List<Pick>picks = new ArrayList<Pick>();
                for (int j = 0; j <= randomNumber(5); j++) {
            picks.add(randomPick());
        }
        return picks;
    }


    // TODO: delete this while removing random picks
    private Date randomDate(){
        int year = randomNumber(2) + 2015;
        int month = randomNumber(12) - 1;
        int day = randomNumber(28);

        return new Date(year, month, day);
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