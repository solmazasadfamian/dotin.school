package com.dotin.timeOffRequest.data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_timeOffRequest")
public class TimeOffRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;
    @Column(name = "c_startTime")
    private String startTime;
    @Column(name = "c_endTime")
    private String endTime;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Employee.class, optional = false)
    @JoinColumn(name = "c_employee")
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CategoryElement.class, optional = false)
    @JoinColumn(name = "c_timeOffStatus")
    private CategoryElement timeOffStatus;
    @Column(name = "c_active")
    private Boolean active = true;
    @Column(name = "c_deleted")
    private Boolean deleted = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
