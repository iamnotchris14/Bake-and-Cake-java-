// /******************************************************************************

// Welcome to GDB Online.
// GDB online is an online compiler and debugger tool for C, C++, Python, Java, PHP, Ruby, Perl,
// C#, OCaml, VB, Swift, Pascal, Fortran, Haskell, Objective-C, Assembly, HTML, CSS, JS, SQLite, Prolog.
// Code, Compile, Run and Debug online from anywhere in world.

// *******************************************************************************/
// public class Main
// {
// 	public static void main(String[] args) {
// 		System.out.println("Hello World");
// 	}
// }
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BakerySystem {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static Scanner scanner = new Scanner(System.in);
    private static List<Order> orders = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        initializeSampleData();
        loginSystem();
    }

    private static void initializeSampleData() {
        // Initialize sample menu items
        Menu.addMenuItem(new Menu("A01", "Chocolate Muffin", 5.99, 10, "Muffins"));
        Menu.addMenuItem(new Menu("A02", "Vanilla Muffin", 5.99, 10, "Muffins"));
        Menu.addMenuItem(new Menu("B01", "Hokkaido Cheesecake", 9.99, 8, "Cakes"));
        Menu.addMenuItem(new Menu("B02", "Chocolate Indulgence", 7.49, 8, "Cakes"));
        Menu.addMenuItem(new Menu("C01", "Sourdough Bread", 5.99, 15, "Bread"));
        Menu.addMenuItem(new Menu("C02", "Sausage Bun", 5.99, 15, "Bread"));
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
            scanner.nextLine();

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
            System.out.println("1. Add Menu Item");
            System.out.println("2. Remove Menu Item");
            System.out.println("3. Update Stock");
            System.out.println("4. View Menu");
            System.out.println("5. View All Orders");
            System.out.println("6. Logout");
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
                    viewAllOrders();
                    break;
                case 6:
                    return;
                default:
                    System.out.println(RED + "\n❌ Invalid option. Please try again." + RESET);
            }
        }
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
                    Menu.displayAllItems();
                    break;
                case 2:
                    searchItem();
                    break;
                case 3:
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();
                    createOrder(customerName);
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
                    addItemsToOrder(order);
                    break;
                case 2:
                    removeItemsFromOrder(order);
                    break;
                case 3:
                    order.printOrder();
                    break;
                case 4:
                    if (checkoutOrder(order)) return;
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
            Menu.displayAllItems();
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
        
        //***Inserted new payment system ***
        // to include a payment promtpt
        
        double totalAmount = order.calculateTotal();
        System.out.println(GREEN + "\nTotal Amount: RM" + totalAmount + RESET);
        
        //Choose payment method
        System.out.println("\nSelect Payment Method: ");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println(CYAN + "Choose an option: " + RESET);
        
        int paymentChoice = scanner.nextInt();
        //scanner.nextLine();
        
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
        
        
        // Verify stock and process order
        for (OrderItem item : order.getOrderItems()) {
            if (!Menu.reduceStock(item.getItem().getItemId(), item.getQuantity())) {
                System.out.println(RED + "❌ Error processing order. Not enough stock for: " + 
                    item.getItem().getName() + RESET);
                return false;
            }
        }
        
        // Payment method 
        if (payment.processPayment(totalAmount))
        {
            order.updateStatus("Completed");
            orders.add(order);
            System.out.println(GREEN + "\n✅ Order placed successfully!" + RESET);
            order.printOrder();
            return true;
        }
        
        System.out.println(RED + "Payment failed." + RESET);
        return false;
    }

    private static void viewAllOrders() {
        if (orders.isEmpty()) {
            System.out.println(YELLOW + "\nℹ No orders have been placed yet." + RESET);
            return;
        }
        
        System.out.println("\n════════════════ ALL ORDERS ════════════════");
        for (Order order : orders) {
            order.printOrder();
        }
    }

    private static void addMenuItem() {
        // Display current menu items first
        System.out.println("\n════════════ CURRENT MENU ITEMS ════════════");
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
        
        // Display updated menu
        System.out.println("\n════════════ UPDATED MENU ITEMS ════════════");
        Menu.displayAllItems();
    }

    private static void removeMenuItem() {
        // Display current menu items first
        System.out.println("\n════════════ CURRENT MENU ITEMS ════════════");
        Menu.displayAllItems();
        
        System.out.println("\n❌ REMOVE MENU ITEM");
        System.out.print("Enter Item ID to Remove: ");
        String removeId = scanner.nextLine();
        
        Menu.removeMenuItem(removeId);
        
        // Display updated menu
        System.out.println("\n════════════ UPDATED MENU ITEMS ════════════");
        Menu.displayAllItems();
    }

    private static void updateStock() {
        // Display current menu items first
        System.out.println("\n════════════ CURRENT MENU ITEMS ════════════");
        Menu.displayAllItems();
        
        System.out.println("\n🔄 UPDATE STOCK");
        System.out.print("Enter Item ID to Update: ");
        String updateId = scanner.nextLine();
        Menu item = Menu.searchItemById(updateId);
        if (item != null) {
            System.out.println("\nCurrent stock for " + item.getName() + ": " + item.getQuantity());
            System.out.print("Enter New Quantity: ");
            int newQuantity = scanner.nextInt();
            scanner.nextLine();
            item.updateQuantity(newQuantity);
            System.out.println(GREEN + "✅ Stock updated successfully!" + RESET);
            
            // Display updated item
            System.out.println("\n════════════ UPDATED ITEM ════════════");
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
        String border = "=================================";
        System.out.println("\n" + border);
        System.out.println("   " + title);
        System.out.println(border);
    }
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
        // Calculate the required box width based on the longest line (34 chars)
        int boxWidth = 34;
        String upperCategory = category.toUpperCase();
        
        // Center the category name with proper padding
        int totalPadding = boxWidth - 2 - upperCategory.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        
        System.out.println("\n╔" + "═".repeat(boxWidth - 2) + "╗");
        System.out.println("║" + " ".repeat(leftPadding) + upperCategory + " ".repeat(rightPadding) + "║");
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
    
    // In your Menu class's displayMenuItem() method:
    public void displayMenuItem() {
        System.out.printf("│ %-8s │ %-20s │ $%-8.2f │ %-8d │%n",
            itemId, name, price, quantity);
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    // Getters
    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getCategory() { return category; }
}

class Order {
    private String orderId;
    private String customerName;
    private String status;
    private List<OrderItem> orderItems;
    private static int orderCounter = 1;

    public Order(String customerName) {
        this.orderId = "ORD" + String.format("%03d", orderCounter++);
        this.customerName = customerName;
        this.status = "Pending";
        this.orderItems = new ArrayList<>();
    }

    public void addItems(List<OrderItem> items) {
        orderItems.addAll(items);
    }

    public void removeItems(List<String> itemIds) {
        orderItems.removeIf(item -> itemIds.contains(item.getItem().getItemId()));
    }

    public void updateStatus(String status) {
        this.status = status;
    }
    
    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : orderItems)
        {
            total += item.getQuantity() * item.getItem().getPrice();
        }
        return total;
    }

    public void printOrder() {
        System.out.println("\n════════════════ ORDER #" + orderId + " ════════════════");
        System.out.println("Customer: " + customerName);
        System.out.println("Status: " + status);
        System.out.println("\nOrder Items:");
        
        if (orderItems.isEmpty()) {
            System.out.println("\u001B[33m" + "ℹ No items in this order." + "\u001B[0m");
            return;
        }

        System.out.println("┌──────────┬──────────────────────┬──────────┬──────────┬──────────┐");
        System.out.println("│   ID     │        Name          │  Price   │ Quantity │ Subtotal │");
        System.out.println("├──────────┼──────────────────────┼──────────┼──────────┼──────────┤");
        
        double total = 0;
        for (OrderItem item : orderItems) {
            double subtotal = item.getItem().getPrice() * item.getQuantity();
            System.out.printf("│ %-8s │ %-20s │ $%-7.2f │ %-8d │ $%-7.2f │\n", 
                item.getItem().getItemId(), 
                item.getItem().getName(), 
                item.getItem().getPrice(), 
                item.getQuantity(), 
                subtotal);
            total += subtotal;
        }
        
        System.out.println("└──────────┴──────────────────────┴──────────┴──────────┴──────────┘");
        System.out.printf("\nTotal: $%.2f\n", total);
    }

    // Getters
    public List<OrderItem> getOrderItems() { return orderItems; }
}

class OrderItem {
    private Menu item;
    private int quantity;

    public OrderItem(Menu item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    // Getters
    public Menu getItem() { return item; }
    public int getQuantity() { return quantity; }
}

