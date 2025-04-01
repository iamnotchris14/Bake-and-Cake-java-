import java.util.ArrayList;

public class Payment 
{
    private String paymentId;
    // private Order order;
    private String paymentMethod;
    private double amount;
    private String maskedAccountNumber;
    private String lastFourDigits;
    private static ArrayList<Payment> payments = new ArrayList<>();

    // public Payment(Order order)
    // {
    //     this.order = order;
    // }
    
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

    public void processPayment() 
    {
        
    }
}