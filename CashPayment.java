public class CashPayment implements Payment {
    //Since CashPayment implements the Payment interface, it must include the method processPayment(double amount).
    //The @Override annotation ensures this method is correctly overriding the definition from the interface."
    @Override
    public boolean processPayment(double amount)
    {
    
        System.out.println("Cash payment of $" + amount + " received.");
        return true;
    }
}