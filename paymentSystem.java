import java.util.*;

class Payment 
{
    private String paymentId, paymentMethod, maskedAccountNumber, lastFourDigits;
    private Order order;
    // private String paymentMethod;
    private double amount;
    // private String maskedAccountNumber;
    // private String lastFourDigits;
    private static ArrayList<Payment> payments = new ArrayList<>();

    public Payment(Order order)
    {
        this.order = order;
    }
    
    public String getPaymentId()
    {
        return paymentId;
    }
    
    public void setPaymentId(String paymentId) 
    {
        this.paymentId = paymentId;
    }
    
    public String getPaymentMethod()
    {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) 
    {
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() 
    {
        return amount;
    }

    public void setCardDetails(String accountNumber) 
    {
        maskedAccountNumber = accountNumber;
        if (accountNumber != null && accountNumber.length() >= 4) 
        {
            lastFourDigits = accountNumber.substring(accountNumber.length() - 4);
        }
    }

    public void printReceipt()

    
}

public interface paymentMethod
{
    void processPayment(double amount);
}

// paymentSystem class for UI Interaction 
public class paymentSystem 
{
    private Order order;
    private Scanner scanner;
    
    public Payment(Order order)
    {
        this.order = order;
        this.scanner = new Scanner(System.in);
    }

    public void processPayment()
    {
        System.out.println("\nPayment Options:");
        System.out.println("1. Credit Card");
        System.out.println("2. Cash");
        System.out.print("Select payment method (1-2): ");
    }
    
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
            
        boolean paymentSuccess = false;
        
        while (!paymentSuccess) {
            switch (choice) {
                case 1:
                    paymentSuccess = processCreditCardPayment();
                    break;
                case 2:
                    paymentSuccess = processCashPayment();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }
            
            if (!paymentSuccess) {
                System.out.println("Payment failed. Please try another method.");
                System.out.println("1. Credit Card");
                System.out.println("2. Cash");
                System.out.print("Select payment method (1-2): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            }
        }
        
        System.out.println("Payment successful! Thank you for your purchase.");
        }

        private boolean processCreditCardPayment() {
            System.out.println("\nCredit Card Payment");
            System.out.print("Enter card number (16 digits): ");
            String cardNumber = scanner.nextLine();
            
            System.out.print("Enter CVV (3 digits): ");
            String cvv = scanner.nextLine();
            
            // Simple validation
            if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
                System.out.println("Invalid card number. Must be 16 digits.");
                return false;
            }
            
            if (cvv.length() != 3 || !cvv.matches("\\d+")) {
                System.out.println("Invalid CVV. Must be 3 digits.");
                return false;
            }
        }

        private boolean processCashPayment()
        {
            System.out.println("\nCash Payment");
            System.out.printf("Please pay at the counter.");
            System.out.print("Confirm payment received (Y/N): ");
            String confirmation = scanner.nextLine().trim().toUpperCase();
            
            return confirmation.equals("Y");
        }
    }
        
    
}
