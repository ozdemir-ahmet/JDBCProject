package com.ozdemir.data;

import com.ozdemir.model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO {
    public List<Employee> getAllEmployees() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM employee");

        return parseEmployees(rs);
    }

    public List<Employee> getEmployeeByLastName(String lastName) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE lastName = ?");
        statement.setString(1, lastName);

        ResultSet rs = statement.executeQuery();
        return parseEmployees(rs);
    }

    public Optional<Employee> getEmployeeById(int id) throws SQLException, NonUniqueResultException {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE id = ?");
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        List<Employee> employees = parseEmployees(rs);

        if (employees.size() == 0) return Optional.empty();
        if (employees.size() == 1) return Optional.of(employees.get(0));

        throw new NonUniqueResultException("Found multiple results for id: " + id);
    }

    public void saveEmployee(Employee employee) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO employee values (?,?,?,?,?,?,?)");

        statement.setInt(1,employee.getId());
        statement.setString(2, employee.getName());
        statement.setString(3, employee.getLastName());
        statement.setString(4, employee.getPhoneNumber());
        statement.setString(5,employee.getPhoneNumberICE());
        statement.setDate(6,employee.getDateOfBirth());
        statement.setDouble(7,employee.getSalary());

        System.out.println(statement.executeUpdate()+" employee saved");
    }

    public void updateEmployee(Employee employee) throws SQLException{
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement
                ("UPDATE employee set name = ?, lastName = ?, phoneNumber = ?," +
                "phoneNumberICE = ?, dateOfBirth = ?, salary = ? where id = ?");


        statement.setString(1, employee.getName());
        statement.setString(2, employee.getLastName());
        statement.setString(3, employee.getPhoneNumber());
        statement.setString(4,employee.getPhoneNumberICE());
        statement.setDate(5,employee.getDateOfBirth());
        statement.setDouble(6,employee.getSalary());
        statement.setInt(7,employee.getId());

        System.out.println(statement.executeUpdate()+" employee updated");
    }

    public void deleteById(int id) throws SQLException{
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement
                ("DELETE from employee where id = ?");

        statement.setInt(1, id);

        System.out.println(statement.executeUpdate() + " employee deleted");
    }

    public List<Employee> getEmployeeByDOB(int numDays) throws SQLException{
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM  employee WHERE  DATE_ADD(dateOfBirth, INTERVAL YEAR(CURDATE())-YEAR(dateOfBirth)" +
                     " + IF(DAYOFYEAR(CURDATE()) > DAYOFYEAR(dateOfBirth),0,0)" +
                     " YEAR)  BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY)");

        statement.setInt(1, numDays);

        ResultSet rs = statement.executeQuery();
        return parseEmployees(rs);
    }

    private List<Employee> parseEmployees(ResultSet rs) throws SQLException {
        List<Employee> result = new ArrayList<>();
        while (rs.next()) {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setLastName(rs.getString("lastName"));
            employee.setPhoneNumber(rs.getString("phoneNumber"));
            employee.setPhoneNumberICE(rs.getString("phoneNumberICE"));
            employee.setDateOfBirth(rs.getDate("dateOfBirth"));
            employee.setSalary(rs.getDouble("salary"));
            result.add(employee);
        }

        return result;
    }
}
