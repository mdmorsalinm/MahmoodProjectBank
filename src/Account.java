public class Account {
    private String accountName;
    private double balance;
    private boolean isChecking;
    private Customer customer;

    public Account(String accountName, double balance, Customer customer, boolean type) {
        this.accountName = accountName;
        this.balance = balance;
        this.customer = customer;
        isChecking = type;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public boolean isChecking() {
        return isChecking;
    }

    public void addMoney(double amount) {
        balance += amount;
    }

    public void removeMoney(double amount) {
        balance -= amount;
    }





}
