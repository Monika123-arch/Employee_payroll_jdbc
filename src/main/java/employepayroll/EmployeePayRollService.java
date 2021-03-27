package employepayroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayRollService {
    public static Scanner sc = new Scanner(System.in);
    public enum IOService {
        CONSOLE_IO, FILE_IO, DB_IO, REST_IO;
    }
    private List<EmployeePayRollData> employeePayRollDataList;
    private EmployeePayRollDBService employeePayRollDBService;
    public List<EmployeePayRollData> employeeList;
    public EmployeePayRollService() {}

    public EmployeePayRollService(List<EmployeePayRollData> employeeList) {
        this.employeeList = employeeList;
    }


    public long readData(IOService ioService) {
        if (ioService.equals(IOService.CONSOLE_IO)) {
            System.out.println("Enter Employee ID: ");
            int id = sc.nextInt();
            System.out.println("Enter Employee Name: ");
            String name = sc.next();
            System.out.println("Enter Employee Salary: ");
            double salary = sc.nextDouble();
            employeeList.add(new EmployeePayRollData(id, name, salary));
            long result = employeeList.size();
            return result;
        }else if (ioService.equals(IOService.FILE_IO)){
            new EmployeePayRollFileIOService().readData();
            return employeeList.size();
        }else
            return 0;
    }


    public void writeData(IOService ioService) {
        if(ioService.equals(IOService.CONSOLE_IO))
            System.out.println("\nOUTPUT:\n" + employeeList);
        else if(ioService.equals(IOService.FILE_IO))
            new EmployeePayRollFileIOService().writeData(employeeList);
    }


    public void printData(IOService ioService){
        if(ioService.equals(IOService.FILE_IO))
            new EmployeePayRollFileIOService().printData();
    }

    public long countEntries(IOService ioService){
        if(ioService.equals(IOService.FILE_IO))
            new EmployeePayRollFileIOService().countEntries();
        return employeeList.size();
    }


    public List<EmployeePayRollData> readEmployeeData(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            this.employeeList = new EmployeePayRollDBService().readData();
        return this.employeeList;
    }

    public void updateEmployeeSalary(String empName, double empSalary) {
        int result = employeePayRollDBService.updateEmployeeData(empName,empSalary);
        if (result == 0) return;
        EmployeePayRollData employeePayRollData = this.getEmployeePayRollData(empName);
        if (employeePayRollData != null) employeePayRollData.salary= (int) empSalary;
    }

    private EmployeePayRollData getEmployeePayRollData(String name) {
        for (EmployeePayRollData data : employeeList) {
            if (data.name.equals(name))
                return data;
        }
        return null;
    }

    public boolean checkEmployeePayRollSyncWithDB(String name) {
        List<EmployeePayRollData>employeePayrollDataList= employeePayRollDBService.getEmployeePayRollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayRollData(name));
    }


    public List<EmployeePayRollData> readFilteredEmpPayRollData(IOService ioService,String date1,String date2) {
        if (ioService.equals(IOService.DB_IO))
            this.employeeList = employeePayRollDBService.readFilteredData(date1,date2);
        return this.employeeList;
    }

    public static void main(String[] args) {
        ArrayList<EmployeePayRollData> employeeList = new ArrayList<>();
        EmployeePayRollService empService = new EmployeePayRollService(employeeList);
        Scanner sc = new Scanner(System.in);
        empService.writeData(IOService.FILE_IO);
        empService.readData(IOService.FILE_IO);
    }
}
