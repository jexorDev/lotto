package com.lotto.controller;

import com.lotto.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
//@RequestMapping("api/v1/")
public class HomeController {

    @RequestMapping("/home")
    //Method-level request mapping appends its value onto the end of the class-level annotation
    //@RequestMapping(value = "shipwrecks", method = RequestMethod.GET)
    public Model home(@RequestParam("user") String username, Model model) {
        //loadData();

        List<User> allUsers = new ArrayList<User>();
        User currentUser = null;
        User nextUser = null;

        // get the users and sort them
        for (User u : userDao.findAll()){
            // matching usernames is not case sensitive
            if (u.getUserName().equalsIgnoreCase(username)) {
                currentUser = u;
            } else {
                nextUser = u; // any other user, used for the history
            }
            allUsers.add(u);
        }
        Collections.sort(allUsers);

        model.addAttribute("users", allUsers);
        model.addAttribute("currentUser", currentUser);

        // get the tickets and sort them
        List<Ticket> tickets = new ArrayList<Ticket>();
        for (Ticket t : ticketDao.findAll()){ tickets.add(t); }
        Collections.sort(tickets);
        model.addAttribute("tickets", tickets);

        History history = new History();
        history.AddPayments(paymentDao.findAll());
        history.AddTickets(tickets);
        // TODO make the last parameter dynamic from the TotalSummary object
        history.processTransactions(currentUser, nextUser, 5);
        model.addAttribute("history", history);

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
    private PickDao pickDao;

    @Autowired
    private PlayerDao playerDao;

    private void loadData(){
        playerDao.deleteAll();
        pickDao.deleteAll();
        ticketDao.deleteAll();
        paymentDao.deleteAll();
        totalSummaryDao.deleteAll();

        User dustin;
        User anthony;
        User sean;
        List<User> allUsers = new ArrayList<User>();

        if (userDao.exists((long)1)) { // tables are old and already have user data
            for (User u : userDao.findAll()){ allUsers.add(u); }

            dustin = userDao.findOne((long)1);
            anthony = userDao.findOne((long)2);
            sean = userDao.findOne((long)3);
        } else { // tables are brand new. You need to add the users for the first time.
            dustin = new User("Dustin",true, "Welcome123", "", null);
            sean = new User("Sean",true, "Welcome123", "", null);
            anthony = new User("Anthony",true, "Welcome123", "", null);

            userDao.save(dustin);
            userDao.save(anthony);
            userDao.save(sean);

            allUsers.add(dustin);
            allUsers.add(anthony);
            allUsers.add(sean);
        }

        // TODO: delete this while removing random picks
        for (int i = 0; i <= randomNumber(5) + 3; i++) {
            int cost = randomNumber(5) * 3;
            long userId = (long)randomNumber(3);
            User user = userDao.findOne(userId);
            Boolean powerPlay = randomNumber(2) == 2;

            ///////////////////////////// DON'T DELETE THIS ///////////////////////////////
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
        }

        // load up a bunch of random payments to/from everyone
        for (int i = 0; i < randomNumber(7) + 2; i++) {
            paymentDao.save(new Payment(sean, anthony, randomDate(), randomNumber(10)));
        }
        for (int i = 0; i < randomNumber(7) + 2; i++) {
            paymentDao.save(new Payment(anthony, sean, randomDate(), randomNumber(10)));
        }
        for (int i = 0; i < randomNumber(7) + 2; i++) {
            paymentDao.save(new Payment(sean, dustin, randomDate(), randomNumber(10)));
        }
        for (int i = 0; i < randomNumber(7) + 2; i++) {
            paymentDao.save(new Payment(dustin, sean, randomDate(), randomNumber(10)));
        }
        for (int i = 0; i < randomNumber(7) + 2; i++) {
            paymentDao.save(new Payment(anthony, dustin, randomDate(), randomNumber(10)));
        }
        for (int i = 0; i < randomNumber(7) + 2; i++) {
            paymentDao.save(new Payment(dustin, anthony, randomDate(), randomNumber(10)));
        }

        totalSummaryDao.save(new TotalSummary(dustin, anthony, 12));
        totalSummaryDao.save(new TotalSummary(anthony, dustin, -12));
        totalSummaryDao.save(new TotalSummary(dustin, sean, 6));
        totalSummaryDao.save(new TotalSummary(sean, dustin, -6));
        totalSummaryDao.save(new TotalSummary(sean, anthony, 2));
        totalSummaryDao.save(new TotalSummary(anthony, sean, -2));

        return;
    }

    // TODO: delete this while removing random picks
    private List<Player> randomPlayers(Ticket ticket, Iterable<User> allUsers){
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
    private List<Pick> randomPicks(Ticket ticket){
        List<Pick>picks = new ArrayList<Pick>();
        for (int j = 0; j < randomNumber(5); j++) {
            picks.add(randomPick(ticket));
        }
        return picks;
    }

    // TODO: delete this while removing random picks
    private Date randomDate(){
        int year = 2017;
        int month = randomNumber(12) - 1;
        int day = randomNumber(28);

        return new Date(year, month, day);
    }

    // TODO: delete this while removing random picks
    private Pick randomPick(Ticket ticket){
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

        return new Pick(ticket, picks.get(0), picks.get(1), picks.get(2), picks.get(3), picks.get(4), powerBall);
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