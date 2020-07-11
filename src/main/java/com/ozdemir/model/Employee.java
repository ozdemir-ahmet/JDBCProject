package com.ozdemir.model;

import java.sql.Date;

public class Employee {
    private int id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String phoneNumberICE;
    private Date dateOfBirth;
    private double salary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumberICE() {
        return phoneNumberICE;
    }

    public void setPhoneNumberICE(String phoneNumberICE) {
        this.phoneNumberICE = phoneNumberICE;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name +
                ", lastName=" + lastName +
                ", phoneNumber= " + phoneNumber +
                ", phoneNumberICE= " + phoneNumberICE +
                ", dateOfBirth=" + dateOfBirth +
                ", salary=" + salary +
                '}';
    }

}
