package employepayroll;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayRollDBService {
    public EmployeePayRollDBService(){}
    private PreparedStatement employeePayRollDataStatement;
    private static EmployeePayRollDBService employeePayRollDBService;


    public static EmployeePayRollDBService getInstance(){
        if (employeePayRollDBService==null)
            employeePayRollDBService=new EmployeePayRollDBService();
        return employeePayRollDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/employee_payroll?useSSl=false";
        String userName = "root";
        String password = "mamu";
        Connection connection;
        System.out.println("Connecting To DB: " + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL,userName,password);
        System.out.println("Connection is successful..! " + connection);
        return connection;
    }

    public List<EmployeePayRollData> readData() {
        String sql = "select * from employeepayroll;";
        List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayRollDataList = this.getEmployeePayRollData(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayRollDataList;
    }

    private List<EmployeePayRollData> getEmployeePayRollData(ResultSet resultSet) {
        List<EmployeePayRollData>employeePayRollDataList = new ArrayList<>();
        try {
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone_number = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                String department = resultSet.getString("department");
                String gender = resultSet.getString("gender");
                double basic_pay = resultSet.getDouble("basic_pay");
                double deduction = resultSet.getDouble("deduction");
                double taxable_pay = resultSet.getDouble("taxable_pay");
                double income_tax= resultSet.getDouble("income_tax");
                double net_pay = resultSet.getDouble("net_pay");
                LocalDate date = resultSet.getDate("start").toLocalDate();
                employeePayRollDataList.add(new EmployeePayRollData(id,name,phone_number,address,department,gender,basic_pay,deduction,taxable_pay,income_tax,net_pay,date));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employeePayRollDataList;
    }

    public List<EmployeePayRollData> getEmployeePayRollData(String name) {
        List<EmployeePayRollData>employeePayrollDataList = null;
        if (this.employeePayRollDataStatement==null)
            this.preparedStatementForEmployeeData();
        try {
            employeePayRollDataStatement.setString(1,name);
            ResultSet resultSet = employeePayRollDataStatement.executeQuery();
            employeePayrollDataList = this.getEmployeePayRollData(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    private void preparedStatementForEmployeeData() {
        try {
            Connection connection = this.getConnection();
            String sql = "select * from employeepayroll where name=?;";
            employeePayRollDataStatement = connection.prepareStatement(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int updateEmployeeData(String name, double salary) {
        return this.updateEmployeeDataUsingStatement(name,salary);
    }

    private int updateEmployeeDataUsingStatement(String name, double basic_pay){
        String sql = String.format("update employeepayroll set salary = %.2f where name = '%s';",basic_pay,name);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public List<EmployeePayRollData> readFilteredData(String date,String endDate) {
        String sql = String.format("select * from employeepayroll where start between '%s' and '%s';",date,endDate);
        List<EmployeePayRollData> employeePayRollDataList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayRollDataList = this.getEmployeePayRollData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayRollDataList;
    }

}