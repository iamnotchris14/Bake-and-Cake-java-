class CardPayment implements Payment {
    @Override
    public boolean processPayment(double amount)
    {
        System.out.println("Card payment of RM" + amount + " received.");
        return true;
    }
}

