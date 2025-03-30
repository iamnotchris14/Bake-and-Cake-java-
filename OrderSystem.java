import java.util.ArrayList;
import java.util.List;

public class OrderSystem 
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
