// Employee Payroll Processing System

// --- Core Implementation ---

interface Taxable {
    double calculateTax();
}

abstract class Employee {
    private String employeeId;
    private String name;
    private String department;
    private int joiningYear;
    public static int totalEmployees = 0;

    public Employee(String employeeId, String name, String department, int joiningYear) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.joiningYear = joiningYear;
        totalEmployees++;
    }

    public Employee(String employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
        totalEmployees++;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getJoiningYear() {
        return joiningYear;
    }

    public abstract double calculateSalary();
    public abstract void displayDetails();

    public static class TaxBracket {
        public String bracketName;
        public double minSalary;
        public double taxPercent;

        public TaxBracket(String bracketName, double minSalary, double taxPercent) {
            this.bracketName = bracketName;
            this.minSalary = minSalary;
            this.taxPercent = taxPercent;
        }

        public void describe() {
            System.out.println("Tax Bracket: " + bracketName + " | Min Salary: " + minSalary + " | Tax: " + taxPercent + "%");
        }
    }
}

class FullTimeEmployee extends Employee implements Taxable {
    private double monthlySalary;
    private double bonusRate;

    public FullTimeEmployee(String employeeId, String name, String department, int joiningYear, double monthlySalary, double bonusRate) {
        super(employeeId, name, department, joiningYear);
        this.monthlySalary = monthlySalary;
        this.bonusRate = bonusRate;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public double getBonusRate() {
        return bonusRate;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary + (monthlySalary * bonusRate / 100.0);
    }

    @Override
    public double calculateTax() {
        return calculateSalary() * 0.10;
    }

    @Override
    public void displayDetails() {
        System.out.println("=== Full-Time Employee Details ===");
        System.out.println("ID : " + getEmployeeId());
        System.out.println("Name : " + getName());
        System.out.println("Department : " + getDepartment());
        System.out.println("Joined : " + getJoiningYear());
        System.out.println("Monthly Salary : " + monthlySalary);
        System.out.println("Bonus Rate : " + bonusRate + "%");
    }

    public class PaySlip {
        public String month;
        public String remarks;

        public PaySlip(String month, String remarks) {
            this.month = month;
            this.remarks = remarks;
        }

        public void printPaySlip() {
            System.out.println("--- Pay Slip ---");
            System.out.println("Employee : " + getName());
            System.out.println("Month : " + month);
            
            double bonusAmt = monthlySalary * (bonusRate / 100.0);
            if (FullTimeEmployee.this instanceof SeniorEmployee) {
                SeniorEmployee se = (SeniorEmployee) FullTimeEmployee.this;
                System.out.println("Salary : " + monthlySalary + " + Bonus: " + bonusAmt + " + Seniority: " + se.getSeniorityAllowance());
            } else {
                System.out.println("Salary : " + monthlySalary + " + Bonus: " + bonusAmt);
            }
            
            System.out.println("Net Pay : " + calculateSalary());
            System.out.println("Remarks : " + remarks);
        }
    }
}

class SeniorEmployee extends FullTimeEmployee {
    private double seniorityAllowance;
    private int yearsOfExperience;

    public SeniorEmployee(String employeeId, String name, String department, int joiningYear, double monthlySalary, double bonusRate, double seniorityAllowance, int yearsOfExperience) {
        super(employeeId, name, department, joiningYear, monthlySalary, bonusRate);
        this.seniorityAllowance = seniorityAllowance;
        this.yearsOfExperience = yearsOfExperience;
    }

    public double getSeniorityAllowance() {
        return seniorityAllowance;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    @Override
    public double calculateSalary() {
        return super.calculateSalary() + seniorityAllowance;
    }

    @Override
    public double calculateTax() {
        return calculateSalary() * 0.12;
    }

    @Override
    public void displayDetails() {
        System.out.println("=== Senior Employee Details ===");
        System.out.println("ID : " + getEmployeeId());
        System.out.println("Name : " + getName());
        System.out.println("Department : " + getDepartment());
        System.out.println("Joined : " + getJoiningYear());
        System.out.println("Monthly Salary : " + getMonthlySalary());
        System.out.println("Bonus Rate : " + getBonusRate() + "%");
        System.out.println("Seniority Allowance : " + seniorityAllowance);
        System.out.println("Years of Experience : " + yearsOfExperience);
    }
}

class PartTimeEmployee extends Employee implements Taxable {
    private double hourlyRate;
    private double hoursWorked;

    public PartTimeEmployee(String employeeId, String name, String department, int joiningYear, double hourlyRate, double hoursWorked) {
        super(employeeId, name, department, joiningYear);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public double calculateTax() {
        return calculateSalary() * 0.05;
    }

    @Override
    public void displayDetails() {
        System.out.println("=== Part-Time Employee Details ===");
        System.out.println("ID : " + getEmployeeId());
        System.out.println("Name : " + getName());
        System.out.println("Department : " + getDepartment());
        System.out.println("Hourly Rate : " + hourlyRate);
        System.out.println("Hours Worked: " + hoursWorked);
    }
}

// --- Driver Class ---

public class PayrollSystem {
    public static void main(String[] args) {

        Employee.TaxBracket tb = new Employee.TaxBracket("High Income", 30000, 10.0);
        tb.describe();

        SeniorEmployee se = new SeniorEmployee(
            "E001", "Shirin", "Engineering", 2018,
            40000.0, 10.0, 8000.0, 7
        );

        PartTimeEmployee pte = new PartTimeEmployee(
            "E002", "Bakar", "Sales", 2023,
            200.0, 60.0
        );

        se.displayDetails();
        System.out.println("Net Salary : " + se.calculateSalary());
        System.out.println("Tax Amount : " + se.calculateTax());

        FullTimeEmployee.PaySlip ps = se.new PaySlip("June 2025", "Excellent Performance");
        ps.printPaySlip();

        pte.displayDetails();
        System.out.println("Net Salary : " + pte.calculateSalary());
        System.out.println("Tax Amount : " + pte.calculateTax());

        System.out.println("Total Employees: " + Employee.totalEmployees);
    }
}
