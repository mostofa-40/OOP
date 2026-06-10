// Problem 9 : School Student Grade Management System

// --- Core Implementation ---

interface Evaluable {
    String computeGrade();
}

abstract class Person {
    private String personID;
    private String name;
    private String email;
    public static int totalStudents = 0;

    public Person(String personID, String name, String email) {
        this.personID = personID;
        this.name = name;
        this.email = email;
        totalStudents++;
    }

    public String getPersonID() {
        return personID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void displayInfo() {
        System.out.println("ID: " + personID + " | Name: " + name + " | Email: " + email);
    }
}

abstract class Student extends Person implements Evaluable {
    private String studentID;
    private String program;
    private double[] marks;

    public Student(String personID, String name, String email, String studentID, String program, double[] marks) {
        super(personID, name, email);
        this.studentID = studentID;
        this.program = program;
        this.marks = marks;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getProgram() {
        return program;
    }

    public double getAverage() {
        if (marks == null || marks.length == 0) return 0;
        double sum = 0;
        for (double mark : marks) {
            sum += mark;
        }
        return sum / marks.length;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.print("Roll: " + studentID + " | Program: " + program);
    }
}

class UndergraduateStudent extends Student {
    private int yearOfStudy;

    public UndergraduateStudent(String personID, String name, String email, String studentID, String program, double[] marks, int yearOfStudy) {
        super(personID, name, email, studentID, program, marks);
        this.yearOfStudy = yearOfStudy;
    }

    @Override
    public String computeGrade() {
        UGGradePolicy policy = new UGGradePolicy();
        return policy.assign(getAverage());
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Undergraduate Student ===");
        super.displayInfo();
        System.out.println(" | Year: " + yearOfStudy);
        double roundedAvg = Math.round(getAverage() * 100.0) / 100.0;
        UGGradePolicy policy = new UGGradePolicy();
        System.out.println("Average: " + roundedAvg + " | Grade (UGGradePolicy): " + policy.assign(getAverage()));
    }

    public static class UGGradePolicy {
        public UGGradePolicy() {}

        public String assign(double average) {
            if (average >= 85) return "A+";
            if (average >= 80) return "A";
            if (average >= 70) return "B";
            if (average >= 60) return "C";
            return "F";
        }
    }
}

class GraduateStudent extends Student {
    private String thesisTitle;
    private String supervisor;

    public GraduateStudent(String personID, String name, String email, String studentID, String program, double[] marks, String thesisTitle, String supervisor) {
        super(personID, name, email, studentID, program, marks);
        this.thesisTitle = thesisTitle;
        this.supervisor = supervisor;
    }

    @Override
    public String computeGrade() {
        GradGradePolicy policy = new GradGradePolicy();
        return policy.assign(getAverage());
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Graduate Student ===");
        super.displayInfo();
        System.out.println("\nThesis: " + thesisTitle + " | Supervisor: " + supervisor);
        double roundedAvg = Math.round(getAverage() * 100.0) / 100.0;
        GradGradePolicy policy = new GradGradePolicy();
        System.out.println("Average: " + roundedAvg + " | Grade (GradGradePolicy): " + policy.assign(getAverage()));
    }

    public static class GradGradePolicy {
        public GradGradePolicy() {}

        public String assign(double average) {
            if (average >= 90) return "A+";
            if (average >= 80) return "A";
            if (average >= 70) return "B";
            if (average >= 60) return "C";
            return "F";
        }
    }
}

class GradeCalculator {
    public GradeCalculator() {}

    public String evaluate(double average) {
        UndergraduateStudent.UGGradePolicy policy = new UndergraduateStudent.UGGradePolicy();
        return policy.assign(average);
    }

    public String evaluate(double average, double bonus) {
        return evaluate(average + bonus);
    }
}

// --- Driver Class ---

public class SchoolSystem {
    public static void main(String[] args) {

        double[] m1 = {75, 80, 90};
        double[] m2 = {85, 88, 92};

        UndergraduateStudent ug = new UndergraduateStudent(
            "UG01", "Karim", "karim@uni.edu",
            "S001", "CSE", m1, 3
        );

        GraduateStudent grad = new GraduateStudent(
            "GR01", "Nila", "nila@uni.edu",
            "S002", "MS-CSE", m2,
            "AI Ethics", "Dr. Smith"
        );

        ug.displayInfo();
        System.out.println("Grade: " + ug.computeGrade());

        grad.displayInfo();
        System.out.println("Grade: " + grad.computeGrade());

        GradeCalculator gc = new GradeCalculator();

        System.out.println("evaluate(81.67) : " + gc.evaluate(81.67));
        System.out.println("evaluate(81.67,5.0) : " + gc.evaluate(81.67, 5.0));

        System.out.println("Total Students: " + Person.totalStudents);
    }
}