import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BakerySystem {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String PURPLE = "\u001B[35m";
    private static final String BLUE = "\u001B[34m";
    
    private static Scanner scanner = new Scanner(System.in);
    private static List<Order> orders = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        initializeSampleData(); //Uses menu class to add sample items for viewing
        loginSystem(); //Starts the login process
    }
    //Menu
    private static void initializeSampleData() {
        Menu.addMenuItem(new Menu("A01", "Chocolate Muffin", 5.99, 10, "Muffins"));
        Menu.addMenuItem(new Menu("A02", "Blueberry Muffin", 5.99, 10, "Muffins"));
        Menu.addMenuItem(new Menu("B01", "Hokkaido Cheesecake", 9.99, 8, "Cakes"));
        Menu.addMenuItem(new Menu("B02", "Chocolate Indulgence", 7.49, 8, "Cakes"));
        Menu.addMenuItem(new Menu("C01", "Sourdough Bread", 5.99, 15, "Bread"));
        Menu.addMenuItem(new Menu("C02", "Sausage Bun", 5.99, 15, "Bread"));
        Menu.addMenuItem(new Menu("D01", "Almond Croissant", 4.49, 12, "Pastries"));
        Menu.addMenuItem(new Menu("D02", "Cinnamon Roll", 3.99, 12, "Pastries"));
    }
    //login
    private static void loginSystem() throws InterruptedException {
        Login admin = new Login("admin", "admin123", true);
        Login customer = new Login("customer", "customer123", false);

        while (true) {
            printHeader(" BAKE AND CAKE ");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: //Uses login class for admin authentication
                    if (handleLogin(admin, "Admin")) {
                        adminPanel();
                    }
                    break;
                case 2: //Uses login class for customer authentication
                    if (handleLogin(customer, "Customer")) {
                        customerView();
                    }
                    break;
                case 3:
                    exitSystem();
                default:
                    System.out.println(RED + "\n❌ Invalid option. Please try again." + RESET);
            }
            Thread.sleep(1000);
        }
    }

    private static void adminPanel() throws InterruptedException {
        while (true) {
            printHeader(" ADMIN PANEL ");
            System.out.println("1. Create Menu Item");
            System.out.println("2. Remove Menu Item");
            System.out.println("3. Update Stock");
            System.out.println("4. View Menu");
            System.out.println("5. View All Orders");
            System.out.println("6. Generate Sales Report");
            System.out.println("7. Logout");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMenuItem(); //Uses the menu class to create items
                    break;
                case 2:
                    removeMenuItem(); //Uses the menu class to remove items 
                    break;
                case 3:
                    updateStock(); //Uses the menu class to update the stocks
                    break;
                case 4:
                    Menu.displayAllItems(); //Uses the menu class to display all items
                    break;
                case 5:
                    viewAllOrders(); 
                    break;
                case 6:
                    generateSalesReport();
                    break;
                case 7:
                    return;
                default:
                    System.out.println(RED + "\n❌ Invalid option. Please try again." + RESET);
            }
        }
    }
    
    public static void generateSalesReport() {
        if (orders.isEmpty()) {
            System.out.println(YELLOW + "\nℹ No orders have been placed yet." + RESET);
            return;
        }
        
        double totalRevenue = 0;
        int totalItemsSold = 0;
        
        System.out.println("\n" + PURPLE + "════════════════ SALES REPORT ════════════════" + RESET);
        System.out.printf("%-15s %-20s %-10s %-10s\n", "Order ID", "Customer", "Items", "Total");
        System.out.println(PURPLE + "────────────────────────────────────────────────" + RESET);
        
        for (Order order : orders) {
            int itemsInOrder = order.getOrderItems().stream().mapToInt(OrderItem::getQuantity).sum();
            double orderTotal = order.getTotal();
            
            System.out.printf("%-15s %-20s %-10d $%-9.2f\n", 
                order.getId(), 
                order.getCustomerName(), 
                itemsInOrder, 
                orderTotal);
            
            totalRevenue += orderTotal;
            totalItemsSold += itemsInOrder;
        }
        
        System.out.println(PURPLE + "────────────────────────────────────────────────" + RESET);
        System.out.printf("%-15s %-20s %-10d $%-9.2f\n", 
            "TOTAL", "", totalItemsSold, totalRevenue);
        System.out.println(PURPLE + "════════════════════════════════════════════════" + RESET);
    }

    private static void customerView() throws InterruptedException {
        while (true) {
            printHeader(" CUSTOMER VIEW ");
            System.out.println("1. View Menu");
            System.out.println("2. Search Item");
            System.out.println("3. Create Order");
            System.out.println("4. Logout");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Menu.displayAllItems(); // Uses Menu class to display items
                    break;
                case 2:
                    searchItem(); // Uses Menu class to search items
                    break;
                case 3:
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();
                    createOrder(customerName); // Uses Menu class for order creation
                    break;
                case 4:
                    return;
                default:
                    System.out.println(RED + "\n❌ Invalid option. Please try again." + RESET);
            }
        }
    }

    private static void createOrder(String customerName) throws InterruptedException {
        Order order = new Order(customerName);
        
        while (true) {
            printHeader(" ORDER MENU - " + customerName.toUpperCase());
            System.out.println("1. Add Items to Order");
            System.out.println("2. Remove Items from Order");
            System.out.println("3. View Current Order");
            System.out.println("4. Checkout");
            System.out.println("5. Cancel Order");
            System.out.print(CYAN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addItemsToOrder(order); // Uses Menu class to add items to order

                    break;
                case 2:
                    removeItemsFromOrder(order); //Uses Menu class to remove items to order
                    break;
                case 3:
                    order.printOrder();
                    break;
                case 4:
                    if (checkoutOrder(order)) return; //Uses Menu class to reduce stock
                    break;
                case 5:
                    System.out.println(RED + "❌ Order cancelled." + RESET);
                    return;
                default:
                    System.out.println(RED + "❌ Invalid option. Please try again." + RESET);
            }
        }
    }

    private static void addItemsToOrder(Order order) {
        List<OrderItem> itemsToAdd = new ArrayList<>();
        
        while (true) {
            Menu.displayAllItems(); // Uses Menu class to display items
            System.out.println(YELLOW + "\nCurrent items in batch: " + itemsToAdd.size() + RESET);
            System.out.print("Enter Item ID to add (or 'done' to finish): ");
            String itemId = scanner.nextLine();
            
            if (itemId.equalsIgnoreCase("done")) {
                break;
            }

            Menu item = Menu.searchItemById(itemId);
            if (item == null) {
                System.out.println(RED + "❌ Item not found." + RESET);
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            
            if (quantity <= 0) {
                System.out.println(RED + "❌ Quantity must be positive." + RESET);
                continue;
            }

            if (item.getQuantity() < quantity) {
                System.out.println(RED + "❌ Not enough stock. Available: " + item.getQuantity() + RESET);
                continue;
            }

            itemsToAdd.add(new OrderItem(item, quantity));
            System.out.println(GREEN + "✔ Added " + quantity + " " + item.getName() + "(s) to batch!" + RESET);
        }
        
        if (!itemsToAdd.isEmpty()) {
            order.addItems(itemsToAdd);
            System.out.println(GREEN + "\n✅ Successfully added " + itemsToAdd.size() + " items to order!" + RESET);
        } else {
            System.out.println(YELLOW + "\nℹ No items were added." + RESET);
        }
    }

    private static void removeItemsFromOrder(Order order) {
        if (order.getOrderItems().isEmpty()) {
            System.out.println(RED + "❌ No items in order to remove." + RESET);
            return;
        }
        
        List<String> itemsToRemove = new ArrayList<>();
        
        while (true) {
            order.printOrder();
            System.out.println(YELLOW + "\nCurrent items to remove: " + itemsToRemove.size() + RESET);
            System.out.print("Enter Item ID to remove (or 'done' to finish): ");
            String itemId = scanner.nextLine();
            
            if (itemId.equalsIgnoreCase("done")) {
                break;
            }

            boolean itemExists = order.getOrderItems().stream()
                .anyMatch(item -> item.getItem().getItemId().equals(itemId));
                
            if (!itemExists) {
                System.out.println(RED + "❌ Item not found in order." + RESET);
                continue;
            }

            itemsToRemove.add(itemId);
            System.out.println(GREEN + "✔ Added " + itemId + " to removal list!" + RESET);
        }
        
        if (!itemsToRemove.isEmpty()) {
            order.removeItems(itemsToRemove);
            System.out.println(GREEN + "\n✅ Successfully removed " + itemsToRemove.size() + " items from order!" + RESET);
        } else {
            System.out.println(YELLOW + "\nℹ No items were removed." + RESET);
        }
    }

    private static boolean checkoutOrder(Order order) {
        if (order.getOrderItems().isEmpty()) {
            System.out.println(RED + "❌ Cannot checkout empty order." + RESET);
            return false;
        }
        
        double totalAmount = order.getTotal();
        System.out.println(GREEN + "\nTotal Amount: $" + totalAmount + RESET);
        
        System.out.println("\nSelect Payment Method: ");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.print(CYAN + "Choose an option: " + RESET);
        
        int paymentChoice = scanner.nextInt();
        scanner.nextLine();
        
        Payment payment;
        switch (paymentChoice) {
            case 1:
                payment = new CashPayment();
                break;
            case 2:
                payment = new CardPayment();
                break;
            default:
                System.out.println(RED + "❌ Invalid payment method." + RESET);
                return false;
        }
        
        for (OrderItem item : order.getOrderItems()) {
            if (!Menu.reduceStock(item.getItem().getItemId(), item.getQuantity())) {
                System.out.println(RED + "❌ Error processing order. Not enough stock for: " + 
                    item.getItem().getName() + RESET);
                return false;
            }
        }
        
        if (payment.processPayment(totalAmount)) {
            order.updateStatus("Completed");
            orders.add(order);
            System.out.println(GREEN + "\n✅ Order placed successfully!" + RESET);
            order.printOrder();
            return true;
        }
        
        System.out.println(RED + "❌ Payment failed." + RESET);
        return false;
    }

    private static void viewAllOrders() {
        if (orders.isEmpty()) {
            System.out.println(YELLOW + "\nℹ No orders have been placed yet." + RESET);
            return;
        }
        
        System.out.println("\n" + BLUE + "════════════════ ALL ORDERS ════════════════" + RESET);
        for (Order order : orders) {
            order.printOrder();
            System.out.println(BLUE + "────────────────────────────────────────────" + RESET);
        }
    }

    private static void addMenuItem() {
        System.out.println("\n" + PURPLE + "════════════ CURRENT MENU ITEMS ════════════" + RESET);
        Menu.displayAllItems();
        
        System.out.println("\n➕ ADD NEW MENU ITEM");
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
        
        System.out.println("\n" + PURPLE + "════════════ UPDATED MENU ITEMS ════════════" + RESET);
        Menu.displayAllItems();  // Uses Menu class to display updated items
    }

    private static void removeMenuItem() {
        System.out.println("\n" + PURPLE + "════════════ CURRENT MENU ITEMS ════════════" + RESET);
        Menu.displayAllItems(); //Uses Menu class to display items
        
        System.out.println("\n❌ REMOVE MENU ITEM");
        System.out.print("Enter Item ID to Remove: ");
        String removeId = scanner.nextLine();
        // Using Menu class to remove item
        Menu.removeMenuItem(removeId);
        
        System.out.println("\n" + PURPLE + "════════════ UPDATED MENU ITEMS ════════════" + RESET);
        Menu.displayAllItems(); // Uses Menu class to display updated items
    }

    private static void updateStock() {
        System.out.println("\n" + PURPLE + "════════════ CURRENT MENU ITEMS ════════════" + RESET);
        Menu.displayAllItems(); // Uses Menu class to display items
        
        System.out.println("\n🔄 UPDATE STOCK");
        System.out.print("Enter Item ID to Update: ");
        String updateId = scanner.nextLine();
        // Using Menu class to search for item
        Menu item = Menu.searchItemById(updateId);
        if (item != null) {
            System.out.println("\nCurrent stock for " + item.getName() + ": " + item.getQuantity());
            System.out.print("Enter New Quantity: ");
            int newQuantity = scanner.nextInt();
            scanner.nextLine();
            // Using Menu class to update quantity
            item.updateQuantity(newQuantity);
            System.out.println(GREEN + "✅ Stock updated successfully!" + RESET);
            
            System.out.println("\n" + PURPLE + "════════════ UPDATED ITEM ════════════" + RESET);
            Menu.printItemHeader();
            item.displayMenuItem();
            Menu.printItemFooter();
        } else {
            System.out.println(RED + "❌ Item not found." + RESET);
        }
    }

    private static void searchItem() {
        System.out.print("🔎 Enter Item ID to Search: ");
        String searchId = scanner.nextLine();
        // Using Menu class to search for item
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

    private static boolean handleLogin(Login user, String userType) throws InterruptedException {
        System.out.print("\n🔑 Enter username: ");
        String username = scanner.nextLine();
        System.out.print("🔒 Enter password: ");
        String password = scanner.nextLine();

        System.out.print("🔄 Logging in");
        loadingEffect();
        // Using Login class to authenticate user
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

    private static void exitSystem() {
        System.out.print(GREEN + "\n🚪 Exiting");
        try {
            loadingEffect();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("\n👋 Goodbye!" + RESET);
        scanner.close();
        System.exit(0);
    }

    private static void loadingEffect() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            Thread.sleep(500);
        }
    }

    private static void printHeader(String title) {
        String border = "=".repeat(40);
        System.out.println("\n" + border);
        System.out.println("   " + title);
        System.out.println(border);
    }
}

interface Payment {
    boolean processPayment(double amount);
}


class Login {
    private String username;
    private String password;
    private boolean isAdmin;

    public Login(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public boolean authenticate(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

class Menu {
    private String itemId;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private static List<Menu> menuItems = new ArrayList<>();

    public Menu(String itemId, String name, double price, int quantity, String category) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public static void addMenuItem(Menu item) {
        menuItems.add(item);
    }

    public static void removeMenuItem(String itemId) {
        Menu item = searchItemById(itemId);
        if (item != null) {
            menuItems.remove(item);
            System.out.println("\u001B[32m" + "✅ Item removed successfully!" + "\u001B[0m");
        } else {
            System.out.println("\u001B[31m" + "❌ Item not found." + "\u001B[0m");
        }
    }

    public static Menu searchItemById(String itemId) {
        for (Menu item : menuItems) {
            if (item.getItemId().equalsIgnoreCase(itemId)) {
                return item;
            }
        }
        return null;
    }

    public static boolean reduceStock(String itemId, int quantity) {
        Menu item = searchItemById(itemId);
        if (item != null && item.getQuantity() >= quantity) {
            item.updateQuantity(item.getQuantity() - quantity);
            return true;
        }
        return false;
    }
    
    public static void displayAllItems() {
        if (menuItems.isEmpty()) {
            System.out.println("\u001B[33m" + "ℹ No items available in the menu." + "\u001B[0m");
            return;
        }

        String currentCategory = "";
        for (Menu item : menuItems) {
            if (!item.getCategory().equals(currentCategory)) {
                currentCategory = item.getCategory();
                printCategoryBox(currentCategory);
                printItemHeader();
            }
            item.displayMenuItem();
        }
        printItemFooter();
    }

    public static void printCategoryBox(String category) {
        String upperCategory = category.toUpperCase();
        int boxWidth = 34;
        int padding = (boxWidth - 2 - upperCategory.length()) / 2;
        
        System.out.println("\n╔" + "═".repeat(boxWidth - 2) + "╗");
        System.out.println("║" + " ".repeat(padding) + upperCategory + " ".repeat(padding) + "║");
        System.out.println("╚" + "═".repeat(boxWidth - 2) + "╝");
    }
    
    public static void printItemHeader() {
        System.out.println("┌──────────┬──────────────────────┬───────────┬──────────┐");
        System.out.printf("│ %-8s │ %-20s │ %-9s │ %-8s │%n", 
            "ID", "Name", "Price", "Quantity");
        System.out.println("├──────────┼──────────────────────┼───────────┼──────────┤");
    }
    
    public static void printItemFooter() {
        System.out.println("└──────────┴──────────────────────┴───────────┴──────────┘");
    }
    
    public void displayMenuItem() {
        System.out.printf("│ %-8s │ %-20s │ $%-8.2f │ %-8d │%n",
            itemId, name, price, quantity);
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getCategory() { return category; }
}