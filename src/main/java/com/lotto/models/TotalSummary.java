package com.lotto.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * An entity TotalSummary
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 *
 * @author des
 */
@Entity
@Table(name = "total")
public class TotalSummary implements Serializable {


    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    // An auto generated id (unique for each ticket in the db)
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private User fromUser;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private User toUser;

    private int amount;


    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public TotalSummary() { }

    public TotalSummary(User fromUser, User toUser, int amount) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

} // class TotalSummary