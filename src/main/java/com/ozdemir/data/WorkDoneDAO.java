package com.ozdemir.data;

import com.ozdemir.model.WorkDone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkDoneDAO {

    public List<WorkDone> getAllWorkDones() throws SQLException {
        Connection conn = ConnectionFactory.getConnection();

        Statement statement = conn.createStatement();
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
}
