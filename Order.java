import java.util.ArrayList;
import java.util.List;

// Represents a customer's order
public class Order {
    private String id; // Unique order ID like "ORD-0001"
    private String customerName; // Name of the customer
    private String status; // Order status: "Pending" or "Completed"
    private List<OrderItem> orderItems; // List of items in the order
    private double total; // Total cost of the order
    private static int orderCounter = 1; // Static counter to auto-increment order IDs

    // Constructor to create a new order
    public Order(String customerName) {
        this.id = "ORD-" + String.format("%04d", orderCounter++); // Format ID like ORD-0001
        this.customerName = customerName; // Assign customer name
        this.status = "Pending"; // Default status when order is created
        this.orderItems = new ArrayList<>(); // Initialize empty list of items
        this.total = 0.0; // Initial total is zero
    }

    // Method to add multiple items to the order
    public void addItems(List<OrderItem> itemsToAdd) {
        for (OrderItem newItem : itemsToAdd) { // Loop through items to be added
            boolean itemExists = false; // Flag to track if item already exists in order
            for (OrderItem existingItem : orderItems) { // Loop through existing items
                // Check if item already exists in the order by comparing item IDs
                if (existingItem.getItem().getItemId().equals(newItem.getItem().getItemId())) {
                    // If it exists, update the quantity
                    existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
                    itemExists = true; // Set flag
                    break; // Stop checking once found
                }
            }
            if (!itemExists) {
                orderItems.add(newItem); // If item not found, add it to the order
            }
            // Update the total cost of the order
            total += newItem.getItem().getPrice() * newItem.getQuantity();
        }
    }

    // Method to remove items from the order by item ID
    public void removeItems(List<String> itemIdsToRemove) {
        List<OrderItem> itemsToRemove = new ArrayList<>(); // Temporary list to store items to be removed
        for (String itemId : itemIdsToRemove) { // Loop through item IDs to remove
            for (OrderItem item : orderItems) { // Loop through current items
                if (item.getItem().getItemId().equals(itemId)) { // If ID matches
                    // Deduct the item's total price from order total
                    total -= item.getItem().getPrice() * item.getQuantity();
                    itemsToRemove.add(item); // Mark item for removal
                    break; // Stop checking once found
                }
            }
        }
        orderItems.removeAll(itemsToRemove); // Remove all marked items at once
    }

    // Method to update the order's status
    public void updateStatus(String newStatus) {
        this.status = newStatus; // Set new status
    }

    // Method to print all order details in formatted output
    public void printOrder() {
        System.out.println("\n════════════ ORDER DETAILS ════════════");
        System.out.println(" Order ID: " + id); // Print order ID
        System.out.println(" Customer: " + customerName); // Print customer name
        System.out.println(" Status: " + status); // Print status
        System.out.println(" Items:"); // Print header for items

        for (OrderItem item : orderItems) { // Loop through each item
            // Print item details: ID, name, quantity, price, and subtotal
            System.out.printf(" - %-5s %-25s x%-3d $%-6.2f $%-7.2f%n",
                item.getItem().getItemId(), // Item ID
                item.getItem().getName(), // Item name
                item.getQuantity(), // Quantity
                item.getItem().getPrice(), // Price per item
                item.getItem().getPrice() * item.getQuantity()); // Subtotal for that item
        }

        System.out.println("───────────────────────────────────────");
        System.out.printf(" Total: $%.2f%n", total); // Print total
        System.out.println("═══════════════════════════════════════\n");
    }

    // Getters to access private fields
    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getStatus() { return status; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public double getTotal() { return total; }
}

// Class representing a single item in an order
class OrderItem {
    private Menu item; // Menu item object
    private int quantity; // Quantity of that item ordered

    // Constructor to create an order item
    public OrderItem(Menu item, int quantity) {
        this.item = item; // Set menu item
        this.quantity = quantity; // Set quantity
    }

    // Getter for the menu item
    public Menu getItem() { return item; }

    // Getter for quantity
    public int getQuantity() { return quantity; }

    // Setter to update quantity
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
