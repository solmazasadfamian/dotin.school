package com.dotin.timeOffRequest.dao;

import com.dotin.timeOffRequest.entity.TimeOffRequest;

import java.util.List;

public class TimeOffRequestDao extends GenericDaoImpl<TimeOffRequest, Long> {

    public List<TimeOffRequest> getOverlapEmployeeTimeOff(String start, String end, Long empId) {
        return (List<TimeOffRequest>) getCurrentSession()
                .createQuery("FROM TimeOffRequest AS tor WHERE " +
                        "(tor.startDate BETWEEN :stDate AND :edDate " +
                        "or tor.endDate BETWEEN :stDate AND :edDate " +
                        "or :stDate BETWEEN tor.startDate AND tor.endDate " +
                        "or :edDate BETWEEN tor.startDate AND tor.endDate)" +
                        " and employee.id = :emp and tor.active = true and tor.timeOffStatus.code in (200,300)")
                .setParameter("stDate", start)
                .setParameter("edDate", end)
                .setParameter("emp", empId)
                .getResultList();
    }
}

