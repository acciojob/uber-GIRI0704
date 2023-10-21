package com.driver.model;

import javax.persistence.*;

@Entity
@Table
public class TripBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tripBookingId;
    private String fromLocation;
    private String toLocation;
    private int distanceInKm;
    @Enumerated(value = EnumType.STRING)
    private TripStatus tripStatus;
    private int bill;

    public TripBooking(Integer tripBookingId, String fromLocation, String toLocation, int distanceInKm, TripStatus tripStatus, int bill) {
        this.tripBookingId = tripBookingId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKm = distanceInKm;
        this.tripStatus = tripStatus;
        this.bill = bill;
    }

    public TripBooking() {
    }

    public Integer getTripBookingId() {
        return tripBookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public int getDistanceInKm() {
        return distanceInKm;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public int getBill() {
        return bill;
    }

    public void setTripBookingId(Integer tripBookingId) {
        this.tripBookingId = tripBookingId;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public void setDistanceInKm(int distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Driver driver;

    public Customer getCustomer() {
        return customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}