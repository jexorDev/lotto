package com.lotto.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * An entity TotalSummary
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 *
 * @author des
 */
@Entity
@Table(name = "total")
public class TotalSummary{
    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // the first user
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private User userA;

    // the second user
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private User userB;

    // Amount of the payment
    @NotNull
    private Integer amount;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public TotalSummary() { }

    public TotalSummary(User userA, User userB, int amount) {
        this.userA = userA;
        this.userB = userB;
        this.amount = amount;
    }

    public User getUserA() {
        return userA;
    }

    public void setUserA(User userA) {
        this.userA = userA;
    }

    public User getUserB() {
        return userB;
    }

    public void setUserB(User userB) {
        this.userB = userB;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPredicament() {

        String predicament;

        if (amount < 0) {
            predicament = "You owe " + userB.getUserName() + " " + Math.abs(amount);
        }
        else if (amount > 0) {
            predicament = userB.getUserName() + " owes you " + amount;
        }
        else {
            predicament = "You do not have a balance with " + userB.getUserName();
        }

        return predicament;

    }

} // class TotalSummary