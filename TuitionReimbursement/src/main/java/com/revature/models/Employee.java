package com.revature.models;

import javax.annotation.Generated;
import javax.persistence.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name="employee")
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer remaining;
    private Integer pending;
    private Integer awarded;
    private Integer supervisor;
    private Integer department;
    private Boolean benCo;



    public Employee() {

    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Employee(Integer id, String username, String password, String firstName, String lastName, Integer remaining,
                    Integer pending, Integer awarded, Integer supervisor, Integer department,Boolean benCo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.remaining = remaining;
        this.pending = pending;
        this.awarded = awarded;
        this.supervisor = supervisor;
        this.department = department;
        this.benCo = benCo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Integer getAwarded() {
        return awarded;
    }

    public void setAwarded(Integer awarded) {
        this.awarded = awarded;
    }

    public Integer getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Integer supervisor) {
        this.supervisor = supervisor;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Boolean getBenCo() {
        return benCo;
    }

    public void setBenCo(Boolean benCo) {
        this.benCo = benCo;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", remaining=" + remaining + '\'' +
                ", pending=" + pending +
                ", awarded=" + awarded +
                ", supervisor=" + supervisor +
                ", department=" + department +
                ", benCo=" + benCo +
                '}';

    }

    public String toJSON() {
        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(this);
            return json;
        }

        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }
}
