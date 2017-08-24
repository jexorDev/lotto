package com.lotto.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * An entity Ticket
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 *
 * @author des
 */
@Entity
@Table(name = "ticket")
public class Ticket implements Comparable {

    @Override
    public int compareTo(Object o) {
        return this.purchaseDate.compareTo(((Ticket)o).purchaseDate);
    }
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

    // The user who purchased the ticket
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private User purchaserUser;

    // Whether the ticket is a powerPlay
    @NotNull
    private Boolean powerPlay;

    // The list of picks associated with the ticket
    @NotNull
    @OneToMany(mappedBy="ticket")
    private Collection<Pick> picks;

    // The list of picks associated with the ticket
    @OneToMany
    private Collection<Player> players;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Ticket() { }

    public Ticket(Integer cost, Date purchaseDate, Boolean powerPlay, User purchaserUser, Collection<Pick> picks) {
        this.cost = cost;
        this.purchaseDate = purchaseDate;
        this.powerPlay = powerPlay;
        this.purchaserUser = purchaserUser;
        this.picks = picks;
        this.players = new ArrayList<Player>();
    }

    public Iterable<Pick> GetPicks(){
        return picks;
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

    public Boolean getPowerPlay() { return powerPlay; }

    public void setPowerPlay(Boolean powerPlay) { this.powerPlay = powerPlay; }

    public User getPurchaserUser() {
        return purchaserUser;
    }

    public void setPurchaserUser(User purchaserUser) {
        this.purchaserUser = purchaserUser;
    }

    public Date GetJavaDate() {
        Date javaDate = new Date(purchaseDate.getTime());
        javaDate.setYear(javaDate.getYear()-1900);
        return javaDate;
    }

    public Date GetDrawDate() {
        Date javaDate = GetJavaDate();

        // the draw dates are either Wednesday (3) or Saturday (6)
        int dow = javaDate.getDay() <= 3 ? 3 : 6;

        // find the number of days that need to be added to the current date to get the draw date
        int diff = dow - javaDate.getDay();

        // return the draw date
        return AddDays(javaDate, diff);
    }

    public Date AddDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public void addPlayers(List<Player> players) {
        this.players.addAll(players);
    }

    // TODO make this actually get the players
    public String GetPlayers(){
        if (purchaserUser.getId() == 1){
            return "Anthony";
        } else if (purchaserUser.getId() == 2) {
            return "Dustin and Anthony";
        } else {
            return "Sean, Dustin, and Anthony";
        }
    }

} // class User