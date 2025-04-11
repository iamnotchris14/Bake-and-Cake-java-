// Import necessary Java utilities
import java.util.ArrayList;
import java.util.HashMap;

// Main Menu class representing items in a bakery menu
public class Menu {
    
    // Private fields to encapsulate menu item data
    private String itemId;       // Unique identifier for the menu item
    private String name;        // Name of the menu item
    private double price;       // Price of the item
    private int quantity;       // Available quantity in stock
    private String category;     // Category the item belongs to (e.g., "Cake", "Bread")
    
    // Static ArrayList to store all menu items (shared across all Menu instances)
    private static ArrayList<Menu> menuList = new ArrayList<>();
    
    // Constructor to initialize a new Menu item
    public Menu(String itemId, String name, double price, int quantity, String category) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
    
    // Displays a single menu item in a formatted way
    // Uses printf for precise formatting of columns
    public void displayMenuItem() {
        System.out.printf("│ %-5s │ %-25s │ $%-6.2f │ %-8d │\n", 
                         itemId, name, price, quantity);
    }

    // Updates the quantity of this menu item
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    // Getter methods for private fields
    public String getCategory() { return category; }
    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // Inventory management static methods
    
    // Adds a new menu item to the shared menuList
    public static void addMenuItem(Menu item) {
        menuList.add(item);
    }
    
    // Displays all menu items grouped by category with formatted output
    public static void displayAllItems() {
        // Check if menu is empty
        if (menuList.isEmpty()) {
            System.out.println("\n⚠️ No menu items available.");
            return;
        }
        
        // Create a HashMap to group items by category
        HashMap<String, ArrayList<Menu>> categorizedItems = new HashMap<>();
        for (Menu item : menuList) {
            // For each category, create a list if not exists, then add item
            categorizedItems.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item);
        }

        System.out.println();
        // Print each category with its items
        for (String category : categorizedItems.keySet()) {
            printCategoryBox(category);  // Print category header
            printItemHeader();          // Print column headers
            // Print each item in the category
            for (Menu item : categorizedItems.get(category)) {
                item.displayMenuItem();
            }
            printItemFooter();           // Print category footer
        }
    }

    // Reduces stock quantity for an item if available
    public static boolean reduceStock(String itemId, int quantity) {
        Menu item = searchItemById(itemId);
        // Check if item exists and has sufficient quantity
        if (item != null && item.getQuantity() >= quantity) {
            item.updateQuantity(item.getQuantity() - quantity);  // Update quantity
            return true;  // Success
        }
        return false;  // Failure
    }

    // Helper method to print a formatted category header box
    public static void printCategoryBox(String category) {
        String border = "┌────────────────────────────────────────────────────────┐";
        String middle = String.format("│ %-54s │", "  " + category.toUpperCase());
        System.out.println(border + "\n" + middle + "\n" + border);
    }

    // Helper method to print the item table header
    public static void printItemHeader() {
        System.out.println("├───────┼───────────────────────────┼─────────┼──────────┤");
        System.out.printf("│ %-5s │ %-25s │ %-7s │ %-8s │\n", "ID", "Name", "Price", "Quantity");
        System.out.println("├───────┼───────────────────────────┼─────────┼──────────┤");
    }

    // Helper method to print the item table footer
    public static void printItemFooter() {
        System.out.println("└───────┴───────────────────────────┴─────────┴──────────┘\n");
    }

    // Searches for a menu item by its ID
    public static Menu searchItemById(String itemId) {
        // Linear search through menuList
        for (Menu item : menuList) {
            if (item.itemId.equals(itemId)) {
                return item;  // Return found item
            }
        }
        return null;  // Return null if not found
    }

    // Removes a menu item from the list by its ID
    public static void removeMenuItem(String itemId) {
        Menu item = searchItemById(itemId);
        if (item != null) {
            menuList.remove(item);  // Remove if found
            System.out.println("✅ Item removed successfully.");
        } else {
            System.out.println("❌ Item not found.");  // Error message if not found
        }
    }
}