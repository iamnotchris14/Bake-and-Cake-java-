import java.util.Scanner;

public class BakerySystem {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        // Initialize sample data
        initializeSampleData();
        
        // Start the system
        loginSystem();
    }

    private static void initializeSampleData() {
        // Sample menu items
        Menu.addMenuItem(new Menu("A01", "Chocolate Muffin", 5.99, 5, "Muffins"));
        Menu.addMenuItem(new Menu("A02", "Vanilla Muffin", 5.99, 5, "Muffins"));
        Menu.addMenuItem(new Menu("B01", "Hokkaido Burnt Cheesecake", 9.99, 5, "Cakes"));
        Menu.addMenuItem(new Menu("B02", "Chocolate Indulgence", 7.49, 5, "Cakes"));
        Menu.addMenuItem(new Menu("C01", "Sourdough Bread", 5.99, 5, "Bread"));
        Menu.addMenuItem(new Menu("001", "Sausage Bun", 5.99, 5, "Bread"));
    }

    private static void loginSystem() throws InterruptedException {
        Login admin = new Login("admin", "admin123", true);
        Login customer = new Login("customer", "customer123", false);

        while (true) {
            printHeader(" BAKERY MANAGEMENT SYSTEM ");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    if (handleLogin(admin, "Admin")) {
                        adminPanel();
                    }
                    break;
                case 2:
                    if (handleLogin(customer, "Customer")) {
                        customerView();
                    }
                    break;
                case 3:
                    System.out.println(GREEN + "\n🚪 Exiting... Goodbye!" + RESET);
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println(RED + "\n❌ Invalid option. Please try again." + RESET);
            }
            Thread.sleep(1000);
        }
    }

    private static boolean handleLogin(Login user, String userType) throws InterruptedException {
        System.out.print("\n🔑 Enter username: ");
        String username = scanner.nextLine();
        System.out.print("🔒 Enter password: ");
        String password = scanner.nextLine();

        System.out.print("🔄 Logging in");
        loadingEffect();

        if (user.authenticate(username, password)) {
            System.out.println(GREEN + "\n✅ " + userType + " login successful!" + RESET);
            Thread.sleep(1000);
            return true;
        } else {
            System.out.println(RED + "\n❌ Invalid username or password." + RESET);
            Thread.sleep(1000);
            return false;
        }
    }

    private static void adminPanel() throws InterruptedException {
        while (true) {
            printHeader(" ADMIN PANEL ");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Remove Menu Item");
            System.out.println("3. Update Stock");
            System.out.println("4. View Menu");
            System.out.println("5. Logout");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMenuItem();
                    break;
                case 2:
                    removeMenuItem();
                    break;
                case 3:
                    updateStock();
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

    private static void addMenuItem() {
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
    }

    private static void removeMenuItem() {
        System.out.print("❌ Enter Item ID to Remove: ");
        String removeId = scanner.nextLine();
        Menu.removeMenuItem(removeId);
    }

    private static void updateStock() {
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
    }

    private static void customerView() throws InterruptedException {
        while (true) {
            printHeader(" CUSTOMER VIEW ");
            System.out.println("1. View Menu");
            System.out.println("2. Search Item by ID");
            System.out.println("3. Logout");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Menu.displayAllItems();
                    break;
                case 2:
                    searchItem();
                    break;
                case 3:
                    return;
                default:
                    System.out.println(RED + "\n❌ Invalid option. Please try again." + RESET);
            }
        }
    }

    private static void searchItem() {
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