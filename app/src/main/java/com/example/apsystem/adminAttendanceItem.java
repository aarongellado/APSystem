package com.example.apsystem;

public class adminAttendanceItem {

    private String employee_id;
    private String date;
    private String time_in;
    private String time_out;
    private String siteName;

    public adminAttendanceItem(String employee_id, String date, String time_in, String time_out, String siteName) {
        this.employee_id = employee_id;
        this.date = date;
        this.time_in = time_in;
        this.time_out = time_out;
        this.siteName = siteName;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public String getDate() {
        return date;
    }

    public String getTime_in() {
        return time_in;
    }

    public String getTime_out() {
        return time_out;
    }

    public String getSiteName() {
        return siteName;
    }
}
