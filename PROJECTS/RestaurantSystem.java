// Restaurant Food Order Management System

// --- Core Implementation ---

abstract class MenuItem {
    private String itemCode;
    private String itemName;
    private double unitPrice;
    public static int totalItems = 0;

    public MenuItem(String itemCode, String itemName, double unitPrice) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        totalItems++;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double orderTotal(int quantity) {
        return unitPrice * quantity;
    }

    public double orderTotal(int quantity, double disc) {
        return (unitPrice * quantity) * (1 - disc / 100.0);
    }

    public abstract void displayDetails();

    public static class Category {
        public String categoryCode;
        public String description;

        public Category(String categoryCode, String description) {
            this.categoryCode = categoryCode;
            this.description = description;
        }

        public void display() {
            System.out.println("Category: " + categoryCode + " | " + description);
        }
    }
}

class FoodItem extends MenuItem {
    private String cuisineType;
    private int prepTimeMins;

    public FoodItem(String itemCode, String itemName, double unitPrice, String cuisineType, int prepTimeMins) {
        super(itemCode, itemName, unitPrice);
        this.cuisineType = cuisineType;
        this.prepTimeMins = prepTimeMins;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public int getPrepTimeMins() {
        return prepTimeMins;
    }

    @Override
    public void displayDetails() {
        System.out.println("=== Food Item Details ===");
        System.out.println("Item Code : " + getItemCode());
        System.out.println("Name : " + getItemName());
        System.out.println("Unit Price : " + getUnitPrice());
        System.out.println("Cuisine : " + cuisineType);
        System.out.println("Prep Time : " + prepTimeMins + " mins");
    }

    public class OrderTicket {
        public int tableNumber;
        public int quantity;

        public OrderTicket(int tableNumber, int quantity) {
            this.tableNumber = tableNumber;
            this.quantity = quantity;
        }

        public void printTicket() {
            System.out.println("--- Order Ticket ---");
            System.out.println("Table : " + tableNumber);
            System.out.println("Item  : " + getItemCode() + " - " + getItemName());
            System.out.println("Price : " + getUnitPrice() + " x " + quantity + " = " + orderTotal(quantity));
        }
    }
}

class ChefSpecial extends FoodItem {
    private String chefName;
    private double signatureDiscount;

    public ChefSpecial(String itemCode, String itemName, double unitPrice, String cuisineType, int prepTimeMins, String chefName, double signatureDiscount) {
        super(itemCode, itemName, unitPrice, cuisineType, prepTimeMins);
        this.chefName = chefName;
        this.signatureDiscount = signatureDiscount;
    }

    @Override
    public double orderTotal(int quantity) {
        return super.orderTotal(quantity, signatureDiscount);
    }

    @Override
    public void displayDetails() {
        System.out.println("=== Chef Special Details ===");
        System.out.println("Item Code : " + getItemCode());
        System.out.println("Name : " + getItemName());
        System.out.println("Unit Price : " + getUnitPrice());
        System.out.println("Cuisine : " + getCuisineType());
        System.out.println("Prep Time : " + getPrepTimeMins() + " mins");
        System.out.println("Chef : " + chefName);
        System.out.println("Signature Disc: " + signatureDiscount + "%");
    }
}

class BeverageItem extends MenuItem {
    private String temperature;
    private int volumeMl;

    public BeverageItem(String itemCode, String itemName, double unitPrice, String temperature, int volumeMl) {
        super(itemCode, itemName, unitPrice);
        this.temperature = temperature;
        this.volumeMl = volumeMl;
    }

    @Override
    public void displayDetails() {
        System.out.println("=== Beverage Details ===");
        System.out.println("Item Code : " + getItemCode());
        System.out.println("Name : " + getItemName());
        System.out.println("Unit Price: " + getUnitPrice());
        System.out.println("Temperature: " + temperature);
        System.out.println("Volume : " + volumeMl + " ml");
    }
}

// --- Driver Class ---

public class RestaurantSystem {
    public static void main(String[] args) {

        MenuItem.Category cat = new MenuItem.Category("MAIN", "Main Course");
        cat.display();

        ChefSpecial cs = new ChefSpecial(
            "CS01", "Hilsa Curry", 400.0, "Bengali",
            25, "Chef Rahim", 10.0
        );

        BeverageItem b = new BeverageItem(
            "BV01", "Mango Lassi", 120.0, "Cold", 300
        );

        cs.displayDetails();

        System.out.println("ChefSpecial total (3 qty) : " + cs.orderTotal(3));
        System.out.println("Regular total (3 qty, 5% off): " + cs.orderTotal(3, 5.0));

        FoodItem.OrderTicket ot = cs.new OrderTicket(7, 3);
        ot.printTicket();

        b.displayDetails();
        System.out.println("Beverage total (5 qty) : " + b.orderTotal(5));

        System.out.println("Total Items: " + MenuItem.totalItems);
    }
}
