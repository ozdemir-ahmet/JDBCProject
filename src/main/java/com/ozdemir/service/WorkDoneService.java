package com.ozdemir.service;

import com.ozdemir.data.EmployeeDAO;
import com.ozdemir.data.ProjectDAO;
import com.ozdemir.data.WorkDoneDAO;
import com.ozdemir.model.Employee;
import com.ozdemir.model.WorkDone;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class WorkDoneService {
    private WorkDoneDAO workDoneDAO = new WorkDoneDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private ProjectDAO projectDAO = new ProjectDAO();

    public List<WorkDone> getAllWorkDones() throws SQLException {
        return workDoneDAO.getAllWorkDones();
    }

    public void saveWorkDone(WorkDone workDone) throws SQLException{
        workDoneDAO.saveWorkDone(workDone);
    }
}
