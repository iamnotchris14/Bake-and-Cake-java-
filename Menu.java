import java.util.ArrayList;
import java.util.HashMap;

public class Menu {
    
    private String itemId;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private static ArrayList<Menu> menuList = new ArrayList<>();

    public Menu(String itemId, String name, double price, int quantity, String category) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
    // Polymorphic method (can be overridden)
    public void displayMenuItem() {
        System.out.printf("│ %-5s │ %-25s │ $%-6.2f │ %-8d │\n", 
                         itemId, name, price, quantity);
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    // Getters
    public String getCategory() { return category; }
    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // Inventory functions
    public static void addMenuItem(Menu item) {
        menuList.add(item);
    }
    // Static method working with all Menu items
    public static void displayAllItems() {
        if (menuList.isEmpty()) {
            System.out.println("\n⚠️ No menu items available.");
            return;
        }
        
        HashMap<String, ArrayList<Menu>> categorizedItems = new HashMap<>();
        for (Menu item : menuList) {
            categorizedItems.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item);
        }

        System.out.println();
        for (String category : categorizedItems.keySet()) {
            printCategoryBox(category);
            printItemHeader();
            for (Menu item : categorizedItems.get(category)) {
                item.displayMenuItem();
            }
            printItemFooter();
        }
    }

    public static boolean reduceStock(String itemId, int quantity) {
        Menu item = searchItemById(itemId);
        if (item != null && item.getQuantity() >= quantity) {
            item.updateQuantity(item.getQuantity() - quantity);
            return true;
        }
        return false;
    }

    public static void printCategoryBox(String category) {
        String border = "┌────────────────────────────────────────────────────────┐";
        String middle = String.format("│ %-54s │", "  " + category.toUpperCase());
        System.out.println(border + "\n" + middle + "\n" + border);
    }

    public static void printItemHeader() {
        System.out.println("├───────┼───────────────────────────┼─────────┼──────────┤");
        System.out.printf("│ %-5s │ %-25s │ %-7s │ %-8s │\n", "ID", "Name", "Price", "Quantity");
        System.out.println("├───────┼───────────────────────────┼─────────┼──────────┤");
    }

    public static void printItemFooter() {
        System.out.println("└───────┴───────────────────────────┴─────────┴──────────┘\n");
    }

    public static Menu searchItemById(String itemId) {
        for (Menu item : menuList) {
            if (item.itemId.equals(itemId)) {
                return item;
            }
        }
        return null;
    }

    public static void removeMenuItem(String itemId) {
        Menu item = searchItemById(itemId);
        if (item != null) {
            menuList.remove(item);
            System.out.println("✅ Item removed successfully.");
        } else {
            System.out.println("❌ Item not found.");
        }
    }
}