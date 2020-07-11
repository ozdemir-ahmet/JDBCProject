package com.ozdemir.model;

import java.sql.Date;

public class WorkDone {
    private int employeeId;
    private Employee employee;
    private int projectId;
    private Project project;
    private Date startOfWork;
    private Date endOfWork;
    private int hoursWorked;
    private String remarks;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getStartOfWork() {
        return startOfWork;
    }

    public void setStartOfWork(Date startOfWork) {
        this.startOfWork = startOfWork;
    }

    public Date getEndOfWork() {
        return endOfWork;
    }

    public void setEndOfWork(Date endOfWork) {
        this.endOfWork = endOfWork;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "WorkDone{" +
                "employee=" + employee.getName() + " " + employee.getLastName() +
                ", project='" + project.getName() +
                ", startOfWork=" + startOfWork +
                ", endOfWork= " + endOfWork +
                ", hourWorked= " + hoursWorked +
                ", remarks=" + remarks +
                '}';
    }

    public String getOneLine() {
        return "WorkDone{" +
                "employeeId=" + employeeId +
                ", projectId='" + projectId +
                ", startOfWork=" + startOfWork +
                ", endOfWork= " + endOfWork +
                ", hourWorked= " + hoursWorked +
                ", remarks=" + remarks +
                '}';
    }
}
