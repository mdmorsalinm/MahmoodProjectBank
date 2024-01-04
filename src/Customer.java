public class Customer {
    private String name;
    private int pin;

    public Customer(String name, int pin) {
        this.name = name;
        this.pin = pin;
    }

    public void setPin(int num) {
        pin = num;
    }

    public int getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }
}
