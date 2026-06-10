// Problem 20 : Power Utility Energy Billing System

// --- Core Implementation ---

interface Billable {
    void generateEnergyBill();
}

abstract class Consumer {
    private String consumerId;
    private String consumerName;
    private String address;

    public Consumer(String consumerId, String consumerName, String address) {
        this.consumerId = consumerId;
        this.consumerName = consumerName;
        this.address = address;
    }

    public Consumer(String consumerId, String consumerName) {
        this(consumerId, consumerName, "Unknown");
    }

    public String getConsumerId() {
        return consumerId;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public String getAddress() {
        return address;
    }

    public abstract void displayConsumerInfo();
}

abstract class EnergyConsumer extends Consumer {
    private String meterId;
    private double unitsConsumedKWh;
    private double tariffRatePerUnit;
    public static int totalConsumers = 0;

    public EnergyConsumer(String consumerId, String consumerName, String address, String meterId, double unitsConsumedKWh, double tariffRatePerUnit) {
        super(consumerId, consumerName, address);
        this.meterId = meterId;
        this.unitsConsumedKWh = unitsConsumedKWh;
        this.tariffRatePerUnit = tariffRatePerUnit;
        totalConsumers++;
    }

    public String getMeterId() {
        return meterId;
    }

    public double getUnitsConsumed() {
        return unitsConsumedKWh;
    }

    public double getTariffRate() {
        return tariffRatePerUnit;
    }

    public abstract double calculateBill();
    public abstract double calculateBill(double extraUnits);
    public abstract void displayConsumerInfo();
}

class IndustrialConsumer extends EnergyConsumer implements Billable {
    private double loadFactorPercent;
    private double peakDemandKW;
    private double demandChargePerKW;

    public IndustrialConsumer(String consumerId, String consumerName, String address, String meterId, double unitsConsumedKWh, double tariffRatePerUnit, double loadFactorPercent, double peakDemandKW, double demandChargePerKW) {
        super(consumerId, consumerName, address, meterId, unitsConsumedKWh, tariffRatePerUnit);
        this.loadFactorPercent = loadFactorPercent;
        this.peakDemandKW = peakDemandKW;
        this.demandChargePerKW = demandChargePerKW;
    }

    public double getPeakDemandKW() {
        return peakDemandKW;
    }

    public double getDemandChargePerKW() {
        return demandChargePerKW;
    }

    @Override
    public double calculateBill() {
        double energyCharge = getUnitsConsumed() * getTariffRate();
        double demandCharge = peakDemandKW * demandChargePerKW;
        return energyCharge + demandCharge;
    }

    @Override
    public double calculateBill(double extraUnits) {
        return calculateBill() + (extraUnits * getTariffRate());
    }

    @Override
    public void generateEnergyBill() {
        double energyCharge = getUnitsConsumed() * getTariffRate();
        double demandCharge = peakDemandKW * demandChargePerKW;
        
        System.out.println("===== INDUSTRIAL ENERGY BILL =====");
        System.out.println("Consumer : " + getConsumerName() + " | Meter: " + getMeterId());
        System.out.println("Energy Charge : " + getUnitsConsumed() + " kWh x " + getTariffRate() + " = " + energyCharge);
        System.out.println("Demand Charge : " + peakDemandKW + " kW x " + demandChargePerKW + " = " + demandCharge);
        System.out.println("TOTAL BILL : " + calculateBill() + " BDT");
    }

    @Override
    public void displayConsumerInfo() {
        System.out.println("=== Industrial Consumer ===");
        System.out.println("ID: " + getConsumerId() + " | Name: " + getConsumerName() + " | Address: " + getAddress());
        System.out.println("Meter ID: " + getMeterId() + " | Units: " + getUnitsConsumed() + " kWh | Tariff: " + getTariffRate() + "/unit");
        System.out.println("Load Factor: " + loadFactorPercent + "% | Peak Demand: " + peakDemandKW + " kW | Demand Charge: " + demandChargePerKW + "/kW");
    }
}

class HeavyIndustrialConsumer extends IndustrialConsumer {
    private double powerFactorPenalty;
    private double backupGeneratorKVA;

