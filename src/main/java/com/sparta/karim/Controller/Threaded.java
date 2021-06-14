package com.sparta.karim.Controller;

import com.sparta.karim.Model.EmployeeDAO;
import com.sparta.karim.Model.EmployeeDTO;
import com.sparta.karim.Model.ModelSQL;
import com.sparta.karim.View.Starter;

import java.util.List;

public class Threaded extends Thread{
    List<String> employeesString;

    public float timeTaken;



    public Threaded(List<String> employeesString){
        Starter.threadCounter++;
        this.employeesString = employeesString;
    }


    @Override
    public void run(){
        List<EmployeeDTO> employeeDTOS = ModelSQL.ReturnCreatedEmployeeObjects(employeesString);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.enterTableValues(employeeDTOS);
        Starter.numberOfEmployeesAdded += employeeDAO.numberOfEmployeesInTable;
        //System.out.println(employeeDAO.numberOfEmployeesInTable);

    }

}


// Will use a split up of a string
// so that the creation of the employee objects
// as well as the writing to the database can both be done
// concurrently


//
//    @Override
//    public void run(){
//        try {
//            ModelSQL newModel = new ModelSQL();
//
//            List<String> employeeList = newModel.readResources();
//
//            List<Employee> employees = new ArrayList<>();
//            int numberOfSetsOf100 = employeeList.size() / 100 + 1;
//            if (numberOfSetsOf100 % 100 == 0) {
//                numberOfSetsOf100--;
//            }
//
//            int counter = 0;
//
//            for (int i = 0; i < employeeList.size(); i += 100) {
//                ModelSQL newThread = new ModelSQL();
//                counter++;
//                employees.addAll(newThread.ReturnCreatedEmployeeObjects(employeeList.subList(i, i + 100)));
//            }
//
//            ModelSQL newThread = new ModelSQL();
//            employees.addAll(newThread.ReturnCreatedEmployeeObjects(employeeList.subList(counter * 100, employeeList.size() - 1)));
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        return employees;
//    }