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

    // The user who purchased the ticket
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private User purchaserUser;

    // The list of picks associated with the ticket
    @NotNull
    @OneToMany(mappedBy="ticket")
    private Collection<Pick> picks;

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

        // TODO: delete this while removing random picks
        this.picks = new ArrayList<Pick>();
        for (int i = 0; i <= randomNumber(5); i++) {
            picks.add(randomPick());
        }
    }

    // TODO: delete this while removing random picks
    private Pick randomPick(){
        ArrayList<Integer> picks = new ArrayList<Integer>();

        int needed = 5;

        while (needed > 0){
            int newNumber = randomNumber(69);
            if (!picks.contains(newNumber)){
                picks.add(newNumber);
                needed--;
            }
        }

        int powerBall = randomNumber(26);

        return new Pick(picks.get(0), picks.get(1), picks.get(2), picks.get(3), picks.get(4), powerBall);
    }

    // TODO: delete this while removing random picks
    private Integer randomNumber(Integer max){
        Random r = new Random();
        int result = r.nextInt(max + 1);

        if (result > 0 && result <= max){
            return result;
        } else {
            return randomNumber(max);
        }
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