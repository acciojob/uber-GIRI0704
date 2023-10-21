package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driverId;
    private String mobile;
    private String password;

    public Driver() {
    }

    public Driver(Integer driverId, String mobile, String password) {
        this.driverId = driverId;
        this.mobile = mobile;
        this.password = password;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    private Cab cab;

    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    List<TripBooking> tripBookingList = new ArrayList<>();

    public Cab getCab() {
        return cab;
    }

    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }
}