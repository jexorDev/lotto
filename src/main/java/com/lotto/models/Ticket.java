package com.lotto.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * An entity Ticket
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 *
 * @author des
 */
@Entity
@Table(name = "ticket")
public class Ticket {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    // An auto generated id (unique for each ticket in the db)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // The cost of the ticket
    @NotNull
    private Integer cost;

    // The date the ticket was purchased
    @NotNull
    private Date purchaseDate;

    // The id of the user who purchased the ticket
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User purchaserUser;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Ticket() { }

    public Ticket(long id) {
        this.id = id;
    }

    public Ticket(Integer cost, Date purchaseDate, User purchaserUser) {
        this.cost = cost;
        this.purchaseDate = purchaseDate;
        this.purchaserUser = purchaserUser;
    }

    // Getter and setter methods
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Date getPurchasedate() {
        return purchaseDate;
    }

    public void setPurchasedate(Date purchasedate) {
        this.purchaseDate = purchasedate;
    }

    public User getPurchaserUser() {
        return purchaserUser;
    }

    public void setPurchaserUser(User purchaserUser) {
        this.purchaserUser = purchaserUser;
    }
} // class User