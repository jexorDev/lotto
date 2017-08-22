package com.lotto.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private long purchaserUserId;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Ticket() { }

    public Ticket(long id) {
        this.id = id;
    }

    public Ticket(Integer cost, Date purchaseDate, long purchaserUserId) {
        this.cost = cost;
        this.purchaseDate = purchaseDate;
        this.purchaserUserId = purchaserUserId;
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

    public long getPurchaserUserId() {
        return purchaserUserId;
    }

    public void setPurchaserUserId(long purchaserUserId) {
        this.purchaserUserId = purchaserUserId;
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
        if (purchaserUserId == 1){
            return "Anthony";
        } else if (purchaserUserId == 2) {
            return "Dustin and Anthony";
        } else {
            return "Sean, Dustin, and Anthony";
        }
    }

} // class User