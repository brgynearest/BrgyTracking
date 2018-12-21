package com.thesis.sad.brgytracking.Model;

public class information_of_user {
    public String firstname;
    public String lastname;
    public String age;
    public String contact_num;
    public String address;
    public String guardian;
    public String guardian_number;


    public information_of_user(){

    }
    public information_of_user(String firstname, String lastname, String age, String contact_num, String address, String guardian, String guardian_number) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.contact_num = contact_num;
        this.address = address;
        this.guardian = guardian;
        this.guardian_number = guardian_number;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAge() {
        return age;
    }

    public String getContact_num() {
        return contact_num;
    }

    public String getAddress() {
        return address;
    }

    public String getGuardian() {
        return guardian;
    }

    public String getGuardian_number() {
        return guardian_number;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setContact_num(String contact_num) {
        this.contact_num = contact_num;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public void setGuardian_number(String guardian_number) {
        this.guardian_number = guardian_number;
    }
}
