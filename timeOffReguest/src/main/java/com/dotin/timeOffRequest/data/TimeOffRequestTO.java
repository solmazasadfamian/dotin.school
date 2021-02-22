package com.dotin.timeOffRequest.data;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TimeOffRequestTO {

    private Long id;
    private Date startTime;
    private Date endTime;

    private EmployeeTO employee;

    private CategoryElementTO timeOffStatus;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EmployeeTO.class, optional = false)
    @JoinColumn(name = "id")
    public EmployeeTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeTO employee) {
        this.employee = employee;
    }

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CategoryElementTO.class, optional = false)
    @JoinColumn(name = "id")
    public CategoryElementTO getTimeOffStatus() {
        return timeOffStatus;
    }

    public void setTimeOffStatus(CategoryElementTO categoryElement) {
        this.timeOffStatus = categoryElement;
    }
}
