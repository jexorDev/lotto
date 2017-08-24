package com.lotto.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * An entity Player
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 *
 * @author des
 */
@Entity
@Table(name = "player")
public class Player implements Serializable {


    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    // An auto generated id (unique for each ticket in the db)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // An auto generated id (unique for each ticket in the db)
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private Ticket ticket;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;


    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Player() { }

    public Player(Ticket ticket, User user) {
        this.ticket = ticket;
        this.user = user;
    }

    public long getId() { return id; }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

} // class Player