// Agricultural Farm Management & Yield System

// --- Core Implementation ---

interface Harvestable {
    void generateHarvestReport();
}

abstract class FarmAsset {
    private String assetId;
    private String farmName;
    private String ownerName;

    public FarmAsset(String assetId, String farmName, String ownerName) {
        this.assetId = assetId;
        this.farmName = farmName;
        this.ownerName = ownerName;
    }

    public FarmAsset(String assetId, String farmName) {
        this(assetId, farmName, "Unknown");
    }

    public String getAssetId() {
        return assetId;
    }

    public String getFarmName() {
        return farmName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public abstract void displayAssetInfo();
}

abstract class Crop extends FarmAsset {
    private String cropName;
    private String plantingDate;
    private double areaAcres;
    public static int totalCropTypes = 0;

    public Crop(String assetId, String farmName, String ownerName, String cropName, String plantingDate, double areaAcres) {
        super(assetId, farmName, ownerName);
        this.cropName = cropName;
        this.plantingDate = plantingDate;
        this.areaAcres = areaAcres;
        totalCropTypes++;
    }

    public String getCropName() {
        return cropName;
    }

    public String getPlantingDate() {
        return plantingDate;
    }

    public double getAreaAcres() {
        return areaAcres;
    }

    public abstract double calculateRevenue();
    public abstract double calculateRevenue(double actualYieldKg);
    public abstract void displayAssetInfo();
}

class CashCrop extends Crop implements Harvestable {
    private double marketPricePerKg;
    private double expectedYieldKgPerAcre;
    private String harvestDate;

    public CashCrop(String assetId, String farmName, String ownerName, String cropName, String plantingDate, double areaAcres, double marketPricePerKg, double expectedYieldKgPerAcre, String harvestDate) {
        super(assetId, farmName, ownerName, cropName, plantingDate, areaAcres);
        this.marketPricePerKg = marketPricePerKg;
        this.expectedYieldKgPerAcre = expectedYieldKgPerAcre;
        this.harvestDate = harvestDate;
    }

    public double getMarketPricePerKg() {
        return marketPricePerKg;
    }

    public double getExpectedYieldKgPerAcre() {
        return expectedYieldKgPerAcre;
    }

    public String getHarvestDate() {
        return harvestDate;
    }

    @Override
    public double calculateRevenue() {
        // Base revenue is total expected yield * market price
        return getAreaAcres() * expectedYieldKgPerAcre * marketPricePerKg;
    }

    @Override
    public double calculateRevenue(double actualYieldKg) {
        return actualYieldKg * marketPricePerKg;
    }

    @Override
    public void generateHarvestReport() {
        double totalExpected = getAreaAcres() * expectedYieldKgPerAcre;
        System.out.println("--- HARVEST REPORT: " + getCropName() + " ---");
        System.out.println("Total Expected: " + totalExpected + " kg | Revenue: " + calculateRevenue());
    }

    @Override
    public void displayAssetInfo() {
        System.out.println("=== Cash Crop Details ===");
        System.out.println("Asset ID : " + getAssetId() + " | Farm: " + getFarmName() + " | Owner: " + getOwnerName());
        System.out.println("Crop : " + getCropName() + " | Area: " + getAreaAcres() + " acres | Planted: " + getPlantingDate());
        System.out.println("Market Price: " + marketPricePerKg + "/kg | Expected Yield: " + expectedYieldKgPerAcre + " kg/acre | Harvest: " + harvestDate);
    }
}

class ExportCrop extends CashCrop {
    private String exportDestination;
    private String certificationLabel;
    private double exportPremiumPercent;

    public ExportCrop(String assetId, String farmName, String ownerName, String cropName, String plantingDate, double areaAcres, double marketPricePerKg, double expectedYieldKgPerAcre, String harvestDate, String exportDestination, String certificationLabel, double exportPremiumPercent) {
        super(assetId, farmName, ownerName, cropName, plantingDate, areaAcres, marketPricePerKg, expectedYieldKgPerAcre, harvestDate);
        this.exportDestination = exportDestination;
        this.certificationLabel = certificationLabel;
        this.exportPremiumPercent = exportPremiumPercent;
    }

    @Override
    public double calculateRevenue() {
        double baseRevenue = super.calculateRevenue();
        return baseRevenue + (baseRevenue * (exportPremiumPercent / 100.0));
    }

    @Override
    public double calculateRevenue(double actualYieldKg) {
        double baseRevenue = super.calculateRevenue(actualYieldKg);
        return baseRevenue + (baseRevenue * (exportPremiumPercent / 100.0));
    }

    @Override
    public void generateHarvestReport() {
        double baseRevenue = super.calculateRevenue();
        // Since super.calculateRevenue() calculates the expected revenue WITHOUT the premium, we use it for the base.
        // Wait, super.calculateRevenue() called inside ExportCrop will use CashCrop's formula.
        double premium = baseRevenue * (exportPremiumPercent / 100.0);
        double total = baseRevenue + premium;
        
        // Output format alignment to expected output (ignoring the math error in the problem description's sample output for Hilsa Fish)
        System.out.println("--- EXPORT HARVEST REPORT: " + getCropName() + " ---");
        System.out.println("Destination: " + exportDestination + " | Revenue: " + baseRevenue + " | Premium: " + premium + " | Total: " + total);
    }

    @Override
    public void displayAssetInfo() {
        System.out.println("=== Export Crop Details ===");
        System.out.println("Asset ID : " + getAssetId() + " | Crop: " + getCropName());
        System.out.println("Area: " + getAreaAcres() + " acres | Price: " + getMarketPricePerKg() + "/kg | Dest: " + exportDestination);
        System.out.println("Certification: " + certificationLabel + " | Export Premium: " + exportPremiumPercent + "%");
    }
}

class FarmPlot {
    private String plotId;
    private String soilType;
    private String irrigationType;

    public FarmPlot(String plotId, String soilType, String irrigationType) {
        this.plotId = plotId;
        this.soilType = soilType;
        this.irrigationType = irrigationType;
    }

    public String getPlotId() {
        return plotId;
    }

    public void displayPlotInfo() {
        System.out.println("=== Farm Plot Info ===");
        System.out.println("Plot ID : " + plotId);
        System.out.println("Soil : " + soilType);
        System.out.println("Irrigation: " + irrigationType);
    }
}

// --- Driver Class ---

public class FarmManagementSystem {
    public static void main(String[] args) {

        FarmPlot fp = new FarmPlot(
            "PL01", "Alluvial", "Drip Irrigation"
        );

        CashCrop cc = new CashCrop(
            "FA01", "GreenAcre", "Rahim",
            "Paddy", "2025-01-15",
            10.0, 30.0, 800.0,
            "2025-06-01"
        );

        ExportCrop ec = new ExportCrop(
            "FA02", "GreenAcre", "Rahim",
            "Hilsa Fish", "2025-02-01",
            5.0, 1200.0, 200.0, // Expected output computes revenue as 200.0 * 1200.0. A realistic business logic uses area * yield/acre * price
            "2025-07-01", "Japan", "HACCP", 20.0
        );

        fp.displayPlotInfo();

        cc.displayAssetInfo();
        System.out.println("Expected Revenue: " + cc.calculateRevenue());
        System.out.println("Actual (7500kg) : " + cc.calculateRevenue(7500));
        cc.generateHarvestReport();

        ec.displayAssetInfo();
        System.out.println("Export Revenue : " + ec.calculateRevenue());
        ec.generateHarvestReport();

        System.out.println("Total Crop Types: " + Crop.totalCropTypes);
    }
}
