import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private String customerName;
    private String status;
    private List<OrderItem> orderItems;
    private double total;
    private static int orderCounter = 1;
    

    public Order(String customerName) {
        this.id = "ORD-" + String.format("%04d", orderCounter++);
        this.customerName = customerName;
        this.status = "Pending";
        this.orderItems = new ArrayList<>();
        this.total = 0.0;
    }

    public void addItems(List<OrderItem> itemsToAdd) {
        for (OrderItem newItem : itemsToAdd) {
            boolean itemExists = false;
            for (OrderItem existingItem : orderItems) {
                if (existingItem.getItem().getItemId().equals(newItem.getItem().getItemId())) {
                    existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
                    itemExists = true;
                    break;
                }
            }
            if (!itemExists) {
                orderItems.add(newItem);
            }
            total += newItem.getItem().getPrice() * newItem.getQuantity();
        }
    }

    public void removeItems(List<String> itemIdsToRemove) {
        List<OrderItem> itemsToRemove = new ArrayList<>();
        for (String itemId : itemIdsToRemove) {
            for (OrderItem item : orderItems) {
                if (item.getItem().getItemId().equals(itemId)) {
                    total -= item.getItem().getPrice() * item.getQuantity();
                    itemsToRemove.add(item);
                    break;
                }
            }
        }
        orderItems.removeAll(itemsToRemove);
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public void printOrder() {
        System.out.println("\n════════════ ORDER DETAILS ════════════");
        System.out.println(" Order ID: " + id);
        System.out.println(" Customer: " + customerName);
        System.out.println(" Status: " + status);
        System.out.println(" Items:");
        
        for (OrderItem item : orderItems) {
            System.out.printf(" - %-5s %-25s x%-3d $%-6.2f $%-7.2f%n",
                item.getItem().getItemId(),
                item.getItem().getName(),
                item.getQuantity(),
                item.getItem().getPrice(),
                item.getItem().getPrice() * item.getQuantity());
        }
        
        System.out.println("───────────────────────────────────────");
        System.out.printf(" Total: $%.2f%n", total);
        System.out.println("═══════════════════════════════════════\n");
    }

    // Getters
    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getStatus() { return status; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public double getTotal() { return total; }
}

class OrderItem {
    private Menu item;
    private int quantity;

    public OrderItem(Menu item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Menu getItem() { return item; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}