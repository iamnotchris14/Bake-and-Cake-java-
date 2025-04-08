public class CashPayment implements Payment {
    @Override
    public boolean processPayment(double amount)
    {
    
        System.out.println("Cash payment of RM" + amount + " received.");
        return true;
    }
}