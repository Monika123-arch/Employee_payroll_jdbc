package employepayroll;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayRollData {
    public int id;
    public String name,gender,address,phone_no,department;
    public double salary,deduction,taxable_pay,income_tax,net_pay,start;
    public LocalDate join_date;

    public EmployeePayRollData(int id, String name, String gender, Double salary, String address, String phone_no, LocalDate date_join) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.address = address;
        this.phone_no = phone_no;
        this.join_date = date_join;

    }

    public EmployeePayRollData(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public EmployeePayRollData(int id, String name, String phone_number, String address, String department, String gender, double basic_pay, double deduction, double taxable_pay, double income_tax, double net_pay, LocalDate date) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.department = department;
        this.join_date = date;
        this.deduction = deduction;
        this.taxable_pay = taxable_pay;
        this.income_tax = income_tax;
        this.net_pay = net_pay;


    }

    public void EmployePayrolldata(int id, String name,String gender, double salary, String address, String phone_no, LocalDate join_date){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.address = address;
        this.phone_no = phone_no;
        this.join_date = join_date;
    }

    @Override
    public String toString(){
        return "EmployeePayRolldata [id:" +id+" name:" +name+ " salary:" + salary+ "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmployeePayRollData that = (EmployeePayRollData) obj;
        return id == that.id && salary == that.salary && Objects.equals(name, that.name) && Objects.equals(gender, that.gender) && Objects.equals(join_date, that.join_date);
    }
}
