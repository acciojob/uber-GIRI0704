package com.driver.model;

import org.mapstruct.IterableMapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    private String mobile;
    private String password;

    public Customer(Integer customerId, String mobile, String password) {
        this.customerId = customerId;
        this.mobile = mobile;
        this.password = password;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<TripBooking> tripBookingList = new ArrayList<>();

    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }
}