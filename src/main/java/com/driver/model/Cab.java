package com.driver.model;

import javax.persistence.*;

@Entity
@Table
public class Cab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cabId;
    private int perKmRate;

    private boolean available;

    public Cab(Integer cabId, int perKmRate, boolean available) {
        this.cabId = cabId;
        this.perKmRate = perKmRate;
        this.available = available;
    }

    public Integer getCabId() {
        return cabId;
    }

    public int getPerKmRate() {
        return perKmRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setCabId(Integer cabId) {
        this.cabId = cabId;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @OneToOne
    @JoinColumn
    private Driver driver;
}