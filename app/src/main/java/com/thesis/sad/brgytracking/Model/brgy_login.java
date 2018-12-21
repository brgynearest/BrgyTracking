package com.thesis.sad.brgytracking.Model;

public class brgy_login {
    public String username;
    public String password;
    public String contacts;
    public String longitude;
    public String lattitude;


    public brgy_login(){


    }

    public brgy_login( String username, String password, String contacts, String longitude, String lattitude) {

        this.username = username;
        this.password = password;
        this.contacts = contacts;
        this.longitude = longitude;
        this.lattitude = lattitude;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getContacts() {
        return contacts;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLattitude() {
        return lattitude;
    }

}
