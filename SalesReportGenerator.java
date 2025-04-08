import java.util.*;

public class SalesReportGenerator implements ReportGenerator 
{
    public void generateReport(List<Order> orders) 
    {
        System.out.println("\n======= SALES REPORT =======");

        if (orders.isEmpty()) {
            System.out.println("No sales data available.");
            return;
        }

        Map<String, Integer> itemSalesCount = new HashMap<>();
        Map<String, Double> itemSalesRevenue = new HashMap<>();
        double totalRevenue = 0;

        for (Order order : orders) {

            for (OrderItem item : order.getOrderItems()) 
            {
                String name = item.getItem().getName();
                int quantity = item.getQuantity();
                double subtotal = quantity * item.getItem().getPrice();

                itemSalesCount.put(name, itemSalesCount.getOrDefault(name, 0) + quantity);
                itemSalesRevenue.put(name, itemSalesRevenue.getOrDefault(name, 0.0) + subtotal);
                totalRevenue += subtotal;
            }
        }

        System.out.println("Total Orders: " + orders.size());
        System.out.printf("%-25s %-10s %-10s%n", "Item", "Qty Sold", "Revenue");

        for (String item : itemSalesCount.keySet()) 
        {
            int qty = itemSalesCount.get(item);
            double revenue = itemSalesRevenue.get(item);
            System.out.printf("%-25s %-10d $%-9.2f%n", item, qty, revenue);
        }

        System.out.printf("\nTotal Revenue: $%.2f%n", totalRevenue);
    }
}
