package com.aerotravel.flightticketbooking.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "verify_passenger")
public class VerifyPassenger {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Flight flight;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private Passenger passenger;
    @ManyToOne
    private User user;

    public VerifyPassenger(){

    }
    public VerifyPassenger(Flight flight, Passenger passenger, User user){
        this.flight=flight;
        this.passenger=passenger;
        this.user=user;
    }
    public long getId() {
        return id;
    }

    public void getId(long id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}