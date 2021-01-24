package pl.sdacademy.jdbcexample.JDBCzadania;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Statement statement;



    public static void main(String[] args) throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("NigdyWiecej12!");
        dataSource.setDatabaseName("company");
        dataSource.setServerTimezone("UTC");

        try (Connection connection = dataSource.getConnection()) {
            statement = connection.createStatement();
//            zadanie1();
//            zadanie2("Anna", "Annowska", 1982);
//            zadanie3(5);
//            System.out.println(zadanie5(5));
//            System.out.println(zadanie6());
            zadanie7(new Employee( "Bonzo", "Bonzowski", 1982));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void zadanie1() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT first_name, last_name FROM employee");
        while (resultSet.next()) {
            System.out.println("Imię: " + resultSet.getString("first_name") +
                    ", Nazwisko: " + resultSet.getString("last_name"));
        }
    }

    private static void zadanie2(String firstName, String lastName, int yearOfBirth) throws SQLException {
        String sql = String.format("INSERT INTO employee (first_name, last_name, year_of_birth) " +
                "VALUES ('%s', '%s', %d)", firstName, lastName, yearOfBirth);
        System.out.println(sql);
        statement.execute(sql);
    }

    private static void zadanie3(int id) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employee WHERE id = " + id);
        if (resultSet.next()) {
            System.out.println("Id: " + resultSet.getInt("id"));
            System.out.println("Imię: " + resultSet.getString("first_name"));
            System.out.println("Nazwisko: " + resultSet.getString("last_name"));
            System.out.println("Department id: " + resultSet.getInt("department_id"));
            System.out.println("Job id: " + resultSet.getInt("job_id"));
        } else {
            System.out.println("Nie ma pracownika o ID: " + id);
        }
    }

    private static Employee zadanie5(int id) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employee WHERE id = " + id);
        Employee employee = new Employee(id, "", "", 0);
        if (resultSet.next()) {
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setYearOfBirth(resultSet.getInt("year_of_birth"));
        } else {
            System.out.println("Nie ma pracownika o id: " + id);
            return null;
        }
        return employee;
    }

    private static List<Employee> zadanie6() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
        while (resultSet.next()) {
            employees.add(new Employee(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getInt(4)));
        }
        return employees;
    }

    public static void zadanie7(Employee employee) throws SQLException {
                    String sql = String.format("INSERT INTO employee (first_name, last_name, year_of_birth) " +
                    "VALUES('%s', '%s', %d)", employee.getFirstName(), employee.getLastName(), employee.getYearOfBirth());
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            employee.setId(id);
            System.out.println("Utworzono nowego pracownika o id " + id);

    }
}



