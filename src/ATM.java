import java.util.Scanner;
public class ATM {
    private Account saving;
    private Account checking;
    private Customer customer;
    private Scanner scan;
    private TransactionHistory transaction;

    public ATM() {
        transaction = new TransactionHistory();
        scan = new Scanner(System.in);
    }

    public void start() {
        logic();
    }

    private void logic() {
        System.out.println("Welcome to the ATM");
        System.out.println("To get started you need to create an account!");
        System.out.print("Enter your username: ");
        String name = scan.nextLine();
        System.out.print("Enter your 4 digit pin number: ");
        int pin = scan.nextInt();
        scan.nextLine();
        while (pin < 1000 || pin > 9999) {
            System.out.println("That is not 4 digit");
            System.out.print("Enter your 4 digit pin number again: ");
            pin = scan.nextInt();
        }
        customer = new Customer(name, pin);
        saving = new Account("saving", 100, customer, false);
        checking = new Account("checking", 100, customer, true);
        System.out.println("Account Successfully Created!");

        System.out.println("Username: " + name);
        System.out.print("Enter your 4 digit pin to login: ");
        int newPin = scan.nextInt();
        while (newPin != pin) {
            System.out.print("Incorrect pin try again: ");
            newPin = scan.nextInt();
        }
        int choice = 10000;
        while (choice != 7) {
            printMenu();
            System.out.print("Enter your choice (1 - 7): ");
            choice = scan.nextInt();
            scan.nextLine();
            if (choice == 1) {
                Account currentAcc = accountType();
                System.out.print("Enter your withdrawal amount(theres only $5 and $20 bills): ");
                int amount = scan.nextInt();
                while (amount % 5 != 0 || amount > currentAcc.getBalance()) {
                    if (amount % 5 != 0) {
                        System.out.println("We only have $5 and $20 bills sorry");
                    }
                    if (amount > currentAcc.getBalance()) {
                        System.out.println("Insufficient Funds!");
                    }
                    System.out.print("Enter your withdrawal amount: ");
                    amount = scan.nextInt();
                }
                int change;
                int total = 0;
                int num5 = 0;
                int num20 = 0;
                while (total != amount) {
                    System.out.println("$5 bills: " + num5);
                    System.out.println("$20 bills: " + num20);
                    System.out.println("Total: " + total + " Target: " + amount);
                    System.out.print("Pick How Many $5 (input 5) and $20 (input 20): ");
                    change = scan.nextInt();
                    if ((change == 5) && (total + change) < amount) {
                        total += 5;
                        num5++;
                    } else if ((change == 20) && (total + change) < amount) {
                        total += 20;
                        num20++;
                    }
                }
                currentAcc.removeMoney(amount);
                transaction.withdrawHistory(amount, currentAcc.isChecking());
                System.out.println("Successfully withdrew $" + amount);
            } else if (choice == 2) {
                Account currentAcc = accountType();
                System.out.print("Enter your deposit amount: ");
                double amount = scan.nextDouble();
                currentAcc.addMoney(amount);
                transaction.depositHistory(amount, currentAcc.isChecking());
                System.out.println("Successfully deposited $" + amount);
            } else if (choice == 3) {
                System.out.print("From account: (s)avings or (c)hecking: ");
                String from = scan.nextLine();
                while (!from.equals("s") && !from.equals("c")) {
                    System.out.print("Pick an account: (s)avings, (c)hecking again: ");
                    from = scan.nextLine();
                }
                System.out.print("From account: (s)avings or (c)hecking: ");
                String to = scan.nextLine();
                while (!to.equals("s") && !to.equals("c")) {
                    System.out.print("Pick an account: (s)avings, (c)hecking again: ");
                    to = scan.nextLine();
                }
                System.out.println("Enter transfer amount: ");
                double amount = scan.nextDouble();
                if (from.equals("c") && to.equals("s")) {
                    if (checking.getBalance() >= amount) {
                        checking.removeMoney(amount);
                        saving.addMoney(amount);
                        transaction.transferHistory(amount, true);
                        System.out.println("Transferred $" + amount + " from checking to savings");
                    } else {
                        System.out.println("Insufficient funds in checking");
                    }
                } else if (from.equals("s") && to.equals("c")) {
                    if (saving.getBalance() >= amount) {
                        saving.removeMoney(amount);
                        checking.addMoney(amount);
                        transaction.transferHistory(amount, false);
                        System.out.println("Transferred $" + amount + " from savings to checking");
                    } else {
                        System.out.println("Insufficient funds in saving");
                    }
                } else {
                    System.out.println("Invalid Transfer! You are transferring money to same account");
                }
            }

        }

    }

    private void printMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Withdraw Money");
        System.out.println("2. Deposit money");
        System.out.println("3. Transfer money between accounts");
        System.out.println("4. Get account balances");
        System.out.println("5. Get transaction history");
        System.out.println("6. Change PIN");
        System.out.println("7. Exit");
    }

    private Account accountType() {
        Account currentAcc = null;
        System.out.print("Pick an account: (s)avings, (c)hecking: ");
        String option = scan.nextLine();
        while (!option.equals("s") && !option.equals("c")) {
            System.out.print("Pick an account: (s)avings, (c)hecking again: ");
            option = scan.nextLine();
        }
        if (option.equals("s")) {
            currentAcc = saving;
        } else if (option.equals("c")) {
            currentAcc = checking;
        }
        return currentAcc;
    }
}
