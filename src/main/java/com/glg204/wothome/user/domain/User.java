package com.glg204.wothome.user.domain;

import com.glg204.wothome.authentification.domain.WOTUser;

public class User {

    private Long id;

    private WOTUser wotUser;

    private String telephone;

    private String address;

    public User() {
    }

    public User(WOTUser wotUser) {
        this.wotUser = wotUser;
    }

    public User(WOTUser wotUser, String telephone, String address) {
        this.wotUser = wotUser;
        this.telephone = telephone;
        this.address = address;
    }

    public User(Long id, WOTUser wotUser, String telephone, String address) {
        this.id = id;
        this.wotUser = wotUser;
        this.telephone = telephone;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public WOTUser getWotUser() {
        return wotUser;
    }

    public void setWotUser(WOTUser wotUser) {
        this.wotUser = wotUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
