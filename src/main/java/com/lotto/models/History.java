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

    public History() {
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
        User purchaser = ticket.getPurchaserUser();
        Date date = ticket.getPurchasedate();

        Integer playerCount = ticket.GetPlayers().size() + 1;
        Integer amount = ticket.getCost() / playerCount;

        for (Player player : ticket.GetPlayers()) {
            String description = String.format("%1$s bought a ticket for %2$s.", purchaser.getUserName(), player.getUser().getUserName());
            transactions.add(new Transaction(date, description, purchaser, player.getUser(), amount));
        }
    }
}
