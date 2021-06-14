package com.sparta.karim.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//Model
public class EmployeeDTO {
    int empId;
    String prefix;
    String firstName;
    char middleInitial;
    String lastName;
    char gender;
    String email;
    Date dob;
    Date dateOfJoining;
    long salary;
    //public static int skipped = 0;
    public static List<String> nullId = new ArrayList<>();

    public EmployeeDTO(int empId, String prefix, String firstName, char middleInitial, String lastName, char gender, String email, Date dob, Date dateOfJoining, long salary) {
        this.empId = empId;
        this.prefix = prefix;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.dob = dob;
        this.dateOfJoining = dateOfJoining;
        this.salary = salary;
    }

    public EmployeeDTO(){
    }

    public EmployeeDTO(String line){
        String[] employeeDetails = line.split(",");
        List<String> employeeDetailsList = new ArrayList<>(Arrays.asList(employeeDetails));

        if(employeeDetailsList.contains("null")){
            System.out.println(".......................................Skipped: " + employeeDetailsList);
            nullId.add(employeeDetails[0]);;
        } else {
            try {
                empId = Integer.parseInt(employeeDetails[0]);
                prefix = employeeDetails[1];
                firstName = employeeDetails[2];
                middleInitial = employeeDetails[3].charAt(0);
                lastName = employeeDetails[4];
                gender = employeeDetails[5].charAt(0);
                email = employeeDetails[6];
                dob = new SimpleDateFormat("dd/MM/yyyy").parse(employeeDetails[7]);
                dateOfJoining = new SimpleDateFormat("dd/MM/yyyy").parse(employeeDetails[8]);
                salary = Integer.parseInt(employeeDetails[9]);

                //System.out.println(empId  + ", " + prefix  + ", " + firstName  + ", " + middleInitial  + ", " + lastName  + ", " + gender  + ", " + email  + ", " + dob  + ", " + dateOfJoining  + ", " + salary);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public char getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(char middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }
}
