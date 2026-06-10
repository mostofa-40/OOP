// Problem 15 : University Staff Payroll & Publication System

// --- Core Implementation ---

interface Publishable {
    void publishResearch();
}

abstract class Person {
    private String personId;
    private String fullName;
    private String dateOfBirth;

    public Person(String personId, String fullName, String dateOfBirth) {
        this.personId = personId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(String personId, String fullName) {
        this(personId, fullName, "Unknown");
    }

    public String getPersonId() {
        return personId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public abstract void displayInfo();
}

abstract class Staff extends Person {
    private String designation;
    private double basicSalary;
    private int joiningYear;
    public static int totalStaff = 0;

    public Staff(String personId, String fullName, String dateOfBirth, String designation, double basicSalary, int joiningYear) {
        super(personId, fullName, dateOfBirth);
        this.designation = designation;
        this.basicSalary = basicSalary;
        this.joiningYear = joiningYear;
        totalStaff++;
    }

    public String getDesignation() {
        return designation;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public int getJoiningYear() {
        return joiningYear;
    }

    public abstract double calculateCompensation();
    public abstract double calculateCompensation(int months);
    public abstract void displayInfo();
}

class AcademicStaff extends Staff implements Publishable {
    private int publicationsCount;
    private double researchGrantAmount;
    private String specialization;

    public AcademicStaff(String personId, String fullName, String dateOfBirth, String designation, double basicSalary, int joiningYear, int publicationsCount, double researchGrantAmount, String specialization) {
        super(personId, fullName, dateOfBirth, designation, basicSalary, joiningYear);
        this.publicationsCount = publicationsCount;
        this.researchGrantAmount = researchGrantAmount;
        this.specialization = specialization;
    }

    public int getPublicationsCount() {
        return publicationsCount;
    }

    public double getResearchGrantAmount() {
        return researchGrantAmount;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public double calculateCompensation() {
        return getBasicSalary() + researchGrantAmount;
    }

    @Override
    public double calculateCompensation(int months) {
        return calculateCompensation() * months;
    }

    @Override
    public void publishResearch() {
        System.out.println("Published " + publicationsCount + " research papers in " + specialization + ".");
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Academic Staff ===");
        System.out.println("ID: " + getPersonId() + " | Name: " + getFullName() + " | DOB: " + getDateOfBirth());
        System.out.println("Designation: " + getDesignation() + " | Salary: " + getBasicSalary());
        System.out.println("Joined: " + getJoiningYear() + " | Specialization: " + specialization + " | Publications: " + publicationsCount);
        System.out.println("Research Grant: " + researchGrantAmount);
    }
}

class SeniorProfessor extends AcademicStaff {
    private int phdStudentsSupervised;
    private double conferenceBonus;

    public SeniorProfessor(String personId, String fullName, String dateOfBirth, String designation, double basicSalary, int joiningYear, int publicationsCount, double researchGrantAmount, String specialization, int phdStudentsSupervised, double conferenceBonus) {
        super(personId, fullName, dateOfBirth, designation, basicSalary, joiningYear, publicationsCount, researchGrantAmount, specialization);
        this.phdStudentsSupervised = phdStudentsSupervised;
        this.conferenceBonus = conferenceBonus;
    }

    @Override
    public double calculateCompensation() {
        return super.calculateCompensation() + conferenceBonus;
    }

    @Override
    public double calculateCompensation(int months) {
        return calculateCompensation() * months;
    }

    @Override
    public void publishResearch() {
        System.out.println("Published " + getPublicationsCount() + " papers in " + getSpecialization() + ". Supervising " + phdStudentsSupervised + " PhD students.");
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Senior Professor ===");
        System.out.println("ID: " + getPersonId() + " | Name: " + getFullName() + " | Designation: " + getDesignation());
        System.out.println("Salary: " + getBasicSalary() + " | Specialization: " + getSpecialization() + " | Publications: " + getPublicationsCount());
        System.out.println("PhD Students Supervised: " + phdStudentsSupervised + " | Conference Bonus: " + conferenceBonus);
    }
}

class Department {
    private String deptCode;
    private String deptName;
    private String hod;

    public Department(String deptCode, String deptName, String hod) {
        this.deptCode = deptCode;
        this.deptName = deptName;
        this.hod = hod;
    }

    public String getDeptName() {
        return deptName;
    }

    public void displayDeptInfo() {
        System.out.println("=== Department Info ===");
        System.out.println("Code: " + deptCode + " | Name: " + deptName + " | HOD: " + hod);
    }
}

// --- Driver Class ---

public class StaffPayrollSystem {
    public static void main(String[] args) {

        Department dept = new Department("CSE", "Computer Science", "Prof. Karim");

        AcademicStaff as = new AcademicStaff(
            "ST01", "Dr. Nusrat", "1985-04-12",
            "Associate Prof", 75000.0, 2015,
            18, 15000.0, "ML"
        );

        SeniorProfessor sp = new SeniorProfessor(
            "ST02", "Prof. Jamal", "1970-09-01",
            "Professor", 120000.0, 2005,
            45, 30000.0, "AI", 8, 20000.0
        );

        dept.displayDeptInfo();

        as.displayInfo();
        System.out.println("Monthly Comp : " + as.calculateCompensation());
        System.out.println("6-Month Comp : " + as.calculateCompensation(6));
        as.publishResearch();

        sp.displayInfo();
        System.out.println("Senior Monthly: " + sp.calculateCompensation());
        sp.publishResearch();

        System.out.println("Total Staff: " + Staff.totalStaff);
    }
}