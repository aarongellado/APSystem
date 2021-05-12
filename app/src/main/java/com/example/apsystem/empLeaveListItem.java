package com.example.apsystem;

public class empLeaveListItem {

    private String empLeaveStart;
    private String empLeaveEnd;
    private String empLeaveStatus;
    private String empLeaveType;

    public String getEmpLeaveStart() {
        return empLeaveStart;
    }

    public String getEmpLeaveEnd() {
        return empLeaveEnd;
    }

    public String getEmpLeaveStatus() {
        return empLeaveStatus;
    }

    public String getEmpLeaveType() {
        return empLeaveType;
    }



    public empLeaveListItem(String empLeaveStart, String empLeaveEnd, String empLeaveStatus, String empLeaveType) {
        this.empLeaveStart = empLeaveStart;
        this.empLeaveEnd = empLeaveEnd;
        this.empLeaveStatus = empLeaveStatus;
        this.empLeaveType = empLeaveType;
    }


}
