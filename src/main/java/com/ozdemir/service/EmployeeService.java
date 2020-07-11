package com.ozdemir.service;

import com.ozdemir.data.EmployeeDAO;
import com.ozdemir.data.NonUniqueResultException;
import com.ozdemir.model.Employee;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public List<Employee> getEmployeeByLastName(String lastName) throws SQLException{
        return employeeDAO.getEmployeeByLastName(lastName);
    }

    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDAO.getAllEmployees();
    }

    public void saveEmployee(Employee employee) throws SQLException {
        employeeDAO.saveEmployee(employee);
    }

    public Optional<Employee> getEmployeeById(int id) throws SQLException, NonUniqueResultException {
        return employeeDAO.getEmployeeById(id);
    }

    public void updateEmployee(Employee employee) throws SQLException{
        employeeDAO.updateEmployee (employee);
    }

    public void deleteById(int id) throws SQLException{
        employeeDAO.deleteById(id);
    }

    public List<Employee> getEmployeeByDOB(int numDays) throws SQLException{
        return employeeDAO.getEmployeeByDOB (numDays);
    }
}
