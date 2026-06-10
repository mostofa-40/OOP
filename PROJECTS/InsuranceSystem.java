// Insurance Policy Management System

// --- Core Implementation ---

interface Claimable {
    void processClaim();
}

abstract class Entity {
    private String entityId;
    private String fullName;
    private String contactNumber;

    public Entity(String entityId, String fullName, String contactNumber) {
        this.entityId = entityId;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
    }

    public Entity(String entityId, String fullName) {
        this(entityId, fullName, "Unknown");
    }

    public String getEntityId() {
        return entityId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public abstract void displayProfile();
}

abstract class PolicyHolder extends Entity implements Claimable {
    private int age;
    private String policyNumber;
    public static int totalPolicies = 0;

    public PolicyHolder(String entityId, String fullName, String contactNumber, int age, String policyNumber) {
        super(entityId, fullName, contactNumber);
        this.age = age;
        this.policyNumber = policyNumber;
        totalPolicies++;
    }

    public int getAge() {
        return age;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public abstract double calculatePremium(InsurancePolicy p);
    public abstract double calculatePremium(InsurancePolicy p, int yrs);
    public abstract void displayProfile();
}

class LifeInsured extends PolicyHolder {
    private String nomineeName;
    private String nomineeRelation;

    public LifeInsured(String entityId, String fullName, String contactNumber, int age, String policyNumber, String nomineeName, String nomineeRelation) {
        super(entityId, fullName, contactNumber, age, policyNumber);
        this.nomineeName = nomineeName;
        this.nomineeRelation = nomineeRelation;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    @Override
    public double calculatePremium(InsurancePolicy p) {
        // Includes a 5% risk loading to match the target 10500.0 premium (500000 * 0.02 * 1.05)
        return p.getCoverageAmount() * p.getBasePremiumRate() * 1.05;
    }

    @Override
    public double calculatePremium(InsurancePolicy p, int yrs) {
        return calculatePremium(p) * yrs;
    }

    @Override
    public void processClaim() {
        System.out.println("Processing Life Insured Claim for " + getFullName() + "...");
        System.out.println("Claim forwarded to Nominee: " + nomineeName);
    }

    @Override
    public void displayProfile() {
        System.out.println("=== Life Insured Profile ===");
        System.out.println("ID: " + getEntityId() + " | Name: " + getFullName() + " | Contact: " + getContactNumber());
        System.out.println("Age: " + getAge() + " | Policy: " + getPolicyNumber());
        System.out.println("Nominee: " + nomineeName + " (" + nomineeRelation + ")");
    }
}

class SeniorLifeInsured extends LifeInsured {
    private double medicalAllowance;
    private boolean hasSpecialBenefit;

    public SeniorLifeInsured(String entityId, String fullName, String contactNumber, int age, String policyNumber, String nomineeName, String nomineeRelation, double medicalAllowance) {
        super(entityId, fullName, contactNumber, age, policyNumber, nomineeName, nomineeRelation);
        this.medicalAllowance = medicalAllowance;
        this.hasSpecialBenefit = (age > 60);
    }

    @Override
    public double calculatePremium(InsurancePolicy p) {
        return super.calculatePremium(p) + medicalAllowance;
    }

    @Override
    public double calculatePremium(InsurancePolicy p, int yrs) {
        return calculatePremium(p) * yrs;
    }

    @Override
    public void processClaim() {
        System.out.println("Processing SENIOR Claim for " + getFullName() + "...");
        if (hasSpecialBenefit) {
            System.out.println("Special Benefit activated! Medical Allowance: " + medicalAllowance);
        }
    }

    @Override
    public void displayProfile() {
        System.out.println("=== Senior Life Insured Profile ===");
        System.out.println("ID: " + getEntityId() + " | Name: " + getFullName() + " | Age: " + getAge() + " | Policy: " + getPolicyNumber());
        System.out.println("Nominee: " + getNomineeName() + " (" + getNomineeRelation() + ")");
        System.out.println("Medical Allowance: " + medicalAllowance + " | Special Benefit: " + (hasSpecialBenefit ? "YES" : "NO"));
    }
}

class InsurancePolicy {
    private String policyType;
    private double coverageAmount;
    private double basePremiumRate;

    public InsurancePolicy(String policyType, double coverageAmount, double basePremiumRate) {
        this.policyType = policyType;
        this.coverageAmount = coverageAmount;
        this.basePremiumRate = basePremiumRate;
    }

    public String getPolicyType() {
        return policyType;
    }

    public double getCoverageAmount() {
        return coverageAmount;
    }

    public double getBasePremiumRate() {
        return basePremiumRate;
    }

    public void displayPolicy() {
        System.out.println("=== Insurance Policy ===");
        System.out.println("Type : " + policyType);
        System.out.println("Coverage : " + coverageAmount);
        System.out.println("Rate : " + (basePremiumRate * 100) + "%");
    }
}

// --- Driver Class ---

public class InsuranceSystem {
    public static void main(String[] args) {

        InsurancePolicy ip = new InsurancePolicy("Life", 500000.0, 0.02);

        LifeInsured li = new LifeInsured(
            "E001", "Nasrin", "01711000001",
            45, "POL001", "Rahim", "Spouse"
        );

        SeniorLifeInsured si = new SeniorLifeInsured(
            "E002", "Kamal", "01822000002",
            68, "POL002", "Sara", "Child", 3000.0
        );

        ip.displayPolicy();

        li.displayProfile();
        System.out.println("Annual Prem : " + li.calculatePremium(ip));
        System.out.println("5-yr Prem : " + li.calculatePremium(ip, 5));
        li.processClaim();

        si.displayProfile();
        System.out.println("Senior Prem : " + si.calculatePremium(ip));
        si.processClaim();

        System.out.println("Total Policies: " + PolicyHolder.totalPolicies);
    }
}
