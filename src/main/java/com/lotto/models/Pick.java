package com.lotto.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * An entity Pick
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 *
 * @author des
 */
@Entity
@Table(name = "pick")
public class Pick {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    // An auto generated id (unique for each ticket in the db)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // The first pick
    @NotNull
    private Integer pick1;

    // The second pick
    @NotNull
    private Integer pick2;

    // The third pick
    @NotNull
    private Integer pick3;

    // The fourth pick
    @NotNull
    private Integer pick4;

    // The fifth pick
    @NotNull
    private Integer pick5;

    // The powerBall pick
    @NotNull
    private Integer powerBall;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Pick() { }

    public Pick(long id) {
        this.id = id;
    }

    public Pick(Integer pick1, Integer pick2, Integer pick3, Integer pick4, Integer pick5, Integer powerBall){
        this.pick1 = pick1;
        this.pick2 = pick2;
        this.pick3 = pick3;
        this.pick4 = pick4;
        this.pick5 = pick5;
        this.powerBall = powerBall;
    }

    // Getter and setter methods
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getPick1(){
        return pick1;
    }

    public void setPick1(Integer pick1){
        this.pick1 = pick1;
    }

    public Integer getPick2(){
        return pick2;
    }

    public void setPick2(Integer pick2){
        this.pick2 = pick2;
    }

    public Integer getPick3(){
        return pick3;
    }

    public void setPick3(Integer pick3){
        this.pick3 = pick3;
    }

    public Integer getPick4(){
        return pick4;
    }

    public void setPick4(Integer pick4){
        this.pick4 = pick4;
    }

    public Integer getPick5(){
        return pick5;
    }

    public void setPick5(Integer pick5){
        this.pick5 = pick5;
    }

    public Integer getPowerBall(){
        return powerBall;
    }

    public void setPowerBall(Integer powerBall){
        this.powerBall = powerBall;
    }

} // class Pick