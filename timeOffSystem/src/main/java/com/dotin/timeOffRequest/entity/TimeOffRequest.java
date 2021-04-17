package com.dotin.timeOffRequest.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_timeOffRequest")
public class TimeOffRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long id;
    @Column(name = "c_startDate")
    private String startDate;
    @Column(name = "c_endDate")
    private String endDate;
    @Column(name = "c_startTime")
    private String startTime;
    @Column(name = "c_endTime")
    private String endTime;
    @Column(name = "c_timeOffDayAmount")
    private Integer timeOffDayAmount = 0;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Employee.class, optional = false)
    @JoinColumn(name = "c_employee")
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CategoryElement.class, optional = false)
    @JoinColumn(name = "c_timeOffStatus")
    private CategoryElement timeOffStatus;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CategoryElement.class, optional = false)
    @JoinColumn(name = "requestType")
    private CategoryElement requestType;
    @Column(name = "c_disabled")
    private Boolean disabled = false;
    @Column(name = "c_active")
    private Boolean active = true;
    @Version
    @Column(name = "c_version")
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startTime) {
        this.startDate = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endTime) {
        this.endDate = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public CategoryElement getTimeOffStatus() {
        return timeOffStatus;
    }

    public void setTimeOffStatus(CategoryElement timeOffStatus) {
        this.timeOffStatus = timeOffStatus;
    }

    public CategoryElement getRequestType() {
        return requestType;
    }

    public void setRequestType(CategoryElement requestType) {
        this.requestType = requestType;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean active) {
        this.disabled = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean deleted) {
        this.active = deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getTimeOffDayAmount() {
        return timeOffDayAmount;
    }

    public void setTimeOffDayAmount(Integer timeOffDayAmount) {
        this.timeOffDayAmount = timeOffDayAmount;
    }
}
