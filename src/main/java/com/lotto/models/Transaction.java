package com.lotto.models;

/**
 * Created by cmorgan on 8/23/2017.
 */

import org.hibernate.tool.hbm2ddl.ImportScriptException;

import java.util.Date;

public class Transaction implements Comparable {

    @Override
    public int compareTo(Object o) {
        return this.transactionDate.compareTo(((Transaction)o).transactionDate);
    }
    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    private Date transactionDate;
    private String description;
    private User payer;
    private User recipient;
    private Integer amount;
    private Integer runningTotal;
    private Boolean isCredit;
    private Boolean isDebt;
    private Boolean display = true;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /*public Transaction() {}*/

    public Transaction(Date transactionDate, String description, User payer, User recipient, Integer amount) {
        this.transactionDate = transactionDate;
        this.description = description;
        this.payer = payer;
        this.recipient = recipient;
        this.amount = amount;
    }

    // Get-set methods
    public Date getTransactionDate() { return transactionDate; };

    public void setTransactionDate(Date transactionDate) { this.transactionDate = transactionDate; };

    public String getDescription() { return description; };

    public void setDescription(String description) { this.description = description; };

    public User getPayer() { return payer; };

    public void setPayer(User payer) { this.payer = payer; };

    public User getRecipient() { return recipient; };

    public void setRecipient(User recipient) { this.recipient = recipient; };

    public Integer getAmount() { return amount; }

    public void setAmount(Integer amount) { this.amount = amount; }

    public Integer getRunningTotal() { return runningTotal; }

    public void setRunningTotal(Integer runningTotal) { this.runningTotal = runningTotal; }

    public Boolean getIsCredit() { return isCredit; }

    public void setIsCredit(Boolean isCredit){ SetProcessingInfo(runningTotal, isCredit); }

    public Boolean getIsDebt() { return isDebt; }

    public void setIsDebt(Boolean isDebt){ SetProcessingInfo(runningTotal, !isDebt); }

    public void SetProcessingInfo(Integer runningTotal, Boolean isCredit){
        this.runningTotal = runningTotal;
        this.isCredit = isCredit;
        this.isDebt = !isCredit;
    }

    public void Show(){ this.display = true; }
    public void Hide(){ this.display = false; }
    public void Toggle() { this.display = !this.display; }
    public Boolean Display() { return this.display; }

    public Date GetJavaDate() {
        Date javaDate = new Date(transactionDate.getTime());
        javaDate.setYear(javaDate.getYear()-1900);
        return javaDate;
    }

}
