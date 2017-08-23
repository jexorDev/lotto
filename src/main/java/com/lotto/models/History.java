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

    private Double balance;

    // TODO remove when the user is an object on the payment object
    private UserDao userDao;

    // TODO remove the userDao parameter from this constructor when the users are full objects on the payment object
    public History(UserDao userDao) {
        this.userDao = userDao;
        this.transactions = new ArrayList<Transaction>();
    };

    public void processTransactions(User activeUser, User relatedUser, Double runningBalance) {
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
        User payer = userDao.findOne((long)payment.getPayerId());
        User recipient = userDao.findOne((long)payment.getRecipientId());
        Date date = payment.getPaymentDate();
        Double amount = payment.getAmount();

        String description = String.format("%1$s paid $%2$.2f to %3$s.", payer.getUserName(), amount, recipient.getUserName());

        Transaction transaction = new Transaction(date, description, payer, recipient, amount);
        transactions.add(transaction);
    }

    public void AddTickets(Iterable<Ticket> tickets){
        for (Ticket t : tickets){
            AddTicket(t);
        }
    }

    public void AddTicket(Ticket ticket){

    }
}
