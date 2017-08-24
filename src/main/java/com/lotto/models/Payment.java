package com.lotto.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * An entity Payment
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 *
 * @author jtc
 */


@Entity
@Table(name = "payment")
public class Payment  implements Comparable {

    @Override
    public int compareTo(Object o) {
        return this.paymentDate.compareTo(((Payment)o).paymentDate);
    }
    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // the payer
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private User payer;

    // the recipient
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private User recipient;

    // Date of the payment
    @NotNull
    private Date paymentDate;

    // Amount of the payment
    @NotNull
    private Integer amount;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Payment() {}

    public Payment(long id) { this.id = id; }

    public Payment(User payer, User recipient, Date paymentDate, Integer amount) {
        this.payer = payer;
        this.recipient = recipient;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    // Get-set methods

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public User getPayer() { return payer; }

    public void setPayer(User payer) { this.payer = payer; }

    public User getRecipient() { return recipient; }

    public void setRecipient(User recipient) { this.recipient = recipient; }

    public Date getPaymentDate() { return paymentDate; }

    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate ;}

    public Integer getAmount() { return amount; }

    public void setAmount(Integer amount) { this.amount = amount; }

    public Date GetJavaDate() {
        Date javaDate = new Date(paymentDate.getTime());
        javaDate.setYear(javaDate.getYear()-1900);
        return javaDate;
    }

}