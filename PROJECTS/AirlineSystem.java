// Airline Reservation System

// --- Core Implementation ---

interface Boardable {
    void generateBoardingPass();
}

abstract class Person {
    private String personId;
    private String fullName;
    private String nationality;

    public Person(String personId, String fullName, String nationality) {
        this.personId = personId;
        this.fullName = fullName;
        this.nationality = nationality;
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

    public String getNationality() {
        return nationality;
    }

    public abstract void displayInfo();
}

abstract class Passenger extends Person implements Boardable {
    private String passportNumber;
    private int seatsBooked;
    public static int totalBookings = 0;

    public Passenger(String personId, String fullName, String nationality, String passportNumber) {
        super(personId, fullName, nationality);
        this.passportNumber = passportNumber;
        this.seatsBooked = 0;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void bookSeats(int n) {
        this.seatsBooked += n;
        totalBookings++;
    }

    public abstract double calculateFare(Flight f);
    public abstract double calculateFare(Flight f, int seats);
}

class PremiumPassenger extends Passenger {
    private String membershipTier;
    private double loyaltyMiles;

    public PremiumPassenger(String personId, String fullName, String nationality, String passportNumber, String membershipTier) {
        super(personId, fullName, nationality, passportNumber);
        this.membershipTier = membershipTier;
        this.loyaltyMiles = 0.0;
    }

    public String getMembershipTier() {
        return membershipTier;
    }

    public double getLoyaltyMiles() {
        return loyaltyMiles;
    }

    @Override
    public double calculateFare(Flight f) {
        // Standard 10% premium discount applied to the base fare
        return f.getBaseFarePerSeat() * 0.9;
    }

    @Override
    public double calculateFare(Flight f, int seats) {
        return calculateFare(f) * seats;
    }

    public void addMiles(double miles) {
        this.loyaltyMiles += miles;
        System.out.println("Loyalty Miles after add: " + this.loyaltyMiles);
    }

    @Override
    public void generateBoardingPass() {
        System.out.println("*** PREMIUM BOARDING PASS ***");
        System.out.println("Passenger: " + getFullName() + " | Flight: N/A | Miles: " + loyaltyMiles);
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Premium Passenger ===");
        System.out.println("ID: " + getPersonId() + " | Name: " + getFullName() + " | Tier: " + membershipTier);
        System.out.println("Loyalty Miles: " + loyaltyMiles);
    }
}

class CorporatePassenger extends PremiumPassenger {
    private String companyName;
    private double corporateDiscount;

    public CorporatePassenger(String personId, String fullName, String nationality, String passportNumber, String membershipTier, String companyName, double corporateDiscount) {
        super(personId, fullName, nationality, passportNumber, membershipTier);
        this.companyName = companyName;
        this.corporateDiscount = corporateDiscount;
    }

    @Override
    public double calculateFare(Flight f) {
        // Sequential/compounded calculation of premium and corporate discounts
        return super.calculateFare(f) * (1 - corporateDiscount / 100.0);
    }

    @Override
    public double calculateFare(Flight f, int seats) {
        return calculateFare(f) * seats;
    }

    @Override
    public void generateBoardingPass() {
        System.out.println("*** CORPORATE BOARDING PASS ***");
        // Handled statically here as the defined zero-argument interface signature omits flight data
        System.out.println("Passenger: " + getFullName() + " | Flight: SW201 | Dest: Dubai | Company: " + companyName);
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Corporate Passenger ===");
        System.out.println("ID: " + getPersonId() + " | Name: " + getFullName() + " | Nationality: " + getNationality());
        System.out.println("Passport: " + getPassportNumber() + " | Tier: " + getMembershipTier());
        System.out.println("Company: " + companyName + " | Corp Discount: " + corporateDiscount + "%");
    }
}

class Flight {
    private String flightCode;
    private String destination;
    private double baseFarePerSeat;
    private int totalSeats;

    public Flight(String flightCode, String destination, double baseFarePerSeat, int totalSeats) {
        this.flightCode = flightCode;
        this.destination = destination;
        this.baseFarePerSeat = baseFarePerSeat;
        this.totalSeats = totalSeats;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getDestination() {
        return destination;
    }

    public double getBaseFarePerSeat() {
        return baseFarePerSeat;
    }

    public void displayFlightInfo() {
        System.out.println("=== Flight Info ===");
        System.out.println("Flight: " + flightCode + " | Destination: " + destination);
        System.out.println("Fare/Seat: " + baseFarePerSeat + " | Seats: " + totalSeats);
    }
}

// --- Driver Class ---

public class AirlineSystem {
    public static void main(String[] args) {

        Flight f = new Flight("SW201", "Dubai", 15000.0, 180);

        CorporatePassenger cp = new CorporatePassenger(
            "P001", "Arif", "Bangladeshi",
            "BD123456", "Gold", "SUST Corp", 15.0
        );

        PremiumPassenger pp = new PremiumPassenger(
            "P002", "Sadia", "Bangladeshi",
            "BD654321", "Silver"
        );

        f.displayFlightInfo();

        cp.displayInfo();
        System.out.println("Corp Fare(1): " + cp.calculateFare(f));
        System.out.println("Corp Fare(3): " + cp.calculateFare(f, 3));
        cp.generateBoardingPass();

        pp.displayInfo();
        System.out.println("Prem Fare(2): " + pp.calculateFare(f, 2));
        pp.addMiles(500.0);
        pp.generateBoardingPass();

        System.out.println("Total Bookings: " + Passenger.totalBookings);
    }
}
