package com.ozdemir.data;

import com.ozdemir.model.Employee;
import com.ozdemir.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectDAO {
    public void saveProject(Project project) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO project values (?,?,?,?,?,?)");

        statement.setInt(1, project.getId());
        statement.setString(2, project.getName());
        statement.setDate(3, project.getStartDate());
        statement.setString(4, project.getDescription());
        statement.setDouble(5, project.getPrice());
        statement.setDate(6, project.getExpectedEndDate());

        System.out.println(statement.executeUpdate() + " project saved");
    }

    public Optional<Project> getProjectById(int id) throws SQLException, NonUniqueResultException {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM project WHERE id = ?");
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        List<Project> projects = parseProject(rs);

        if (projects.size() == 0) return Optional.empty();
        if (projects.size() == 1) return Optional.of(projects.get(0));

        throw new NonUniqueResultException("Found multiple results for id: " + id);
    }

    private List<Project> parseProject(ResultSet rs) throws SQLException{
        List<Project> result = new ArrayList<>();
        while (rs.next()) {
            Project project = new Project();
            project.setId(rs.getInt("id"));
            project.setName(rs.getString("name"));
            project.setStartDate(rs.getDate("startDate"));
            project.setDescription(rs.getString("description"));
            project.setPrice(rs.getDouble("price"));
            project.setExpectedEndDate(rs.getDate("expectedEndDate"));

            result.add(project);
        }
        return result;
    }

    public void deleteById(int id) throws SQLException{
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement
                ("DELETE from project where id = ?");

        statement.setInt(1, id);

        System.out.println(statement.executeUpdate() + " project deleted");
    }
}
