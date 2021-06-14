package com.sparta.karim.View;

import com.sparta.karim.Model.EmployeeDAO;
import com.sparta.karim.Model.ModelSQL;
import com.sparta.karim.Controller.Threaded;

import java.util.ArrayList;
import java.util.List;


public class Starter {
    public static int numberOfEmployeesAdded;
    public static int numberOfDuplicates = 0;
    public static int threadCounter = 0;

    public void start(){
        EmployeeDAO employeeDAO = new EmployeeDAO();

        employeeDAO.createTable();
        this.runParallel();


        long endTime = System.nanoTime();
        float timeTakenInMilli = (float)(endTime - ModelSQL.startTime)/1000000;

        System.out.println("\nTime taken to read then write all Employee Object Values into SQL: " + timeTakenInMilli + " Milli-seconds");
        int seconds = Math.round(timeTakenInMilli/1000);
        double secondsTO1DP = Math.round((timeTakenInMilli/1000.0) * 10);
        if (seconds < 60){
            System.out.println("                                         > Which is approximately: " + secondsTO1DP / 10.0 + " Seconds");
        } else {
            System.out.println("                                         > Which is approximately: " + seconds / 60 + " Minutes");
            System.out.println("                                                      & " + seconds % 60 + " Seconds");
        }

        System.out.printf("Number of Employees Added: %s\nNumber of Duplicates: %s\n", numberOfEmployeesAdded, numberOfDuplicates);
    }

    public void runParallel(){
        List<String> employeeString = new ArrayList<>();
        int set = 1000;

        try {
            employeeString = ModelSQL.readResources();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        if (employeeString.size() > 20000){
            set = 1500;
        }
        
        int numberOfSets = employeeString.size() / set;

        List<Threaded> threads = new ArrayList<>();
        for (int i = 0; i < numberOfSets; i++){
            int j = i*set;
            List<String> employeeS = employeeString.subList(j,j+(set+1/*-1*/));
            Threaded newThread = new Threaded(employeeS);
            newThread.start();
            threads.add(newThread);
        }
        Threaded lastThread = new Threaded(employeeString.subList((numberOfSets)*set, employeeString.size()));
        lastThread.start();
        threads.add(lastThread);
        try {
                for (Threaded t : threads) {
                    t.join();
                }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
