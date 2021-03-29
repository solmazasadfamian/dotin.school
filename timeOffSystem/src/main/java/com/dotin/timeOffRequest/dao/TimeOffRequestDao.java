package com.dotin.timeOffRequest.dao;

import com.dotin.timeOffRequest.entity.TimeOffRequest;

import java.util.List;

public class TimeOffRequestDao extends GenericDaoImpl<TimeOffRequest, Long> {

    public List<TimeOffRequest> getOverlapEmployeeTimeOff(String start, String end, Long empId) {
        return (List<TimeOffRequest>) getCurrentSession()
                .createQuery("FROM TimeOffRequest AS tor WHERE tor.startTime BETWEEN :stDate AND :edDate and employee.id = :emp or  tor.endTime BETWEEN :stDate AND :edDate and employee.id = :emp ")
                .setParameter("stDate", start)
                .setParameter("edDate", end)
                .setParameter("emp", empId)
                .getResultList();
    }
}

