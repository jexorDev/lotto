package com.lotto.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * An entity Payment
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 *
 * @author jtc
 */


@Entity
@Table(name = "payment")
public class Payment {

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

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Payment() {}

    public Payment(long id) { this.id = id; }

    public Payment(Integer payerId, Integer recipientId) {
        this.payerId = payerId;
        this.recipientId = recipientId;
    }

    // Get-set methods

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Integer getPayerId() { return payerId; }

    public void setPayerId(Integer payerId) { this.payerId = payerId; }

    public Integer getRecipientId() { return recipientId; }

    public void setRecipientId(Integer recipientId) { this.recipientId = recipientId; }




}