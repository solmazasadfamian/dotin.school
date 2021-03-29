package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.TimeOffRequestDao;
import com.dotin.timeOffRequest.entity.CategoryElement;
import com.dotin.timeOffRequest.entity.Employee;
import com.dotin.timeOffRequest.entity.TimeOffRequest;
import com.dotin.timeOffRequest.exception.BadRequestException;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class TimeOffRequestService {
    private final static Logger log = Logger.getLogger(TimeOffRequestService.class.getName());
    private TimeOffRequestDao timeOffRequestDao;
    private CategoryElementService categoryElementService;
    private EmployeeService employeeService;

    public TimeOffRequestService() {
        timeOffRequestDao = new TimeOffRequestDao();
        categoryElementService = new CategoryElementService();
        employeeService = new EmployeeService();
    }

    public void preAdd(TimeOffRequest timeOffRequest) throws BadRequestException {
        log.info("object with below info for save has received : start time: " + timeOffRequest.getStartTime() + " end time:" + timeOffRequest.getEndTime());
        Employee employee = timeOffRequest.getEmployee();
        CategoryElement status = categoryElementService.findByCode(300L);
        timeOffRequest.setTimeOffStatus(status != null ? status : null);
        Boolean overlapEmployeeTimeOff = getOverlapEmployeeTimeOff(timeOffRequest.getStartTime(), timeOffRequest.getEndTime(), timeOffRequest.getEmployee().getId(), timeOffRequest.getId());
        if (overlapEmployeeTimeOff) {
            throw new BadRequestException("error.overlap", "the selected time has overlap with previous time offs");
        }
        if (timeOffRequest.getTimeOffDayAmount() > employee.getTimeOffBalance()) {
            throw new BadRequestException("error.capacity", "amount of time off is more than remain capacity");
        }
        if (timeOffRequest.getId() == null) {
            this.add(timeOffRequest);
            employee.setTimeOffBalance(employee.getTimeOffBalance() - timeOffRequest.getTimeOffDayAmount());
            employeeService.update(employee);
        } else {
            Integer beforeTimeOffDay = this.findById(timeOffRequest.getId()).getTimeOffDayAmount();
            this.update(timeOffRequest);
            employee.setTimeOffBalance((employee.getTimeOffBalance() + beforeTimeOffDay) - timeOffRequest.getTimeOffDayAmount());
            employeeService.update(employee);
        }
    }

    public void add(TimeOffRequest timeOffRequest) {
        log.info("object with below info for add has received : " + timeOffRequest.getStartTime() + " end time:" + timeOffRequest.getEndTime());
        timeOffRequestDao.openCurrentSessionWithTransaction();
        timeOffRequestDao.insert(timeOffRequest);
        timeOffRequestDao.closeCurrentSessionWithTransaction();
    }

    public void update(TimeOffRequest newTimeOffRequest) {
        log.info("object with below info for update has received : " + newTimeOffRequest.getStartTime() + " end time:" + newTimeOffRequest.getEndTime());
        timeOffRequestDao.openCurrentSessionWithTransaction();
        TimeOffRequest timeOffRequest = timeOffRequestDao.getEntity(newTimeOffRequest.getId());
        newTimeOffRequest.setVersion(timeOffRequest.getVersion());
        timeOffRequestDao.update(newTimeOffRequest);
        timeOffRequestDao.closeCurrentSessionWithTransaction();
    }

    public TimeOffRequest findById(Long id) {
        log.info("request with below id for find has received : " + id);
        timeOffRequestDao.openCurrentSessionWithTransaction();
        TimeOffRequest timeOffRequest = timeOffRequestDao.getEntity(id);
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        return timeOffRequest;
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        timeOffRequestDao.openCurrentSessionWithTransaction();
        TimeOffRequest timeOffRequest = timeOffRequestDao.getEntity(id);
        timeOffRequest.setActive(false);
        timeOffRequest.setDisabled(true);
        timeOffRequestDao.closeCurrentSessionWithTransaction();
    }

    public List<TimeOffRequest> findAll() {
        log.info("request for find all has received");
        timeOffRequestDao.openCurrentSessionWithTransaction();
        List<TimeOffRequest> timeOffRequestList = timeOffRequestDao.selectAll();
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        return timeOffRequestList;
    }

    public List<TimeOffRequest> findAllById(String string, Long id) {
        log.info("request for find all with below id has received : " + id);
        Criteria criteria = timeOffRequestDao.openCurrentSessionWithTransaction().createCriteria(TimeOffRequest.class);
        criteria.add(Restrictions.eq("active", true)).add(Restrictions.eq(string, id));
        List<TimeOffRequest> timeOffRequestList = criteria.list();
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        return timeOffRequestList;
    }

    public List<TimeOffRequest> findAllByManagerId(Long id) {
        log.info("request for find all with below id has received : " + id);
        List<TimeOffRequest> timeOffRequestList = timeOffRequestDao.openCurrentSessionWithTransaction().createCriteria(TimeOffRequest.class)
                .add(Restrictions.eq("active", true))
                .createAlias("employee", "employee")
                .createAlias("employee.manager", "manager")
                .add(Restrictions.eq("manager.id", id))
                .list();
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        return timeOffRequestList;
    }

    public Boolean getOverlapEmployeeTimeOff(String start, String end, Long empId, Long id) {
        log.info("request for get overlap employee time off with below employee id has received : " + empId);
        Boolean hasOverlap = false;
        timeOffRequestDao.openCurrentSessionWithTransaction();
        List<TimeOffRequest> timeOffRequestList = timeOffRequestDao.getOverlapEmployeeTimeOff(start, end, empId);
        if (id != null) {
            TimeOffRequest timeOfRequest = findById(id);
            for (TimeOffRequest tor : timeOffRequestList) {
                if (!tor.getId().equals(timeOfRequest.getId()))
                    return true;
            }
            return false;
        }
        timeOffRequestDao.closeCurrentSessionWithTransaction();
        return timeOffRequestList.size() > 0 ? true : false;
    }

    public Integer getTimeOffDayAmount(String startTime, String endTime) {
        log.info("request for get time off day amount has received");
        return Integer.valueOf(endTime.split("/")[2]) - Integer.valueOf(startTime.split("/")[2]);
    }


}