    public HeavyIndustrialConsumer(String consumerId, String consumerName, String address, String meterId, double unitsConsumedKWh, double tariffRatePerUnit, double loadFactorPercent, double peakDemandKW, double demandChargePerKW, double powerFactorPenalty, double backupGeneratorKVA) {
        super(consumerId, consumerName, address, meterId, unitsConsumedKWh, tariffRatePerUnit, loadFactorPercent, peakDemandKW, demandChargePerKW);
        this.powerFactorPenalty = powerFactorPenalty;
        this.backupGeneratorKVA = backupGeneratorKVA;
    }

    private double getGeneratorCharge() {
        // Derived from expected output: 19500.0 / 500.0 kVA = 39.0 per kVA
        return backupGeneratorKVA * 39.0;
    }

    @Override
    public double calculateBill() {
        return super.calculateBill() + powerFactorPenalty + getGeneratorCharge();
    }

    @Override
    public double calculateBill(double extraUnits) {
        return super.calculateBill(extraUnits) + powerFactorPenalty + getGeneratorCharge();
    }

    @Override
    public void generateEnergyBill() {
        double energyCharge = getUnitsConsumed() * getTariffRate();
        double demandCharge = getPeakDemandKW() * getDemandChargePerKW();
        
        System.out.println("===== HEAVY INDUSTRIAL ENERGY BILL =====");
        System.out.println("Consumer : " + getConsumerName());
        System.out.println("Energy Charge : " + energyCharge);
        System.out.println("Demand Charge : " + demandCharge);
        System.out.println("PF Penalty : " + powerFactorPenalty);
        System.out.println("Generator Load : " + backupGeneratorKVA + " kVA");
        System.out.println("Generator Charge : " + getGeneratorCharge());
        System.out.println("TOTAL BILL : " + calculateBill() + " BDT");
    }

    @Override
    public void displayConsumerInfo() {
        System.out.println("=== Heavy Industrial Consumer ===");
        System.out.println("ID: " + getConsumerId() + " | Name: " + getConsumerName() + " | Address: " + getAddress());
        System.out.println("Units: " + getUnitsConsumed() + " kWh | Tariff: " + getTariffRate() + " | Peak Demand: " + getPeakDemandKW() + " kW");
        System.out.println("Power Factor Penalty: " + powerFactorPenalty + " | Generator: " + backupGeneratorKVA + " kVA");
    }
}

class EnergyMeter {
    private String meterSerialNo;
    private String meterType;
    private String installationDate;

    public EnergyMeter(String meterSerialNo, String meterType, String installationDate) {
        this.meterSerialNo = meterSerialNo;
        this.meterType = meterType;
        this.installationDate = installationDate;
    }

    public String getMeterSerialNo() {
        return meterSerialNo;
    }

    public void displayMeterInfo() {
        System.out.println("=== Energy Meter Info ===");
        System.out.println("Serial : " + meterSerialNo + " | Type: " + meterType + " | Installed: " + installationDate);
    }
}

// --- Driver Class ---

public class PowerUtilitySystem {
    public static void main(String[] args) {

        EnergyMeter em = new EnergyMeter(
            "MT-9981", "Smart Meter", "2022-08-10"
        );

        IndustrialConsumer ic = new IndustrialConsumer(
            "C001", "Apex Textiles", "Gazipur",
            em.getMeterSerialNo(),
            12000.0, 8.5, 75.0, 500.0, 150.0
        );

        HeavyIndustrialConsumer hc = new HeavyIndustrialConsumer(
            "C002", "Steel Works Ltd", "Chittagong",
            "MT-7742",
            45000.0, 9.0, 80.0, 1200.0, 200.0,
            25000.0, 500.0
        );

        em.displayMeterInfo();

        ic.displayConsumerInfo();
        System.out.println("Industrial Bill : " + ic.calculateBill());
        System.out.println("With 1000 extra kWh: " + ic.calculateBill(1000));
        ic.generateEnergyBill();

        hc.displayConsumerInfo();
        System.out.println("Heavy Bill : " + hc.calculateBill());
        hc.generateEnergyBill();

        System.out.println("Total Consumers: " + EnergyConsumer.totalConsumers);
    }
}