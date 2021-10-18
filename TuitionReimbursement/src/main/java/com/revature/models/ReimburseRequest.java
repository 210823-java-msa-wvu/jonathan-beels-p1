package com.revature.models;


import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.annotation.Generated;
import javax.persistence.*;

//import java.sql.Date;
//import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="request")
public class ReimburseRequest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer employeeId;
    private Integer amount;
    private String eventType;
    private Date eventDate;
    private String eventTime;
    private String location;
    private String description;
    private String gradingFormat;
    private String grade;
    private Boolean dsApproval;
    private Boolean dhApproval;
    private Boolean benCoApproval;
    private String justification;
    private Boolean urgent;
    private Boolean additionalInfo;
    private Boolean rejected;

    public ReimburseRequest() {

    }

    public ReimburseRequest(Integer id, Integer employeeId, Integer amount, String eventType, Date eventDate, String eventTime,
                            String location, String description, String gradingFormat, String grade, Boolean dsApproval,
                            Boolean dhApproval, Boolean benCoApproval, String justification, Boolean urgent, Boolean additionalInfo, Boolean rejected) {
        this.id = id;
        this.employeeId = employeeId;
        this.amount = amount;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.location = location;
        this.description = description;
        this.gradingFormat = gradingFormat;
        this.grade = grade;
        this.dsApproval = dsApproval;
        this.dhApproval = dhApproval;
        this.benCoApproval = benCoApproval;
        this.justification =justification;
        this.urgent = urgent;
        this.additionalInfo = additionalInfo;
        this.rejected = rejected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGradingFormat() {
        return gradingFormat;
    }

    public void setGradingFormat(String gradingFormat) {
        this.gradingFormat = gradingFormat;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Boolean getDsApproval() {
        return dsApproval;
    }

    public void setDsApproval(Boolean dsApproval) {
        this.dsApproval = dsApproval;
    }

    public Boolean getDhApproval() {
        return dhApproval;
    }

    public void setDhApproval(Boolean dhApproval) {
        this.dhApproval = dhApproval;
    }

    public Boolean getBenCoApproval() {
        return benCoApproval;
    }

    public void setBenCoApproval(Boolean benCoApproval) {
        this.benCoApproval = benCoApproval;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public Boolean getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Boolean additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    @Override
    public String toString() {
        return "ReimburseRequest{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", amount=" + amount +
                ", eventType='" + eventType + '\'' +
                ", eventDate=" + eventDate +
                ", eventTime=" + eventTime +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", gradingFormat='" + gradingFormat + '\'' +
                ", grade='" + grade + '\'' +
                ", dsApproval=" + dsApproval +
                ", dhApproval=" + dhApproval +
                ", benCoApproval=" + benCoApproval +
                ", justification='" + justification + '\'' +
                ", urgent='" + urgent + '\'' +
                '}';
    }
}
