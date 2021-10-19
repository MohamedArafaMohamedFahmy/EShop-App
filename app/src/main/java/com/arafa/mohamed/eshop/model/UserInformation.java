package com.arafa.mohamed.eshop.model;

public class UserInformation {
   private String userName,emailAddress;

    public UserInformation()
    {

    }

    public UserInformation(String userName, String emailAddress){
        this.userName=userName;
        this.emailAddress=emailAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
