// Library Book Management System

// --- Core Implementation ---

interface Displayable {
    void displayDetails();
}

abstract class LibraryItem implements Displayable {
    private String itemID;
    private String title;
    private String author;
    public static int totalBooks = 0;

    public LibraryItem(String itemID, String title, String author) {
        this.itemID = itemID;
        this.title = title;
        this.author = author;
        totalBooks++;
    }

    public String getItemID() {
        return itemID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public abstract void displayDetails();
}

abstract class CatalogItem extends LibraryItem {
    private String catalogNumber;
    private boolean isAvailable;

    public CatalogItem(String itemID, String title, String author, String catalogNumber, boolean isAvailable) {
        super(itemID, title, author);
        this.catalogNumber = catalogNumber;
        this.isAvailable = isAvailable;
    }

    public void setAvailable(boolean flag) {
        this.isAvailable = flag;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }
}

class PhysicalBook extends CatalogItem {
    private String shelfNumber;
    private int loanPeriodDays;

    public PhysicalBook(String itemID, String title, String author, String catalogNumber, boolean isAvailable, String shelfNumber, int loanPeriodDays) {
        super(itemID, title, author, catalogNumber, isAvailable);
        this.shelfNumber = shelfNumber;
        this.loanPeriodDays = loanPeriodDays;
    }

    @Override
    public void displayDetails() {
        System.out.println("=== Physical Book ===");
        System.out.println("ID: " + getItemID() + " | Title: " + getTitle() + " | Author: " + getAuthor());
        System.out.println("Shelf: " + shelfNumber + " | Loan Period: " + loanPeriodDays + " days | Available: " + isAvailable());
    }

    public double computeFine(int days) {
        LoanPolicy policy = new LoanPolicy();
        return policy.calculate(days, 10.0); // Using 10.0 as rate to match expected output (5 days = 50.0 fine)
    }

    public static class LoanPolicy {
        public LoanPolicy() {}

        public double calculate(int days, double rate) {
            return days * rate;
        }
    }
}

class EBook extends CatalogItem {
    private double fileSizeMB;
    private String downloadURL;

    public EBook(String itemID, String title, String author, String catalogNumber, boolean isAvailable, double fileSizeMB, String downloadURL) {
        super(itemID, title, author, catalogNumber, isAvailable);
        this.fileSizeMB = fileSizeMB;
        this.downloadURL = downloadURL;
    }

    @Override
    public void displayDetails() {
        System.out.println("=== EBook ===");
        System.out.println("ID: " + getItemID() + " | Title: " + getTitle() + " | Author: " + getAuthor());
        System.out.println("Size: " + fileSizeMB + " MB | URL: " + downloadURL + " | Available: " + isAvailable());
    }

    public boolean canDownload(String tier) {
        AccessPolicy policy = new AccessPolicy();
        return policy.permitted(tier);
    }

    public static class AccessPolicy {
        public AccessPolicy() {}

        public boolean permitted(String tier) {
            return tier.equals("PREMIUM");
        }
    }
}

class LibrarySearch {
    private LibraryItem[] catalog;

    public LibrarySearch(LibraryItem[] catalog) {
        this.catalog = catalog;
    }

    public void search(String title) {
        for (LibraryItem item : catalog) {
            if (item.getTitle().equals(title)) {
                System.out.println("Search by title \"" + title + "\" -> Found: " + item.getItemID() + " - " + item.getTitle());
                return;
            }
        }
    }

    public void search(String itemID, boolean byID) {
        if (byID) {
            for (LibraryItem item : catalog) {
                if (item.getItemID().equals(itemID)) {
                    System.out.println("Search by ID \"" + itemID + "\" -> Found: " + item.getItemID() + " - " + item.getTitle());
                    return;
                }
            }
        }
    }
}

// --- Driver Class ---

public class LibrarySystem {
    public static void main(String[] args) {

        PhysicalBook pb = new PhysicalBook(
            "BK001", "Data Structures", "Lipschutz",
            "DWY-005", true, "B-05", 14
        );

        EBook eb = new EBook(
            "EB001", "Clean Code", "Martin",
            "SE-102", true, 4.5, "lib.com/cc"
        );

        pb.displayDetails();
        System.out.println("Fine: " + pb.computeFine(5));

        eb.displayDetails();
        System.out.println("PREMIUM: " + eb.canDownload("PREMIUM"));
        System.out.println("BASIC : " + eb.canDownload("BASIC"));

        LibraryItem[] catalog = {pb, eb};

        LibrarySearch ls = new LibrarySearch(catalog);
        ls.search("Clean Code");
        ls.search("BK001", true);

        System.out.println("Total Books: " + LibraryItem.totalBooks);
    }
}
