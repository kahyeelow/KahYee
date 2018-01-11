package com.tarcc.proin.proin.model;

/**
 * Created by yee_l on 11/1/2018.
 */

public class User {
    private String name;
    private String nric;
    private String dob;
    private String address;
    private String gender;
    private String phoneNo;
    private String email;
    private String occupation;
    private double salary;
    private String username;
    private String password;

    public User() {

    }

    public User(String name, String nric, String dob,
                String address, String gender, String phoneNo,
                String email, String occupation, double salary, String username, String password) {
        this.name = name;
        this.nric = nric;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.email = email;
        this.occupation = occupation;
        this.salary = salary;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
