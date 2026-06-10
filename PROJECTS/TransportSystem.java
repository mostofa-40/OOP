// Transport Vehicle Fare Calculation System

// --- Core Implementation ---

abstract class Vehicle {
    private String vehicleId;
    private String routeName;
    private double baseFare;
    public static int totalVehicles = 0;

    public Vehicle(String vehicleId, String routeName, double baseFare) {
        this.vehicleId = vehicleId;
        this.routeName = routeName;
        this.baseFare = baseFare;
        totalVehicles++;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getRouteName() {
        return routeName;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public abstract double calculateFare(double km);
    public abstract void displayInfo();

    public static class Route {
        public String origin;
        public String destination;

        public Route(String origin, String destination) {
            this.origin = origin;
            this.destination = destination;
        }

        public void display() {
            System.out.println("Route: " + origin + " -> " + destination);
        }
    }
}

class Bus extends Vehicle {
    private int seatCapacity;
    private double chargePerKm;

    public Bus(String vehicleId, String routeName, double baseFare, int seatCapacity, double chargePerKm) {
        super(vehicleId, routeName, baseFare);
        this.seatCapacity = seatCapacity;
        this.chargePerKm = chargePerKm;
    }

    @Override
    public double calculateFare(double km) {
        return getBaseFare() + (km * chargePerKm);
    }

    public double calculateFare(double km, int pax) {
        // Applying a 5% group discount
        return calculateFare(km) * 0.95;
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Bus Info ===");
        System.out.println("Vehicle ID : " + getVehicleId());
        System.out.println("Route : " + getRouteName());
        System.out.println("Base Fare : " + getBaseFare());
        System.out.println("Seat Capacity : " + seatCapacity);
        System.out.println("Charge/km : " + chargePerKm);
    }

    public class FareSummary {
        public double totalFare;
        public int passengerCount;

        public FareSummary(double totalFare, int passengerCount) {
            this.totalFare = totalFare;
            this.passengerCount = passengerCount;
        }

        public void printSummary() {
            System.out.println("--- Fare Summary ---");
            System.out.println("Total Fare : " + totalFare);
            System.out.println("Passenger Count: " + passengerCount);
        }
    }
}

class LuxuryBus extends Bus {
    private double comfortSurcharge;
    private boolean hasLounge;

    public LuxuryBus(String vehicleId, String routeName, double baseFare, int seatCapacity, double chargePerKm, double comfortSurcharge, boolean hasLounge) {
        super(vehicleId, routeName, baseFare, seatCapacity, chargePerKm);
        this.comfortSurcharge = comfortSurcharge;
        this.hasLounge = hasLounge;
    }

    @Override
    public double calculateFare(double km) {
        return super.calculateFare(km) + comfortSurcharge;
    }

    @Override
    public double calculateFare(double km, int pax) {
        // Applying a 5% group discount to the base calculated luxury fare
        return calculateFare(km) * 0.95; 
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Luxury Bus Info ===");
        System.out.println("Vehicle ID : " + getVehicleId());
        System.out.println("Comfort Surcharge: " + comfortSurcharge);
        System.out.println("Lounge Access : " + (hasLounge ? "Yes" : "No"));
    }
}

class Taxi extends Vehicle {
    private double ratePerKm;
    private double waitingChargePerMin;

    public Taxi(String vehicleId, String routeName, double baseFare, double ratePerKm, double waitingChargePerMin) {
        super(vehicleId, routeName, baseFare);
        this.ratePerKm = ratePerKm;
        this.waitingChargePerMin = waitingChargePerMin;
    }

    @Override
    public double calculateFare(double km) {
        return getBaseFare() + (km * ratePerKm);
    }

    public double calculateFare(double km, int waitMin) {
        return calculateFare(km) + (waitMin * waitingChargePerMin);
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Taxi Info ===");
        System.out.println("Vehicle ID : " + getVehicleId());
        System.out.println("Rate/km : " + ratePerKm);
        System.out.println("Waiting/min : " + waitingChargePerMin);
    }
}

// --- Driver Class ---

public class TransportSystem {
    public static void main(String[] args) {

        Vehicle.Route r1 = new Vehicle.Route("Sylhet", "Dhaka");

        Bus b = new Bus("BUS01", "Sylhet-Dhaka", 50.0, 45, 5.0);

        LuxuryBus lb = new LuxuryBus(
            "LB01", "Sylhet-Dhaka", 80.0,
            30, 6.0, 200.0, true
        );

        Taxi t = new Taxi("TAX01", "City Route", 30.0, 10.0, 2.0);

        r1.display();

        b.displayInfo();
        System.out.println("Bus fare (20km) : " + b.calculateFare(20));
        System.out.println("Bus fare (20km,35 pax): " + b.calculateFare(20, 35));

        Bus.FareSummary fs = b.new FareSummary(b.calculateFare(20, 35), 35);
        fs.printSummary();

        lb.displayInfo();
        System.out.println("Luxury fare (20km) : " + lb.calculateFare(20));

        t.displayInfo();
        System.out.println("Taxi fare (15km) : " + t.calculateFare(15));
        System.out.println("Taxi fare (15km,10min wait): " + t.calculateFare(15, 10));

        System.out.println("Total Vehicles: " + Vehicle.totalVehicles);
    }
}
