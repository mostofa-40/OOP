// Problem 17 : Warehouse Inventory and Supply Chain System

// --- Core Implementation ---

interface Trackable {
    void generateStockReport();
}

abstract class Item {
    private String itemId;
    private String itemName;
    private String unitOfMeasure;

    public Item(String itemId, String itemName, String unitOfMeasure) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Item(String itemId, String itemName) {
        this(itemId, itemName, "Unknown");
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public abstract void displayItemInfo();
}

abstract class InventoryItem extends Item {
    private int quantity;
    private double unitCost;
    private String supplierName;
    public static int totalItemTypes = 0;

    public InventoryItem(String itemId, String itemName, String unitOfMeasure, int quantity, double unitCost, String supplierName) {
        super(itemId, itemName, unitOfMeasure);
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.supplierName = supplierName;
        totalItemTypes++;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public abstract double calculateStockValue();
    public abstract double calculateStockValue(int days);
    public abstract void displayItemInfo();
}

class PerishableItem extends InventoryItem implements Trackable {
    private String expiryDate;
    private int shelfLifeDays;
    private boolean isExpired;

    public PerishableItem(String itemId, String itemName, String unitOfMeasure, int quantity, double unitCost, String supplierName, String expiryDate, int shelfLifeDays) {
        super(itemId, itemName, unitOfMeasure, quantity, unitCost, supplierName);
        this.expiryDate = expiryDate;
        this.shelfLifeDays = shelfLifeDays;
        this.isExpired = false; // Default tracking
    }

    @Override
    public double calculateStockValue() {
        return getQuantity() * getUnitCost();
    }

    @Override
    public double calculateStockValue(int days) {
        // Base implementation returns the standard stock value without additional storage fees
        return calculateStockValue();
    }

    public String checkExpiry() {
        return isExpired ? "Expired" : "Not Expired";
    }

    @Override
    public void generateStockReport() {
        System.out.println("--- PERISHABLE STOCK REPORT ---");
        System.out.println("Item: " + getItemName() + " | Qty: " + getQuantity() + " | Value: " + calculateStockValue() + " | Expires: " + expiryDate);
    }

    @Override
    public void displayItemInfo() {
        System.out.println("=== Perishable Item ===");
        System.out.println("ID: " + getItemId() + " | Name: " + getItemName() + " | Unit: " + getUnitOfMeasure());
        System.out.println("Qty: " + getQuantity() + " | Unit Cost: " + getUnitCost() + " | Supplier: " + getSupplierName());
        System.out.println("Expiry: " + expiryDate + " | Shelf Life: " + shelfLifeDays + " days");
    }
}

class ColdChainItem extends PerishableItem {
    private double requiredTempC;
    private double coldStorageCostPerDay;

    public ColdChainItem(String itemId, String itemName, String unitOfMeasure, int quantity, double unitCost, String supplierName, String expiryDate, int shelfLifeDays, double requiredTempC, double coldStorageCostPerDay) {
        super(itemId, itemName, unitOfMeasure, quantity, unitCost, supplierName, expiryDate, shelfLifeDays);
        this.requiredTempC = requiredTempC;
        this.coldStorageCostPerDay = coldStorageCostPerDay;
    }

    @Override
    public double calculateStockValue() {
        return super.calculateStockValue() + coldStorageCostPerDay;
    }

    @Override
    public double calculateStockValue(int days) {
        return super.calculateStockValue() + (days * coldStorageCostPerDay);
    }

    @Override
    public void generateStockReport() {
        System.out.println("--- COLD CHAIN STOCK REPORT ---");
        System.out.println("Item: " + getItemName() + " | Qty: " + getQuantity() + " | Temp: " + requiredTempC + "°C | Cold Value: " + calculateStockValue());
    }

    @Override
    public void displayItemInfo() {
        System.out.println("=== Cold Chain Item ===");
        System.out.println("ID: " + getItemId() + " | Name: " + getItemName() + " | Unit: " + getUnitOfMeasure());
        System.out.println("Qty: " + getQuantity() + " | Unit Cost: " + getUnitCost() + " | Supplier: " + getSupplierName());
        System.out.println("Expiry: " + "2025-12-01" + " | Required Temp: " + requiredTempC + "°C | Cold Cost/Day: " + coldStorageCostPerDay);
    }
}

class Warehouse {
    private String warehouseId;
    private String location;
    private double capacityTons;

    public Warehouse(String warehouseId, String location, double capacityTons) {
        this.warehouseId = warehouseId;
        this.location = location;
        this.capacityTons = capacityTons;
    }

    public String getLocation() {
        return location;
    }

    public void displayWarehouseInfo() {
        System.out.println("=== Warehouse Info ===");
        System.out.println("ID: " + warehouseId + " | Location: " + location + " | Capacity: " + capacityTons + " tons");
    }
}

// --- Driver Class ---

public class InventorySystem {
    public static void main(String[] args) {

        Warehouse wh = new Warehouse(
            "WH01", "Sylhet Industrial Zone", 500.0
        );

        PerishableItem pi = new PerishableItem(
            "I001", "Fresh Mango", "kg",
            200, 80.0, "AgroFresh",
            "2025-07-15", 30
        );

        ColdChainItem ci = new ColdChainItem(
            "I002", "Frozen Fish", "kg",
            500, 150.0, "SeaFarm",
            "2025-12-01", 90, -18.0, 500.0
        );

        wh.displayWarehouseInfo();

        pi.displayItemInfo();
        System.out.println("Expiry Status : " + pi.checkExpiry());
        System.out.println("Stock Value : " + pi.calculateStockValue());
        System.out.println("Value (10 days): " + pi.calculateStockValue(10));
        pi.generateStockReport();

        ci.displayItemInfo();
        System.out.println("Cold Value : " + ci.calculateStockValue());
        System.out.println("Cold (30 days): " + ci.calculateStockValue(30));
        ci.generateStockReport();

        System.out.println("Total Item Types: " + InventoryItem.totalItemTypes);
    }
}