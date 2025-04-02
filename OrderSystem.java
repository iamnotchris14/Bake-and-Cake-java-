import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

class Order
{
    private String id;
    private Customer customer;
    private String status;
    private List<BakeryItem> bakeryItems; 
    private double total;

    public Order(String id, Customer customer) 
    {
        this.id = id;
        this.customer = customer;
        this.status = "Pending";
        this.bakeryItems = new ArrayList<>();
        this.total = 0.0;
    }

    public void addItem(BakeryItem item, int quantity) 
    {
        item.setQuantityInOrder(item.getQuantityInOrder() + quantity); // Need this method in BakeryItem
        bakeryItems.add(item);
        total += item.getPrice() * quantity;
    }
    
    public void removeItem(String itemName) 
    {
        for (BakeryItem item : bakeryItems) 
        {
            if (item.getName().equals(itemName)) 
            {
                total -= item.getPrice() * item.getQuantityInOrder();
                bakeryItems.remove(item);
                break;
            }
        }
    }


    public void updateStatus(String newStatus)
    {
        this.status = newStatus;
    }

    public double getTotal() 
    {
        return total;
    }

    public void printOrder() 
    {
        System.out.println("Order ID: " + id);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Status: " + status);
        System.out.println("Items:");
        
        for (BakeryItem item : bakeryItems)
        {
            System.out.printf("- %s x%d @ $%.2f = $%.2f%n",
                item.getName(),
                item.getQuantityInOrder(),
                item.getPrice(),
                item.getPrice() * item.getQuantityInOrder());
        }
        
        System.out.printf("Total: $%.2f%n", total);
    }


    public String getId()
    { 
        return id;
    }
    public Customer getCustomer() 
    { 
        return customer; 
    }
    public String getStatus() 
    { 
        return status;
    }
    public List<BakeryItem> getBakeryItems() 
    { 
        return bakeryItems; 
    }
}

public class OrderSystem 
{
    private static HashMap<String, Integer> cart = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) 
    {
        while (true)
        {
            System.out.println("\n=== Bakery Ordering System ===");
            System.out.println("1. Add items to cart");
            System.out.println("2. View cart");
            System.out.println("3. Remove item from cart");
            System.out.println("4. Exit");
            System.out.println("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice)
            {
                case 1:
                    addItem();
                    break;

                case 2:
                    viewCart();
                    break;

                case 3:
                    removeItem();
                    break;

                case 4: 
                    System.out.println("Thank you for ordering! Please proceed to payment.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again. ");
            }
        }
    }

    public static void addItem()
    {
        System.out.println("Enter item id: ");
        String item = scanner.nextInt(); //reads the item the customer wants to order

        System.out.println("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        cart.put(item, cart.getOrDefault(item, 0) + quantity);
        System.out.println(quantity + " " + item + "(s) added to cart")
    }
}