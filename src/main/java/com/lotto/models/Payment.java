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

    // ID of the payer
    @NotNull
    private Integer payerId;

    // ID of the recipient
    @NotNull
    private Integer recipientId;

    // Date of the payment
    @NotNull
    private Date paymentDate;

    // Amount of the payment
    @NotNull
    private Double amount;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Payment() {}

    public Payment(long id) { this.id = id; }

    public Payment(Integer payerId, Integer recipientId, Date paymentDate, Double amount) {
        this.payerId = payerId;
        this.recipientId = recipientId;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    // Get-set methods

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Integer getPayerId() { return payerId; }

    public void setPayerId(Integer payerId) { this.payerId = payerId; }

    public Integer getRecipientId() { return recipientId; }

    public void setRecipientId(Integer recipientId) { this.recipientId = recipientId; }

    public Date getPaymentDate() { return paymentDate; }

    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate ;}

    public Double getAmount() { return amount; }

    public void setAmount(Double amount) { this.amount = amount; }

    public Date GetJavaDate() {
        Date javaDate = new Date(paymentDate.getTime());
        javaDate.setYear(javaDate.getYear()-1900);
        return javaDate;
    }

}