package com.hotel.hotel.message.pojos;

public class Guest {

    private int id;
    private String firstName;
    private String lastName;
    private Reservation reservation;
    public Guest() {
        // empty constructor
    }

    public Guest(int id, String firstName, String lastName, Reservation reservation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
