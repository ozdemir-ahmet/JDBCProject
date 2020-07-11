package com.ozdemir.service;


import com.ozdemir.data.NonUniqueResultException;
import com.ozdemir.data.ProjectDAO;
import com.ozdemir.model.Project;

import java.sql.SQLException;
import java.util.Optional;

public class ProjectService {
    private ProjectDAO projectDAO = new ProjectDAO();

    public void saveProject(Project project) throws SQLException {
        projectDAO.saveProject(project);
    }

    public Optional<Project> getProjectById(int id) throws SQLException, NonUniqueResultException {
        return projectDAO.getProjectById (id);
    }

    public void deleteById(int id) throws SQLException{
        projectDAO.deleteById (id);
    }
}
