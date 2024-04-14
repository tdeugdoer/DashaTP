package com.babich.laba6.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class BankEmployee {
    private static int count = 0;
    private int id;
    private String name;
    private int salary;
    private String gender;

    public BankEmployee() {}

    public BankEmployee(String name, int salary, String gender) {
        this.id = ++count;
        this.name = name;
        this.salary = salary;
        this.gender = gender;
    }

    public static ObservableList<BankEmployee> getListBankEmployee() {
        return FXCollections.observableArrayList(
                new BankEmployee("Арсений", 423, "Мужской"),
                new BankEmployee("Катя", 342, "Женский"),
                new BankEmployee("Лёша", 1000, "Мужской"),
                new BankEmployee("Елизавета", 9999, "Женский"),
                new BankEmployee("Ульяна", 854, "Женский")
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "BankEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", gender='" + gender + '\'' +
                '}';
    }
}