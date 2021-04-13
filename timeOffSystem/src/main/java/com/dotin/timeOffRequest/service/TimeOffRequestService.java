package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.TimeOffRequestDao;
import com.dotin.timeOffRequest.dto.CategoryElementDto;
import com.dotin.timeOffRequest.dto.EmployeeDto;
import com.dotin.timeOffRequest.dto.TimeOffRequestDto;
import com.dotin.timeOffRequest.entity.Employee;
import com.dotin.timeOffRequest.entity.TimeOffRequest;
import com.dotin.timeOffRequest.exception.BadRequestException;
import com.dotin.timeOffRequest.exception.ErrorMessages;
import com.dotin.timeOffRequest.mapper.EmployeeMapper;
import com.dotin.timeOffRequest.mapper.TimeOffRequestMapper;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        Boolean dayOffType = true;
        if (timeOffRequestDto.getStartTime() != null)
            dayOffType = false;
        Boolean overlapEmployeeTimeOff = getOverlapEmployeeTimeOff(timeOffRequestDto.getStartDate(), timeOffRequestDto.getEndDate(), timeOffRequestDto.getEmployeeId(), timeOffRequestDto.getId(), dayOffType, timeOffRequestDto.getStartTime(), timeOffRequest.getEndTime());
        if (overlapEmployeeTimeOff) {
            throw new BadRequestException(ErrorMessages.OVERLAP_ERROR_CODE, ErrorMessages.OVERLAP_ERROR_MESSAGE);
        }
        if (timeOffRequestDto.getTimeOffDayAmount() * 8 > employee.getTimeOffBalance()) {
            throw new BadRequestException(ErrorMessages.CAPACITY_ERROR_CODE, ErrorMessages.CAPACITY_ERROR_MESSAGE);
        }
        if (timeOffRequestDto.getId() == null) {
            this.add(timeOffRequestDto);
            if (timeOffRequestDto.getDateTime() == 5) {
                employee.setTimeOffBalance(employee.getTimeOffBalance() - timeOffRequestDto.getTimeOffDayAmount() * 8);
            } else if (timeOffRequestDto.getDateTime() == 6) {
                Integer hourDiff = Integer.valueOf(timeOffRequest.getEndTime().split(":")[0]) - Integer.valueOf(timeOffRequest.getStartTime().split(":")[0]);
                Integer minuteDiff = Integer.valueOf(timeOffRequest.getEndTime().split(":")[1]) - Integer.valueOf(timeOffRequest.getStartTime().split(":")[1]);
                employee.setTimeOffBalance(employee.getTimeOffBalance() - (hourDiff + (minuteDiff / 60.0)));
            }
            employeeService.update(employeeMapper.toDto(employee));

        } else {
            if (timeOffRequestDto.getDateTime() == 5) {
                Integer beforeTimeOffDay = this.findById(timeOffRequestDto.getId()).getTimeOffDayAmount() * 8;
                this.update(timeOffRequestDto);
                employee.setTimeOffBalance((employee.getTimeOffBalance() + beforeTimeOffDay) - timeOffRequestDto.getTimeOffDayAmount() * 8);
            } else if (timeOffRequestDto.getDateTime() == 6) {
                TimeOffRequestDto oldTimeOffRequestDto = this.findById(timeOffRequestDto.getId());
                Integer oldHourDiff = Integer.valueOf(oldTimeOffRequestDto.getEndTime().split(":")[0]) - Integer.valueOf(oldTimeOffRequestDto.getStartTime().split(":")[0]);
                Integer oldMinuteDiff = Integer.valueOf(oldTimeOffRequestDto.getEndTime().split(":")[1]) - Integer.valueOf(oldTimeOffRequestDto.getStartTime().split(":")[1]);
                this.update(timeOffRequestDto);
                Integer hourDiff = Integer.valueOf(timeOffRequest.getEndTime().split(":")[0]) - Integer.valueOf(timeOffRequest.getStartTime().split(":")[0]);
                Integer minuteDiff = Integer.valueOf(timeOffRequest.getEndTime().split(":")[1]) - Integer.valueOf(timeOffRequest.getStartTime().split(":")[1]);
                employee.setTimeOffBalance(employee.getTimeOffBalance() + (oldHourDiff + (oldMinuteDiff / 60.0)) - (hourDiff + (minuteDiff / 60.0)));
            }
            employeeService.update(employeeMapper.toDto(employee));
        }
    }


    public TimeOffRequestDto add(TimeOffRequestDto timeOffRequestDto) {
        log.info("object with below info for add has received : " + timeOffRequestDto.getStartTime() + " end time:" + timeOffRequestDto.getEndTime());
        Session session = timeOffRequestDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            TimeOffRequest timeOffRequest = timeOffRequestMapper.toEntity(timeOffRequestDto);
            TimeOffRequest saveTimeOffRequest = timeOffRequestDao.insert(timeOffRequest);
            transaction.commit();
            return timeOffRequestMapper.toDto(saveTimeOffRequest);
        } finally {
            session.close();
        }
    }

    public void update(TimeOffRequestDto timeOffRequestDto) {
        log.info("object with below info for update has received : " + timeOffRequestDto.getStartTime() + " end time:" + timeOffRequestDto.getEndTime());
        Session session = timeOffRequestDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            TimeOffRequest timeOffRequest = timeOffRequestDao.getEntity(timeOffRequestDto.getId());
            timeOffRequestDto.setVersion(timeOffRequest.getVersion());
            timeOffRequestDao.update(timeOffRequestMapper.toEntity(timeOffRequestDto));
            transaction.commit();
        } finally {
            session.close();
        }
    }

    public TimeOffRequestDto findById(Long id) {
        log.info("request with below id for find has received : " + id);
        Session session = timeOffRequestDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            TimeOffRequest timeOffRequest = timeOffRequestDao.getEntity(id);
            transaction.commit();
            return timeOffRequestMapper.toDto(timeOffRequest);
        } finally {
            session.close();
        }
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        Session session = timeOffRequestDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            TimeOffRequest timeOffRequest = timeOffRequestDao.getEntity(id);
            timeOffRequest.setActive(false);
            timeOffRequest.setDisabled(true);
            Employee employee = timeOffRequest.getEmployee();
            if (timeOffRequest.getDateTime() != null & timeOffRequest.getDateTime().getCode() == 500) {
                employee.setTimeOffBalance((employee.getTimeOffBalance()) + ((timeOffRequest.getTimeOffDayAmount() * 8)));
            } else if (timeOffRequest.getDateTime() != null & timeOffRequest.getDateTime().getCode() == 600) {
                Integer hourDiff = Integer.valueOf(timeOffRequest.getEndTime().split(":")[0]) - Integer.valueOf(timeOffRequest.getStartTime().split(":")[0]);
                Integer minuteDiff = Integer.valueOf(timeOffRequest.getEndTime().split(":")[1]) - Integer.valueOf(timeOffRequest.getStartTime().split(":")[1]);
                employee.setTimeOffBalance(employee.getTimeOffBalance() + (hourDiff + (minuteDiff / 60.0)));
            }
            transaction.commit();
            employeeService.update(employeeMapper.toDto(employee));
        } finally {
            session.close();
        }
    }

    public List<TimeOffRequestDto> findAll() {
        log.info("request for find all has received");
        Session session = timeOffRequestDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            List<TimeOffRequest> timeOffRequestList = timeOffRequestDao.selectAll();
            List<TimeOffRequestDto> timeOffRequestDtoList = new ArrayList<>();
            for (TimeOffRequest timeOffRequest : timeOffRequestList) {
                timeOffRequestDtoList.add(timeOffRequestMapper.toDto(timeOffRequest));
            }
            transaction.commit();
            return timeOffRequestDtoList;
        } finally {
            session.close();
        }
    }

    public List<TimeOffRequestDto> findAllById(String string, Long id) {
        log.info("request for find all with below id has received : " + id);
        Session session = timeOffRequestDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(TimeOffRequest.class);
            criteria.add(Restrictions.eq("active", true)).add(Restrictions.eq(string, id));
            List<TimeOffRequest> timeOffRequestList = criteria.list();
            List<TimeOffRequestDto> timeOffRequestDtoList = new ArrayList<>();
            for (TimeOffRequest timeOffRequest : timeOffRequestList) {
                timeOffRequestDtoList.add(timeOffRequestMapper.toDto(timeOffRequest));
            }
            transaction.commit();
            return timeOffRequestDtoList;
        } finally {
            session.close();
        }
    }

    public List<TimeOffRequestDto> findAllByManagerId(Long id) {
        log.info("request for find all with below id has received : " + id);
        Session session = timeOffRequestDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            List<TimeOffRequest> timeOffRequestList = session.createCriteria(TimeOffRequest.class)
                    .add(Restrictions.eq("active", true))
                    .createAlias("employee", "employee")
                    .createAlias("employee.manager", "manager")
                    .add(Restrictions.eq("manager.id", id))
                    .list();
            List<TimeOffRequestDto> timeOffRequestDtoList = new ArrayList<>();
            for (TimeOffRequest timeOffRequest : timeOffRequestList) {
                timeOffRequestDtoList.add(timeOffRequestMapper.toDto(timeOffRequest));
            }
            transaction.commit();
            return timeOffRequestDtoList;
        } finally {
            session.close();
        }
    }

    public Boolean getOverlapEmployeeTimeOff(String start, String end, Long empId, Long id, Boolean dayOff, String startTime, String endTime) {
        log.info("request for get overlap employee time off with below employee id has received : " + empId);
        Session session = timeOffRequestDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            List<TimeOffRequest> timeOffRequestList = timeOffRequestDao.getOverlapEmployeeTimeOff(start, end, empId);
            if (id != null) {
                TimeOffRequestDto timeOffRequestDto = findById(id);
                for (TimeOffRequest tor : timeOffRequestList) {
                    if (!tor.getId().equals(timeOffRequestDto.getId()))
                        return true;
                }
                return false;
            }
            transaction.commit();
            return timeOffRequestList.size() > 0;
        } finally {
            session.close();
        }
    }

    public void updateEmployeeBalance(TimeOffRequestDto timeOffRequestDto) {
        EmployeeDto employeeDto = employeeService.findById(timeOffRequestDto.getEmployeeId());
        if (timeOffRequestDto.getDateTime() == 5) {
            employeeDto.setTimeOffBalance(employeeDto.getTimeOffBalance() + timeOffRequestDto.getTimeOffDayAmount() * 8);
        } else if (timeOffRequestDto.getDateTime() == 6) {
            Integer hourDiff = Integer.valueOf(timeOffRequestDto.getEndTime().split(":")[0]) - Integer.valueOf(timeOffRequestDto.getStartTime().split(":")[0]);
            Integer minuteDiff = Integer.valueOf(timeOffRequestDto.getEndTime().split(":")[1]) - Integer.valueOf(timeOffRequestDto.getStartTime().split(":")[1]);
            employeeDto.setTimeOffBalance(employeeDto.getTimeOffBalance() + (hourDiff + (minuteDiff / 60.0)));
        }
        employeeService.update(employeeDto);
    }
}
