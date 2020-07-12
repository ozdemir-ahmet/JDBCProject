package com.ozdemir.service;

import com.ozdemir.data.EmployeeDAO;
import com.ozdemir.data.NonUniqueResultException;
import com.ozdemir.data.ProjectDAO;
import com.ozdemir.data.WorkDoneDAO;
import com.ozdemir.model.Employee;
import com.ozdemir.model.Project;
import com.ozdemir.model.WorkDone;
import java.sql.SQLException;
import java.util.LinkedList;
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

    public Optional<WorkDone> getWorkDoneByFKs(int employeeId, int projectId) throws SQLException, NonUniqueResultException {
        Optional<WorkDone> optionalWorkDone = workDoneDAO.getWorkDoneByFKs (employeeId, projectId);
        return optionalWorkDone;
    }

    public void updateWorkDone(WorkDone workDone) throws SQLException{
        workDoneDAO.updateWorkDone (workDone);
    }

    public void deleteByFKs(int employeeId, int projectId) throws SQLException{
        workDoneDAO.deleteByFKs (employeeId, projectId);
    }

    public List<WorkDone> getWorkDoneByProjectId(int projectId) throws SQLException{
        return workDoneDAO.getWorkDoneByProjctId (projectId);
    }

    public List<WorkDone> getTopWorkDonesByProjectId(int projectId) throws SQLException, NonUniqueResultException{
        EmployeeDAO employeeDAO = new EmployeeDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        List<WorkDone> topWorkDones = workDoneDAO.getTopWorkDonesByProjectId (projectId);
        if (topWorkDones.isEmpty()){
            System.out.println("No such project or no one worked in this project yet");
        } else {
            for(WorkDone w : topWorkDones){
                Optional<Employee> employee = employeeDAO.getEmployeeById(w.getEmployeeId());
                Optional<Project> project = projectDAO.getProjectById(w.getProjectId());
                w.setEmployee(employee.get());
                w.setProject(project.get());
            }
        }
        return topWorkDones;
    }
}
