// Problem 4: E-Commerce Product Order System

// --- Core Implementation ---

interface Orderable {
    void placeOrder();
}

abstract class Item {
    private String itemID;
    private String name;
    private double price;
    public static int totalProducts = 0;

    public Item(String itemID, String name, double price) {
        this.itemID = itemID;
        this.name = name;
        this.price = price;
        totalProducts++;
    }

    public String getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public abstract double computeFinalPrice();

    public void displayDetails() {
        System.out.print("ID: " + itemID + " | Name: " + name);
    }
}

abstract class Product extends Item implements Orderable {
    private String category;
    private int stockQuantity;

    public Product(String itemID, String name, double price, String category, int stockQuantity) {
        super(itemID, name, price);
        this.category = category;
        this.stockQuantity = stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void reduceStock(int qty) {
        this.stockQuantity -= qty;
    }

    public abstract void placeOrder();
}

class Electronics extends Product {
    private String brand;
    private int warrantyMonths;

    public Electronics(String itemID, String name, double price, String category, int stockQuantity, String brand, int warrantyMonths) {
        super(itemID, name, price, category, stockQuantity);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public double computeFinalPrice() {
        WarrantyPricing wp = new WarrantyPricing();
        return getPrice() + wp.surcharge(warrantyMonths);
    }

    @Override
    public void placeOrder() {
        int oldStock = getStockQuantity();
        reduceStock(1);
        System.out.println("Order Placed! Stock: " + oldStock + " -> " + getStockQuantity());
    }

    @Override
    public void displayDetails() {
        System.out.println("=== Electronics ===");
        super.displayDetails();
        System.out.println(" | Category: " + getCategory() + " | Base: " + getPrice());
        System.out.println("Brand: " + brand + " | Warranty: " + warrantyMonths + " months");
        WarrantyPricing wp = new WarrantyPricing();
        System.out.println("WarrantyPricing surcharge: " + wp.surcharge(warrantyMonths));
    }

    public static class WarrantyPricing {
        public WarrantyPricing() {}

        public double surcharge(int warrantyMonths) {
            return warrantyMonths * 50.0;
        }
    }
}

class Clothing extends Product {
    private String size;
    private String material;

    public Clothing(String itemID, String name, double price, String category, int stockQuantity, String size, String material) {
        super(itemID, name, price, category, stockQuantity);
        this.size = size;
        this.material = material;
    }

    @Override
    public double computeFinalPrice() {
        SeasonalDiscount sd = new SeasonalDiscount();
        return sd.apply(getPrice(), 10.0);
    }

    @Override
    public void placeOrder() {
        int oldStock = getStockQuantity();
        reduceStock(1);
        System.out.println("Order Placed! Stock: " + oldStock + " -> " + getStockQuantity());
    }

    @Override
    public void displayDetails() {
        System.out.println("=== Clothing ===");
        super.displayDetails();
        System.out.println(" | Category: " + getCategory() + " | Base: " + getPrice());
        System.out.println("Size: " + size + " | Material: " + material);
        SeasonalDiscount sd = new SeasonalDiscount();
        System.out.println("SeasonalDiscount (10%): " + sd.apply(getPrice(), 10.0));
    }

    public static class SeasonalDiscount {
        public SeasonalDiscount() {}

        public double apply(double price, double rate) {
            return price - (price * rate / 100.0);
        }
    }
}

class DiscountEngine {
    public DiscountEngine() {}

    public double applyDiscount(double price, double flatAmount) {
        return price - flatAmount;
    }

    public double applyDiscount(double price, double percent, boolean isPercent) {
        if (isPercent) {
            return price - (price * percent / 100.0);
        }
        return price - percent;
    }
}

// --- Driver Class ---

public class ShopNow {
    public static void main(String[] args) {

        Electronics elec = new Electronics(
            "E001", "Laptop", 55000, "Electronics", 10, "Dell", 24
        );

        Clothing cloth = new Clothing(
            "C001", "T-Shirt", 500, "Clothing", 50, "M", "Cotton"
        );

        elec.displayDetails();
        System.out.println("Final Price: " + elec.computeFinalPrice());
        elec.placeOrder();

        cloth.displayDetails();
        System.out.println("Final Price: " + cloth.computeFinalPrice());
        cloth.placeOrder();

        DiscountEngine de = new DiscountEngine();

        System.out.println("Flat : " + de.applyDiscount(55000, 2000));
        System.out.println("Percent : " + de.applyDiscount(500, 10, true));

        System.out.println("Total Products: " + Item.totalProducts);
    }
}