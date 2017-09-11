package com.lotto.controller;

import com.lotto.helpers.TotalSummaryHelper;
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
    public Model home(@RequestParam("user") String username, @RequestParam(value="historyUser", required=false) Integer historyUserId, Model model) {
        //loadData();
        historyUserId = historyUserId == null ? 0 : historyUserId;

        List<User> allUsers = new ArrayList<User>();
        User currentUser = null;
        User historyUser = null;

        // get the users and sort them
        for (User u : userDao.findAll()){
            // matching usernames is not case sensitive
            if (u.getUserName().equalsIgnoreCase(username)) {
                currentUser = u;
            } else if (u.getId() == historyUserId || historyUserId == 0){
                historyUser = u; // any other user, used for the history
                historyUserId = (int)u.getId();
            }
            allUsers.add(u);
        }
        Collections.sort(allUsers);

        model.addAttribute("users", allUsers);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("historyUser", historyUser);

        /*
        // This filters out tickets. Use this when we have more time for an elegant show/hide all solution.
        // get the tickets and sort them
        List<Ticket> tickets = new ArrayList<Ticket>();
        Date today = new Date();
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.add(Calendar.DAY_OF_MONTH, -5);
        for (Ticket t : ticketDao.findByPurchaseDateAfter(myCalendar.getTime())){
            // Get tickets with a draw date that's in the future
            if (t.GetDrawDate().compareTo(today) == 1) {
                tickets.add(t);
            }
        }
        Collections.sort(tickets);
        model.addAttribute("tickets", tickets);
         */
        // get the tickets and sort them
        List<Ticket> tickets = new ArrayList<Ticket>();
        for (Ticket t : ticketDao.findAll()){ tickets.add(t); }
        Collections.sort(tickets);
        Collections.reverse(tickets);
        model.addAttribute("tickets", tickets);

        Integer comparisonTotal = 0;
        List<TotalSummary> totalSummary = new ArrayList<TotalSummary>();
        for (TotalSummary t : totalSummaryDao.findByUserA(currentUser)) {
            totalSummary.add(t);

            if (t.getUserA() == currentUser && t.getUserB() == historyUser){
                comparisonTotal = t.getAmount();
            }
        }
        model.addAttribute("totalSummary", totalSummary);

        History history = new History();
        history.AddPayments(paymentDao.findAll());
        history.AddTickets(tickets);
        history.processTransactions(currentUser, historyUser, comparisonTotal);
        model.addAttribute("history", history);

        // get the users and sort them
        List<User> users = new ArrayList<User>();
        for (User u : userDao.findAll()){
            users.add(u);
        }
        Collections.sort(users);
        model.addAttribute("users", users);

        model.addAttribute("ticket", new Ticket());

        List<Pick> picks = new ArrayList<Pick>();
        picks.add(new Pick());
        model.addAttribute("picksList", picks);

        model.addAttribute("payment", new Payment());

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

    // TODO: delete this while removing random picks
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

        for (int i = 0; i <= randomNumber(5) + 3; i++) {
            int cost = randomNumber(5) * 3;
            long userId = (long)randomNumber(3);
            User user = userDao.findOne(userId);
            Boolean powerPlay = randomNumber(2) == 2;

            // either a date in August or September
            Date newDate = new Date(2017, randomNumber(2)+6, randomNumber(30));

            // save the ticket without picks or players
            Ticket ticket = new Ticket(cost, newDate, powerPlay, user);
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

        TotalSummaryHelper totalSummaryHelper = new TotalSummaryHelper(totalSummaryDao);
        totalSummaryHelper.createOrUpdateTotalSummary(dustin, anthony, -12);
        totalSummaryHelper.createOrUpdateTotalSummary(dustin, sean, -6);
        totalSummaryHelper.createOrUpdateTotalSummary(anthony, sean, 2);

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