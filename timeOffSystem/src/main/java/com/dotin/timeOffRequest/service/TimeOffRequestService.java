package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.TimeOffRequestDao;
import com.dotin.timeOffRequest.dto.CategoryElementDto;
import com.dotin.timeOffRequest.dto.EmployeeDto;
import com.dotin.timeOffRequest.dto.TimeOffRequestDto;
import com.dotin.timeOffRequest.entity.Employee;
import com.dotin.timeOffRequest.entity.TimeOffRequest;
import com.dotin.timeOffRequest.exception.BadRequestException;
import com.dotin.timeOffRequest.mapper.EmployeeMapper;
import com.dotin.timeOffRequest.mapper.TimeOffRequestMapper;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class TimeOffRequestService {
    private final static Logger log = Logger.getLogger(TimeOffRequestService.class.getName());
    private final TimeOffRequestDao timeOffRequestDao;
    private final CategoryElementService categoryElementService;
    private final EmployeeService employeeService;
    private final TimeOffRequestMapper timeOffRequestMapper;
    private final EmployeeMapper employeeMapper;

    public TimeOffRequestService() {
        timeOffRequestDao = new TimeOffRequestDao();
        categoryElementService = new CategoryElementService();
        employeeService = new EmployeeService();
        timeOffRequestMapper = new TimeOffRequestMapper();
        employeeMapper = new EmployeeMapper();
    }

    public void preAdd(TimeOffRequestDto timeOffRequestDto) throws BadRequestException {
        log.info("object with below info for save has received : start time: " + timeOffRequestDto.getStartTime() + " end time:" + timeOffRequestDto.getEndTime());
        TimeOffRequest timeOffRequest = timeOffRequestMapper.toEntity(timeOffRequestDto);
        Employee employee = timeOffRequest.getEmployee();
        CategoryElementDto statusDto = categoryElementService.findByCode(300L);
        timeOffRequestDto.setTimeOffStatusId(statusDto != null ? statusDto.getId() : null);
        Boolean overlapEmployeeTimeOff = getOverlapEmployeeTimeOff(timeOffRequestDto.getStartTime(), timeOffRequestDto.getEndTime(), timeOffRequestDto.getEmployeeId(), timeOffRequestDto.getId());
        if (overlapEmployeeTimeOff) {
            throw new BadRequestException("error.overlap", "the selected time has overlap with previous time offs");
        }
        if (timeOffRequestDto.getTimeOffDayAmount() > employee.getTimeOffBalance()) {
            throw new BadRequestException("error.capacity", "amount of time off is more than remain capacity");
        }
        if (timeOffRequestDto.getId() == null) {
            this.add(timeOffRequestDto);
            employee.setTimeOffBalance(employee.getTimeOffBalance() - timeOffRequestDto.getTimeOffDayAmount());
            employeeService.update(employeeMapper.toDto(employee));
        } else {
            Integer beforeTimeOffDay = this.findById(timeOffRequestDto.getId()).getTimeOffDayAmount();
            this.update(timeOffRequestDto);
            employee.setTimeOffBalance((employee.getTimeOffBalance() + beforeTimeOffDay) - timeOffRequestDto.getTimeOffDayAmount());
            employeeService.update(employeeMapper.toDto(employee));
        }
    }

    public TimeOffRequestDto add(TimeOffRequestDto timeOffRequestDto) {
        log.info("object with below info for add has received : " + timeOffRequestDto.getStartTime() + " end time:" + timeOffRequestDto.getEndTime());
        timeOffRequestDao.openCurrentSessionWithTransaction();
        TimeOffRequest timeOffRequest = timeOffRequestMapper.toEntity(timeOffRequestDto);
        TimeOffRequest saveTimeOffRequest = timeOffRequestDao.insert(timeOffRequest);
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        return timeOffRequestMapper.toDto(saveTimeOffRequest);
    }

    public void update(TimeOffRequestDto timeOffRequestDto) {
        log.info("object with below info for update has received : " + timeOffRequestDto.getStartTime() + " end time:" + timeOffRequestDto.getEndTime());
        timeOffRequestDao.openCurrentSessionWithTransaction();
        TimeOffRequest timeOffRequest = timeOffRequestDao.getEntity(timeOffRequestDto.getId());
        timeOffRequestDto.setVersion(timeOffRequest.getVersion());
        timeOffRequestDao.update(timeOffRequestMapper.toEntity(timeOffRequestDto));
        timeOffRequestDao.closeCurrentSessionWithTransaction();
    }

    public TimeOffRequestDto findById(Long id) {
        log.info("request with below id for find has received : " + id);
        timeOffRequestDao.openCurrentSessionWithTransaction();
        TimeOffRequest timeOffRequest = timeOffRequestDao.getEntity(id);
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        return timeOffRequestMapper.toDto(timeOffRequest);
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        timeOffRequestDao.openCurrentSessionWithTransaction();
        TimeOffRequest timeOffRequest = timeOffRequestDao.getEntity(id);
        timeOffRequest.setActive(false);
        timeOffRequest.setDisabled(true);
        Employee employee = timeOffRequest.getEmployee();
        employee.setTimeOffBalance((employee.getTimeOffBalance()) + (timeOffRequest.getTimeOffDayAmount()));
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        employeeService.update(employeeMapper.toDto(employee));
    }

    public List<TimeOffRequestDto> findAll() {
        log.info("request for find all has received");
        timeOffRequestDao.openCurrentSessionWithTransaction();
        List<TimeOffRequest> timeOffRequestList = timeOffRequestDao.selectAll();
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        List<TimeOffRequestDto> timeOffRequestDtoList = new ArrayList<>();
        for (TimeOffRequest timeOffRequest : timeOffRequestList) {
            timeOffRequestDtoList.add(timeOffRequestMapper.toDto(timeOffRequest));
        }
        return timeOffRequestDtoList;
    }

    public List<TimeOffRequestDto> findAllById(String string, Long id) {
        log.info("request for find all with below id has received : " + id);
        Criteria criteria = timeOffRequestDao.openCurrentSessionWithTransaction().createCriteria(TimeOffRequest.class);
        criteria.add(Restrictions.eq("active", true)).add(Restrictions.eq(string, id));
        List<TimeOffRequest> timeOffRequestList = criteria.list();
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        List<TimeOffRequestDto> timeOffRequestDtoList = new ArrayList<>();
        for (TimeOffRequest timeOffRequest : timeOffRequestList) {
            timeOffRequestDtoList.add(timeOffRequestMapper.toDto(timeOffRequest));
        }
        return timeOffRequestDtoList;
    }

    public List<TimeOffRequestDto> findAllByManagerId(Long id) {
        log.info("request for find all with below id has received : " + id);
        List<TimeOffRequest> timeOffRequestList = timeOffRequestDao.openCurrentSessionWithTransaction().createCriteria(TimeOffRequest.class)
                .add(Restrictions.eq("active", true))
                .createAlias("employee", "employee")
                .createAlias("employee.manager", "manager")
                .add(Restrictions.eq("manager.id", id))
                .list();
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        List<TimeOffRequestDto> timeOffRequestDtoList = new ArrayList<>();
        for (TimeOffRequest timeOffRequest : timeOffRequestList) {
            timeOffRequestDtoList.add(timeOffRequestMapper.toDto(timeOffRequest));
        }
        return timeOffRequestDtoList;
    }

    public Boolean getOverlapEmployeeTimeOff(String start, String end, Long empId, Long id) {
        log.info("request for get overlap employee time off with below employee id has received : " + empId);
        timeOffRequestDao.openCurrentSessionWithTransaction();
        List<TimeOffRequest> timeOffRequestList = timeOffRequestDao.getOverlapEmployeeTimeOff(start, end, empId);
        if (id != null) {
            TimeOffRequestDto timeOffRequestDto = findById(id);
            for (TimeOffRequest tor : timeOffRequestList) {
                if (!tor.getId().equals(timeOffRequestDto.getId()))
                    return true;
            }
            return false;
        }
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        return timeOffRequestList.size() > 0;
    }

    public void updateEmployeeBalance(TimeOffRequestDto timeOffRequestDto) {
        EmployeeDto employeeDto = employeeService.findById(timeOffRequestDto.getEmployeeId());
        employeeDto.setTimeOffBalance(employeeDto.getTimeOffBalance() + timeOffRequestDto.getTimeOffDayAmount());
        employeeService.update(employeeDto);
    }
}
