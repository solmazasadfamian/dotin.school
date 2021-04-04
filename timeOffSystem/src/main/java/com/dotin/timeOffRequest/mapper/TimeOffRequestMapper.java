package com.dotin.timeOffRequest.mapper;

import com.dotin.timeOffRequest.dao.CategoryElementDao;
import com.dotin.timeOffRequest.dao.EmployeeDao;
import com.dotin.timeOffRequest.dto.TimeOffRequestDto;
import com.dotin.timeOffRequest.entity.CategoryElement;
import com.dotin.timeOffRequest.entity.Employee;
import com.dotin.timeOffRequest.entity.TimeOffRequest;

public class TimeOffRequestMapper {
    private final CategoryElementDao categoryElementDao;
    private final EmployeeDao employeeDao;

    public TimeOffRequestMapper() {
        this.categoryElementDao = new CategoryElementDao();
        this.employeeDao = new EmployeeDao();
    }

    public TimeOffRequest toEntity(TimeOffRequestDto timeOffRequestDto) {
        TimeOffRequest timeOffRequest = new TimeOffRequest();
        timeOffRequest.setId(timeOffRequestDto.getId());
        if (timeOffRequestDto.getTimeOffStatusId() != null) {
            categoryElementDao.openCurrentSessionWithTransaction();
            CategoryElement status = categoryElementDao.getEntity(timeOffRequestDto.getTimeOffStatusId());
            categoryElementDao.closeCurrentSessionWithTransaction();
            timeOffRequest.setTimeOffStatus(status);
        }
        timeOffRequest.setTimeOffDayAmount(timeOffRequestDto.getTimeOffDayAmount());
        if (timeOffRequestDto.getEmployeeId() != null) {
            employeeDao.openCurrentSessionWithTransaction();
            Employee employee = employeeDao.getEntity(timeOffRequestDto.getEmployeeId());
            employeeDao.closeCurrentSessionWithTransaction();
            timeOffRequest.setEmployee(employee);
        }
        timeOffRequest.setActive(timeOffRequestDto.getActive());
        timeOffRequest.setEndTime(timeOffRequestDto.getEndTime());
        timeOffRequest.setStartTime(timeOffRequestDto.getStartTime());
        timeOffRequest.setDisabled(timeOffRequestDto.getDisabled());
        timeOffRequest.setVersion(timeOffRequestDto.getVersion());
        return timeOffRequest;
    }

    public TimeOffRequestDto toDto(TimeOffRequest timeOffRequest) {
        TimeOffRequestDto timeOffRequestDto = new TimeOffRequestDto();
        timeOffRequestDto.setId(timeOffRequest.getId());
        timeOffRequestDto.setActive(timeOffRequest.getActive());
        timeOffRequestDto.setDisabled(timeOffRequest.getDisabled());
        timeOffRequestDto.setEmployeeId(timeOffRequest.getEmployee() != null ? timeOffRequest.getEmployee().getId() : null);
        timeOffRequestDto.setEndTime(timeOffRequest.getEndTime());
        timeOffRequestDto.setStartTime(timeOffRequest.getStartTime());
        timeOffRequestDto.setTimeOffDayAmount(timeOffRequest.getTimeOffDayAmount());
        timeOffRequestDto.setTimeOffStatusId(timeOffRequest.getTimeOffStatus() != null ? timeOffRequest.getTimeOffStatus().getId() : null);
        timeOffRequestDto.setVersion(timeOffRequest.getVersion());
        return timeOffRequestDto;
    }
}
