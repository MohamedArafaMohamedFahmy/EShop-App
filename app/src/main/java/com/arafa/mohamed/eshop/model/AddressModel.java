package com.arafa.mohamed.eshop.model;

public class AddressModel {
    private String userName,city,address,mobileNumber,note;

    public AddressModel() {
    }

    public AddressModel(String userName, String city, String address, String mobileNumber, String note) {
        this.userName = userName;
        this.city = city;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.note = note;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
