package pl.sdacademy.jdbcexample.JDBCzadania;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private PreparedStatement psCreate;
    private PreparedStatement psReadById;
    private PreparedStatement psReadAll;
    private PreparedStatement psUpdate;
    private PreparedStatement psDelete;

    public EmployeeRepository(Connection connection) throws SQLException {
        psCreate = connection.prepareStatement("INSERT INTO employee (first_name, last_name, year_of_birth) " +
                "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        psReadById = connection.prepareStatement("SELECT " +
                "first_name," +
                "last_name," +
                "year_of_birth" +
                " FROM " +
                "employee " +
                "WHERE id = ?");
        psReadAll = connection.prepareStatement("SELECT " +
                "first_name," +
                "last_name," +
                "year_of_birth" +
                " FROM " +
                "employee ");
    }

    public void create(Employee employee) throws SQLException {
        psCreate.setString(1, employee.getFirstName());
        psCreate.setString(2, employee.getLastName());
        psCreate.setInt(3, employee.getYearOfBirth());
        psCreate.execute();
        ResultSet generatedKeys = psCreate.getGeneratedKeys();
        generatedKeys.next();
        int id = generatedKeys.getInt(1);
        employee.setId(id);
    }

    public Employee readById(int id) throws SQLException {
        psReadById.setInt(1, id);
        ResultSet resultSet = psReadById.executeQuery();
        if (resultSet.next()) {
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            int year_of_birth = resultSet.getInt("year_of_birth");
            return new Employee(first_name, last_name, year_of_birth);
        } else {
            System.out.println("Nie ma pracownika o id " + id);
            return null;
        }
    }

    public List<Employee> readAll() throws SQLException {
        ResultSet resultSet = psReadAll.executeQuery();
        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            employeeList.add(new Employee(resultSet.getString("first_name"), resultSet.getString("last_name"),
                    resultSet.getInt("year_of_birth")));
        } return employeeList;
    }
}
