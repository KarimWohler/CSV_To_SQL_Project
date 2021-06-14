package com.sparta.karim.Model;

import com.sparta.karim.Model.EmployeeDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModelSQL{
    public static long startTime;

    public static List<EmployeeDTO> readAndCreateEmployeeObjects() throws Exception{
        return ReturnCreatedEmployeeObjects(readResources());
    }

    public static List<String> readResources() throws Exception{
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like to enter the small or large Employee database?");
        System.out.println("Kindly enter 1 for small, 2 for large");

        startTime = System.nanoTime();

        List<String> lines = new ArrayList<>();
        String userOption = "";
        int repeat = 0;
        int option = 0;

        try {
            while(repeat == 0) {
                    option = input.nextInt();
                    if (option == 1) {
                        userOption = "resources/employees.csv";
                        repeat++;
                    } else if (option == 2) {
                        userOption = "resources/EmployeeRecordsLarge.csv";
                        repeat++;
                    } else {
                        System.out.println("Sorry, that is not a valid option");
                        System.out.println("Kindly try again");
                    }
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(userOption));
            String line = csvReader.readLine();
            do {
                lines.add(line);
                line = csvReader.readLine();

            } while(line!=null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  lines;
    }



    public static List<EmployeeDTO> ReturnCreatedEmployeeObjects(List<String> employeeDetails){
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        int firstSkip = 0;

        for(String employee : employeeDetails){
            if (firstSkip == 0){
            } else {
                employeeDTOS.add(new EmployeeDTO(employee));
            }
            firstSkip++;
        }
        return employeeDTOS;
    }


}
