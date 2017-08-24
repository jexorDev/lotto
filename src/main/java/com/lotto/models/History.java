package com.lotto.models;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by cmorgan on 8/23/2017.
 */
public class History {

    private List<Transaction> transactions;

    private Integer balance;

    // TODO remove when the user is an object on the payment object
    private UserDao userDao;

    // TODO remove the userDao parameter from this constructor when the users are full objects on the payment object
    public History(UserDao userDao) {
        this.userDao = userDao;
        this.transactions = new ArrayList<Transaction>();
    };

    public void processTransactions(User activeUser, User relatedUser, Integer runningBalance) {
        this.balance = runningBalance;

        sortTransactions();

        for (Transaction t : transactions){
            if ((t.getPayer()     == activeUser || t.getPayer()     == relatedUser)
             && (t.getRecipient() == activeUser || t.getRecipient() == relatedUser)
                ){
                // ensure the record will show in the template
                t.Show();

                // mark whether it's considered a credit
                Boolean isCredit = t.getRecipient() == relatedUser;

                // set the running total
                t.SetProcessingInfo(runningBalance, isCredit);

                runningBalance += isCredit ? t.getAmount() : -t.getAmount();

            } else {
                t.Hide();
            }
        }
    }

    public void sortTransactions() {
        Collections.sort(transactions);
        Collections.reverse(transactions);
    }

    public void AddPayments(Iterable<Payment> payments){
        for (Payment p : payments){
            AddPayment(p);
        }
    }

    public ArrayList<Transaction> getTransactions(){ return (ArrayList<Transaction>)transactions; }

    public void AddPayment(Payment payment){
        // TODO replace the INT object with a real User object
        Date date = payment.getPaymentDate();
        Integer amount = payment.getAmount();

        String description = String.format("%1$s paid $%2$d to %3$s.", payment.getPayer().getUserName(), amount, payment.getRecipient().getUserName());

        Transaction transaction = new Transaction(date, description, payment.getPayer(), payment.getRecipient(), amount);
        transactions.add(transaction);
    }

    public void AddTickets(Iterable<Ticket> tickets){
        for (Ticket t : tickets){
            AddTicket(t);
        }
    }

    public void AddTicket(Ticket ticket){
        User payer = ticket.getPurchaserUser();
        Date date = ticket.getPurchasedate();

        // TODO replace this with an actual calculation of how many players there are
        Integer playerCount = 2 + 1; // 2 players + 1 purchaser
        Integer amount = ticket.getCost() / playerCount;

        // TODO replace with a for loop of each player.
        // this is just making a list of players that assumes everyone other than the purchaser is a player
        List<User> players = new ArrayList<User>();
        for (int i = 1; i < 4; i++) {
            if (i != payer.getId()){
                players.add(userDao.findOne((long)i));
            }
        }

        for (User recipient : players) {
            String description = String.format("%1$s bought a ticket for %2$s.", payer.getUserName(), recipient.getUserName());
            Transaction transaction = new Transaction(date, description, payer, recipient, amount);
            transactions.add(transaction);
        }
    }
}
