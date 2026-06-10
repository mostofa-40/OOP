// Telecommunication Subscriber Billing System

// --- Core Implementation ---

interface Invoiceable {
    void generateInvoice();
}

abstract class Subscriber {
    private String subscriberId;
    private String subscriberName;
    private String email;

    public Subscriber(String subscriberId, String subscriberName, String email) {
        this.subscriberId = subscriberId;
        this.subscriberName = subscriberName;
        this.email = email;
    }

    public Subscriber(String subscriberId, String subscriberName) {
        this(subscriberId, subscriberName, "Unknown");
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public String getEmail() {
        return email;
    }

    public abstract void displayProfile();
}

abstract class MobileSubscriber extends Subscriber {
    private String phoneNumber;
    private String networkType;
    public static int totalSubscribers = 0;

    public MobileSubscriber(String subscriberId, String subscriberName, String email, String phoneNumber, String networkType) {
        super(subscriberId, subscriberName, email);
        this.phoneNumber = phoneNumber;
        this.networkType = networkType;
        totalSubscribers++;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNetworkType() {
        return networkType;
    }

    public abstract void displayProfile();
}

class PostpaidSubscriber extends MobileSubscriber implements Invoiceable {
    private double monthlyLimit;
    private double dataUsedGB;
    private double ratePerExtraGB;

    public PostpaidSubscriber(String subscriberId, String subscriberName, String email, String phoneNumber, String networkType, double monthlyLimit, double ratePerExtraGB) {
        super(subscriberId, subscriberName, email, phoneNumber, networkType);
        this.monthlyLimit = monthlyLimit;
        this.ratePerExtraGB = ratePerExtraGB;
        this.dataUsedGB = 0.0;
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public double getRatePerExtraGB() {
        return ratePerExtraGB;
    }

    public double getDataUsedGB() {
        return dataUsedGB;
    }

    public double calculateBill() {
        return monthlyLimit;
    }

    public double calculateBill(double extraGB) {
        this.dataUsedGB = extraGB;
        return monthlyLimit + (extraGB * ratePerExtraGB);
    }

    @Override
    public void generateInvoice() {
        System.out.println("--- POSTPAID INVOICE ---");
        double extraCharge = dataUsedGB * ratePerExtraGB;
        double total = monthlyLimit + extraCharge;
        System.out.println("Subscriber: " + getSubscriberName() + " | Phone: " + getPhoneNumber());
        System.out.println("Monthly Limit: " + monthlyLimit + " | Extra Charge: " + extraCharge + " | Total: " + total);
    }

    @Override
    public void displayProfile() {
        System.out.println("=== Postpaid Subscriber ===");
        System.out.println("ID : " + getSubscriberId() + " | Name: " + getSubscriberName() + " | Email: " + getEmail());
        System.out.println("Phone : " + getPhoneNumber() + " | Network: " + getNetworkType());
        System.out.println("Limit : " + monthlyLimit + " | Rate/ExtraGB: " + ratePerExtraGB);
    }
}

class PremiumPostpaid extends PostpaidSubscriber {
    private String managerName;
    private double rolloverDataGB;

    public PremiumPostpaid(String subscriberId, String subscriberName, String email, String phoneNumber, String networkType, double monthlyLimit, double ratePerExtraGB, String managerName, double rolloverDataGB) {
        super(subscriberId, subscriberName, email, phoneNumber, networkType, monthlyLimit, ratePerExtraGB);
        this.managerName = managerName;
        this.rolloverDataGB = rolloverDataGB;
    }

    @Override
    public double calculateBill() {
        return super.calculateBill();
    }

    @Override
    public double calculateBill(double extraGB) {
        double netExtraGB = extraGB - rolloverDataGB;
        if (netExtraGB < 0) {
            netExtraGB = 0;
        }
        return getMonthlyLimit() + (netExtraGB * getRatePerExtraGB());
    }

    @Override
    public void generateInvoice() {
        System.out.println("--- PREMIUM INVOICE ---");
        System.out.println("Subscriber: " + getSubscriberName() + " | Manager: " + managerName);
        System.out.println("Base: " + getMonthlyLimit() + " | Rollover Applied: " + rolloverDataGB + " GB | Total: " + calculateBill());
    }

    @Override
    public void displayProfile() {
        System.out.println("=== Premium Postpaid ===");
        System.out.println("ID : " + getSubscriberId() + " | Name: " + getSubscriberName() + " | Network: " + getNetworkType());
        System.out.println("Limit : " + getMonthlyLimit() + " | Rollover: " + rolloverDataGB + " GB");
        System.out.println("Manager : " + managerName);
    }
}

class SimCard {
    private String simNumber;
    private String operator;
    private String activationDate;

    public SimCard(String simNumber, String operator, String activationDate) {
        this.simNumber = simNumber;
        this.operator = operator;
        this.activationDate = activationDate;
    }

    public String getSimNumber() {
        return simNumber;
    }

    public void displaySimInfo() {
        System.out.println("=== SIM Card Info ===");
        System.out.println("SIM : " + simNumber + " | Operator: " + operator + " | Activated: " + activationDate);
    }
}

// --- Driver Class ---

public class TelecomSystem {
    public static void main(String[] args) {

        SimCard sim = new SimCard(
            "8801911XXXXXX", "ConnectBD", "2023-06-01"
        );

        PostpaidSubscriber ps = new PostpaidSubscriber(
            "S001", "Ruhul", "ruhul@mail.com",
            "01911000001", "4G", 500.0, 30.0
        );

        PremiumPostpaid pp = new PremiumPostpaid(
            "S002", "Farida", "farida@mail.com",
            "01922000002", "5G", 800.0, 25.0,
            "Mr. Alam", 3.0
        );

        sim.displaySimInfo();

        ps.displayProfile();
        System.out.println("Bill (8GB used) : " + ps.calculateBill(8));
        ps.generateInvoice();

        pp.displayProfile();
        System.out.println("Premium Bill : " + pp.calculateBill());
        pp.generateInvoice();

        System.out.println("Total Subscribers: " + MobileSubscriber.totalSubscribers);
    }
}
