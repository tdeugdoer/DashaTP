package com.babich.laba6.model;

import com.babich.laba6.BankEmployeeApplication;
import com.babich.laba6.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankEmployee {
    private int id;
    private String name;
    private int salary;
    private String gender;
    private int experience;
    private String department;

    public BankEmployee() {
    }

    public BankEmployee(String name, int salary, String gender, int experience, String department) {
        this.name = name;
        this.salary = salary;
        this.gender = gender;
        this.experience = experience;
        this.department = department;
    }

    public static ObservableList<BankEmployee> getListBankEmployee() {
        ObservableList<BankEmployee> employees = FXCollections.observableList(new ArrayList<>());

        String sql = "SELECT * FROM bank_employee";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                BankEmployee employee = new BankEmployee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSalary(resultSet.getInt("salary"));
                employee.setGender(resultSet.getString("gender"));
                employee.setExperience(resultSet.getInt("experience"));
                employee.setDepartment(resultSet.getString("department"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return employees;
    }

    public void save() {
        String sql = "INSERT INTO bank_employee (name, salary, gender, experience, department) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setInt(2, salary);
            statement.setString(3, gender);
            statement.setInt(4, experience);
            statement.setString(5, department);

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                id = generatedKeys.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update() {
        String sql = "UPDATE bank_employee SET name = ?, salary = ?, gender = ?, experience = ?, department = ? WHERE id = ?";
        System.out.println("Обновление");
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.println(this);
            statement.setString(1, name);
            statement.setInt(2, salary);
            statement.setString(3, gender);
            statement.setInt(4, experience);
            statement.setString(5, department);
            statement.setInt(6, id);
            statement.executeUpdate();
            System.out.println("Конец обновления");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete() {
        String sql = "DELETE FROM bank_employee WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "BankEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", gender='" + gender + '\'' +
                ", experience=" + experience +
                ", department='" + department + '\'' +
                '}';
    }
}