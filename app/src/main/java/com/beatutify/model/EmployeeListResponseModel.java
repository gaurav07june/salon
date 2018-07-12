package com.beatutify.model;

import java.util.List;

/**
 * Created by karan.kalsi on 3/22/2018.
 */

public class EmployeeListResponseModel
{
    private List<Employee> employeeList;

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
