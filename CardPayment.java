import java.util.Scanner;

public class CardPayment implements Payment {
    private String cardNumber;
    private String cvv;
    
    public boolean validateCardDetails(String cardNumber, String cvv) {
        if (!cardNumber.matches("\\d{16}")) {
            System.out.println("Invalid card number! Must be 16 digits.");
            return false;
        }
        
        if (!cvv.matches("\\d{3}")) {
            System.out.println("Invalid CVV! Must be 3 digits.");
            return false;
        }
        
        return true;
    }
    //Scanner cannot be closed because it will affect the usage in other classes. (Ignore Error)
    @Override
    public boolean processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter your 16-digit credit card number: ");
        this.cardNumber = scanner.nextLine();
        
        System.out.println("Enter your 3 digit CVV: ");
        this.cvv = scanner.nextLine();
        
        if (!validateCardDetails(this.cardNumber, this.cvv)) {
            System.out.println("Payment failed due to invalid card details.");
            return false;
        }
        
        System.out.println("Card details verified. Processing payment of $" + amount + "...");
        System.out.println("Card payment of $" + amount + " received.");
        return true;
    }
}