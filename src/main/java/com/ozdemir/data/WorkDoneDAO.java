package com.ozdemir.data;

import com.ozdemir.model.Employee;
import com.ozdemir.model.Project;
import com.ozdemir.model.WorkDone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkDoneDAO {

    public Optional<WorkDone> getWorkDoneByFKs(int employeeId, int projectId) throws  SQLException, NonUniqueResultException{
        EmployeeDAO employeeDAO = new EmployeeDAO();
        ProjectDAO projectDAO = new ProjectDAO();

        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM workDone WHERE employeeId = ? and projectId = ?");

        statement.setInt(1, employeeId);
        statement.setInt(2,projectId);

        ResultSet rs = statement.executeQuery();
        List<WorkDone> workDones = parseWorkDones(rs);

        if (workDones.size() == 0) return Optional.empty();
        if (workDones.size() == 1) {
            Optional<Employee> optionalEmployee = employeeDAO.getEmployeeById(employeeId);
            workDones.get(0).setEmployee(optionalEmployee.get());

            Optional<Project> optionalProject = projectDAO.getProjectById(projectId);
            workDones.get(0).setProject(optionalProject.get());

            return Optional.of(workDones.get(0));
        }

        throw new NonUniqueResultException("Multiple results for the given employee and project IDs");
    }

    public List<WorkDone> getAllWorkDones() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM workDone");

        return parseWorkDones(rs);
    }

    private List<WorkDone> parseWorkDones(ResultSet rs) throws SQLException{
        List<WorkDone> result = new ArrayList<>();
        while (rs.next()) {
            WorkDone workDone = new WorkDone();
            workDone.setEmployeeId(rs.getInt("employeeId"));
            workDone.setProjectId(rs.getInt("projectId"));
            workDone.setStartOfWork(rs.getDate("startOfWork"));
            workDone.setEndOfWork(rs.getDate("endOfWork"));
            workDone.setHoursWorked(rs.getInt("hoursWorked"));
            workDone.setRemarks(rs.getString("remarks"));

            result.add(workDone);
        }
        return result;
    }

    public void saveWorkDone(WorkDone workDone) throws SQLException{
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO workDone values (?,?,?,?,?,?)");

        statement.setInt(1, workDone.getEmployeeId());
        statement.setInt(2, workDone.getProjectId());
        statement.setDate(3, workDone.getStartOfWork());
        statement.setDate(4, workDone.getEndOfWork());
        statement.setInt(5, workDone.getHoursWorked());
        statement.setString(6, workDone.getRemarks());

        System.out.println(statement.executeUpdate() + " work done saved");
    }

    public void updateWorkDone(WorkDone workDone) throws SQLException{
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement
                ("UPDATE workDone SET startOfWork = ?," +
                        "endOfWork = ?, hoursWorked = ?, remarks = ? where employeeId = ? AND projectId = ?");


        statement.setDate(1, workDone.getStartOfWork());
        statement.setDate(2, workDone.getEndOfWork());
        statement.setInt(3, workDone.getHoursWorked());
        statement.setString(4,workDone.getRemarks());
        statement.setInt(5,workDone.getEmployeeId());
        statement.setInt(6,workDone.getProjectId());

        System.out.println(statement.executeUpdate()+" work done updated");
    }
}
