package com.driver.model;

import javax.persistence.*;

@Entity
@Table
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminIn;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;

    public Admin(Integer adminIn, String userName, String password) {
        this.adminIn = adminIn;
        this.userName = userName;
        this.password = password;
    }

    public Integer getAdminIn() {
        return adminIn;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setAdminIn(Integer adminIn) {
        this.adminIn = adminIn;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}