package com.dotin.timeOffRequest.dto;

public class TimeOffRequestDto {
    private Long id;
    private String startTime;
    private String endTime;
    private Integer timeOffDayAmount = 0;
    private Long employeeId;
    private Long timeOffStatusId;
    private Boolean disabled = false;
    private Boolean active = true;
    private Integer version;

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

    public Integer getTimeOffDayAmount() {
        return timeOffDayAmount;
    }

    public void setTimeOffDayAmount(Integer timeOffDayAmount) {
        this.timeOffDayAmount = timeOffDayAmount;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getTimeOffStatusId() {
        return timeOffStatusId;
    }

    public void setTimeOffStatusId(Long timeOffStatusId) {
        this.timeOffStatusId = timeOffStatusId;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "TimeOffRequestDto{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", timeOffDayAmount=" + timeOffDayAmount +
                ", employeeId=" + employeeId +
                ", timeOffStatusId=" + timeOffStatusId +
                ", disabled=" + disabled +
                ", active=" + active +
                ", version=" + version +
                '}';
    }
}
