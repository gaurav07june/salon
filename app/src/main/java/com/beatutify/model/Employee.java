package com.beatutify.model;

import java.io.Serializable;

/**
 * Created by karan.kalsi on 3/22/2018.
 */

public class Employee implements Serializable {
    private int employeeId;
    private String employeeName;



    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return employeeId == employee.employeeId;

    }

    @Override
    public int hashCode() {
        return employeeId;
    }
}
