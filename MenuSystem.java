import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Menu class to represent menu items
class Menu {
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

    public void displayMenuItem() {
        System.out.printf("│ %-5s │ %-25s │ $%-6.2f │ %-8d │\n", 
                         itemId, name, price, quantity);
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // Inventory functions
    public static void addMenuItem(Menu item) {
        menuList.add(item);
    }

    public static void displayAllItems() {
        if (menuList.isEmpty()) {
            System.out.println("\n⚠️ No menu items available.");
            return;
        }
        
        // Group items by category
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

// MenuSystem class for UI interaction
public class MenuSystem {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // Sample menu items
        Menu.addMenuItem(new Menu("A01", "Chocolate Muffin", 5.99,5, "Muffins"));
        Menu.addMenuItem(new Menu("A02", "Vanilla Muffin", 5.99,5, "Muffins"));
        Menu.addMenuItem(new Menu("B01", "Hokkaido Burnt Cheesecake", 9.99, 5, "Cakes"));
        Menu.addMenuItem(new Menu("B02", "Chocolate Indulgence", 7.49, 5, "Cakes"));
        Menu.addMenuItem(new Menu("C01", "Sourdough Bread", 5.99,5, "Bread"));
        Menu.addMenuItem(new Menu("001", "Sausage Bun", 5.99,5, "Bread"));
    

        while (true) {
            printHeader(" MENU SYSTEM ");
            System.out.println("1. Admin Panel");
            System.out.println("2. Customer View");
            System.out.println("3. Exit");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminPanel(scanner);
                    break;

                case 2:
                    customerView(scanner);
                    break;

                case 3:
                    System.out.print(GREEN + "\n🚪 Exiting");
                    loadingEffect();
                    System.out.println("\n👋 Goodbye!" + RESET);
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println(RED + "\n❌ Invalid option. Please try again." + RESET);
            }

            Thread.sleep(1000);
        }
    }

    private static void adminPanel(Scanner scanner) throws InterruptedException {
        while (true) {
            printHeader(" ADMIN PANEL ");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Remove Menu Item");
            System.out.println("3. Update Stock");
            System.out.println("4. View Menu");
            System.out.println("5. Back to Main Menu");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("📌 Enter Item ID: ");
                    String itemId = scanner.nextLine();
                    System.out.print("📌 Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("💲 Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("📦 Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("📁 Enter Category: ");
                    String category = scanner.nextLine();

                    Menu.addMenuItem(new Menu(itemId, name, price, quantity, category));
                    System.out.println(GREEN + "✅ Item added successfully!" + RESET);
                    break;

                case 2:
                    System.out.print("❌ Enter Item ID to Remove: ");
                    String removeId = scanner.nextLine();
                    Menu.removeMenuItem(removeId);
                    break;

                case 3:
                    System.out.print("🔄 Enter Item ID to Update: ");
                    String updateId = scanner.nextLine();
                    Menu item = Menu.searchItemById(updateId);
                    if (item != null) {
                        System.out.print("📦 Enter New Quantity: ");
                        int newQuantity = scanner.nextInt();
                        scanner.nextLine();
                        item.updateQuantity(newQuantity);
                        System.out.println(GREEN + "✅ Stock updated successfully!" + RESET);
                    } else {
                        System.out.println(RED + "❌ Item not found." + RESET);
                    }
                    break;

                case 4:
                    Menu.displayAllItems();
                    break;

                case 5:
                    return;

                default:
                    System.out.println(RED + "\n❌ Invalid option. Please try again." + RESET);
            }
        }
    }

    private static void customerView(Scanner scanner) throws InterruptedException {
        while (true) {
            printHeader(" CUSTOMER VIEW ");
            System.out.println("1. View Menu");
            System.out.println("2. Search Item by ID");
            System.out.println("3. Back to Main Menu");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Menu.displayAllItems();
                    break;

                case 2:
                    System.out.print("🔎 Enter Item ID to Search: ");
                    String searchId = scanner.nextLine();
                    Menu item = Menu.searchItemById(searchId);
                    if (item != null) {
                        System.out.println();
                        Menu.printCategoryBox(item.getCategory());
                        Menu.printItemHeader();
                        item.displayMenuItem();
                        Menu.printItemFooter();
                    } else {
                        System.out.println(RED + "❌ Item not found." + RESET);
                    }
                    break;

                case 3:
                    return;

                default:
                    System.out.println(RED + "\n❌ Invalid option. Please try again." + RESET);
            }
        }
    }

    private static void loadingEffect() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            Thread.sleep(500);
        }
    }

    private static void printHeader(String title) {
        String border = "==============================";
        System.out.println("\n" + border);
        System.out.println("   " + title);
        System.out.println(border);
    }
}