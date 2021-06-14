package com.sparta.karim.Model;

import com.sparta.karim.View.Starter;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeDAO {
    private final String createDatabase = "CREATE DATABASE mylocal";
    private final String useDatabase = "USE mylocal";

    private final String URL = "jdbc:mysql://localhost:3306/mylocal?serverTimezone=GMT";
    private final String dropTable = "DROP TABLE IF EXISTS employees";
    private final String createTable = "CREATE TABLE employees (" +
            "empId int PRIMARY KEY, " +
            "prefix varchar(5), " +
            "firstName varchar(15), " +
            "middleInitial varchar(1), " +
            "lastName varchar(15), " +
            "gender varchar(1), " +
            "email varchar(50), " +
            "dob varchar(30), " +
            "dateOfJoining varchar(30), " +
            "salary Long" +
            ");";

    private final String selectTableContents = "SELECT * FROM employees;";

    private final String insertIntoTable = "INSERT INTO employees VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static Properties properties = new Properties();
    private Connection connection;

    public int numberOfEmployeesInTable = 0;
    private static List<Integer> empIdPlaced = new ArrayList<>();
    //public int numberOfDuplicates = 0;
    public static int temp = 0;

    private Connection connectToDatabase(){
        try{
            properties.load(new FileReader("resources/login.properties"));
            connection = DriverManager.getConnection(URL, properties.getProperty("username"), properties.getProperty("password"));
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public void createTable (){
        try {
            Statement statement = connectToDatabase().createStatement();
            statement.executeUpdate(dropTable);
            statement.executeUpdate(createTable);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void enterTableValues(List<EmployeeDTO> employeeDTOS){

        try {
            PreparedStatement preparedStatement = connectToDatabase().prepareStatement(insertIntoTable);
            for (EmployeeDTO employeeDTO : employeeDTOS) {
                synchronized (empIdPlaced) {

                        if (empIdPlaced.contains(employeeDTO.empId)) {
                            temp++;
                        } else {
                            preparedStatement.setInt(1, employeeDTO.empId);
                            preparedStatement.setString(2, employeeDTO.prefix);
                            preparedStatement.setString(3, employeeDTO.firstName);
                            preparedStatement.setString(4, "" + employeeDTO.middleInitial);
                            preparedStatement.setString(5, employeeDTO.lastName);
                            preparedStatement.setString(6, "" + employeeDTO.gender);
                            preparedStatement.setString(7, employeeDTO.email);
                            preparedStatement.setString(8, employeeDTO.dob.toString());
                            preparedStatement.setString(9, employeeDTO.dateOfJoining.toString());
                            preparedStatement.setLong(10, employeeDTO.salary);

                            empIdPlaced.add(employeeDTO.empId);
                            numberOfEmployeesInTable++;
                            preparedStatement.addBatch();
                        }
                }
            }
            preparedStatement.executeBatch();
            } catch(Exception e){
            e.printStackTrace();
        }
            int skipped = EmployeeDTO.nullId.size();
            if (skipped > 0){
                System.out.printf("\nThere were %s employees with null data.\n", skipped);
            }

        Starter.numberOfDuplicates = temp;
    }

    public void setSelectingEmployees() {
        try {
            Statement statement = connectToDatabase().createStatement();
            ResultSet resultSet = statement.executeQuery(selectTableContents);

            if (resultSet != null) {
                while (resultSet.next()) {
                    System.out.println("Employee ID::" + resultSet.getInt(1));
                    System.out.println("Prefix::" + resultSet.getString(2));
                    System.out.println("First Name::" + resultSet.getString(3));
                    System.out.println("Middle Initial::" + resultSet.getString(4));
                    System.out.println("Last Name::" + resultSet.getString(5));
                    System.out.println("Gender::" + resultSet.getString(6));
                    System.out.println("Email::" + resultSet.getString(7));

                    System.out.println("Date Of Birth::" + resultSet.getString(8));
                    System.out.println("Date Of Joining::" + resultSet.getString(9));
                    System.out.println("Salary::" + resultSet.getLong(10));
                }
            } else {
                System.out.println("No records in the table");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
