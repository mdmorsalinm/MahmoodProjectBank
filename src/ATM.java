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
        ConsoleUtility.clearScreen();
        System.out.println(ConsoleUtility.BLUE + "Welcome to the ATM" + ConsoleUtility.RESET);
        System.out.println("To get started you need to create an account!");
        System.out.print("Enter your username: ");
        String name = scan.nextLine();
        System.out.print("Enter your 4 digit pin number: ");
        int pin = scan.nextInt();
        scan.nextLine();
        ConsoleUtility.clearScreen();
        while (pin < 1000 || pin > 9999) {
            System.out.println("Username: " + name);
            System.out.println(ConsoleUtility.RED + "That is not 4 digit" + ConsoleUtility.RESET);
            System.out.print("Enter your 4 digit pin number again: ");
            pin = scan.nextInt();
            ConsoleUtility.clearScreen();
        }
        customer = new Customer(name, pin);
        saving = new Account("saving", 100, customer, false);
        checking = new Account("checking", 100, customer, true);
        System.out.println(ConsoleUtility.GREEN + "Account Successfully Created!" + ConsoleUtility.RESET);
        try {
            Thread.sleep(2000);  // 2000 milliseconds, or 2 seconds
        } catch (Exception e) {
            System.out.println("error");
        }
        ConsoleUtility.clearScreen();
        System.out.println("Username: " + name);
        System.out.print("Enter your 4 digit pin to login: ");
        int newPin = scan.nextInt();
        ConsoleUtility.clearScreen();
        while (newPin != customer.getPin()) {
            System.out.println("Username: " + name);
            System.out.print(ConsoleUtility.RED + "Incorrect pin try again: " + ConsoleUtility.RESET);
            newPin = scan.nextInt();
            ConsoleUtility.clearScreen();
        }
        scan.nextLine();
        int choice = 10000;
        int count = 0;
        String user = "";
        while (choice != 7 && !user.equals("no")) {
            if (count > 0) {
                System.out.print("Do you want to do anything else (yes/no): ");
                user = scan.nextLine();
                ConsoleUtility.clearScreen();
            }
            if (!user.equals("no")) {
                if (count > 0) {
                    System.out.print("Enter your 4 digit pin for security purposes: ");
                    newPin = scan.nextInt();
                    ConsoleUtility.clearScreen();
                    while (newPin != customer.getPin()) {
                        System.out.print(ConsoleUtility.RED + "Incorrect pin try again: " + ConsoleUtility.RESET);
                        newPin = scan.nextInt();
                        scan.nextLine();
                        ConsoleUtility.clearScreen();
                    }
                }
                printMenu();
                System.out.print("Enter your choice (1 - 7): ");
                choice = scan.nextInt();
                scan.nextLine();
                ConsoleUtility.clearScreen();
                if (choice == 1) {
                    Account currentAcc = accountType();
                    System.out.print("Enter your withdrawal amount(theres only $5 and $20 bills): ");
                    int amount = scan.nextInt();
                    scan.nextLine();

                    while (amount % 5 != 0 || amount > currentAcc.getBalance()) {
                        if (amount % 5 != 0) {
                            System.out.println(ConsoleUtility.RED + "We only have $5 and $20 bills sorry" + ConsoleUtility.RESET);
                        }
                        if (amount > currentAcc.getBalance()) {
                            System.out.println(ConsoleUtility.RED + "Insufficient Funds!" + ConsoleUtility.RESET);
                        }
                        System.out.print("Enter your withdrawal amount: ");
                        amount = scan.nextInt();
                        scan.nextLine();
                        ConsoleUtility.clearScreen();
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
                        scan.nextLine();
                        ConsoleUtility.clearScreen();
                        if ((change == 5) && ((total + change) <= amount)) {
                            total += 5;
                            num5++;
                        } else if ((change == 20) && ((total + change) <= amount)) {
                            total += 20;
                            num20++;
                        }
                        ConsoleUtility.clearScreen();
                    }
                    currentAcc.removeMoney(amount);
                    System.out.println(ConsoleUtility.YELLOW + transaction.withdrawHistory(amount, currentAcc.isChecking(), currentAcc.getBalance()) + ConsoleUtility.RESET);
                    try {
                        Thread.sleep(3000);  // 2000 milliseconds, or 2 seconds
                    } catch (Exception e) {
                        System.out.println("error");
                    }
                    ConsoleUtility.clearScreen();

                } else if (choice == 2) {
                    Account currentAcc = accountType();
                    System.out.print("Enter your deposit amount: ");
                    double amount = scan.nextDouble();
                    scan.nextLine();
                    currentAcc.addMoney(amount);
                    System.out.println(ConsoleUtility.YELLOW + transaction.depositHistory(amount, currentAcc.isChecking(), currentAcc.getBalance()) + ConsoleUtility.RESET);
                    try {
                        Thread.sleep(3000);  // 2000 milliseconds, or 2 seconds
                    } catch (Exception e) {
                        System.out.println("error");
                    }
                    ConsoleUtility.clearScreen();
                } else if (choice == 3) {
                    System.out.print("From account: (s)avings or (c)hecking: ");
                    String from = scan.nextLine();
                    ConsoleUtility.clearScreen();
                    while (!from.equals("s") && !from.equals("c")) {
                        System.out.print("Pick an account: (s)avings, (c)hecking again: ");
                        from = scan.nextLine();
                        ConsoleUtility.clearScreen();
                    }
                    System.out.print("To account: (s)avings or (c)hecking: ");
                    String to = scan.nextLine();
                    ConsoleUtility.clearScreen();
                    while (!to.equals("s") && !to.equals("c")) {
                        System.out.print("Pick an account: (s)avings, (c)hecking again: ");
                        to = scan.nextLine();
                        ConsoleUtility.clearScreen();
                    }
                    System.out.print("Enter transfer amount: ");
                    double amount = scan.nextDouble();
                    scan.nextLine();
                    if (from.equals("c") && to.equals("s")) {
                        if (checking.getBalance() >= amount) {
                            checking.removeMoney(amount);
                            saving.addMoney(amount);
                            System.out.println(ConsoleUtility.YELLOW + transaction.transferHistory(amount, true, saving.getBalance(), checking.getBalance()) + ConsoleUtility.RESET);
                        } else {
                            System.out.println(ConsoleUtility.RED + "Insufficient funds in checking" + ConsoleUtility.RESET);
                        }
                    } else if (from.equals("s") && to.equals("c")) {
                        if (saving.getBalance() >= amount) {
                            saving.removeMoney(amount);
                            checking.addMoney(amount);
                            System.out.println(ConsoleUtility.YELLOW + transaction.transferHistory(amount, false, saving.getBalance(), checking.getBalance()) + ConsoleUtility.RESET);
                        } else {
                            System.out.println(ConsoleUtility.RED + "Insufficient funds in saving" + ConsoleUtility.RESET);
                        }
                    } else {
                        System.out.println(ConsoleUtility.RED + "Invalid Transfer! You are transferring money to same account" + ConsoleUtility.RESET);
                    }
                    try {
                        Thread.sleep(3000);  // 2000 milliseconds, or 2 seconds
                    } catch (Exception e) {
                        System.out.println("error");
                    }
                    ConsoleUtility.clearScreen();
                } else if (choice == 4) {
                    System.out.println("Savings Account Balance : $" + saving.getBalance());
                    System.out.println("Checking Account Balance: $" + checking.getBalance());
                    transaction.addOthers(". Got Account Balances");
                    try {
                        Thread.sleep(4000);  // 2000 milliseconds, or 2 seconds
                    } catch (Exception e) {
                        System.out.println("error");
                    }
                    ConsoleUtility.clearScreen();
                } else if (choice == 5) {
                    System.out.println(ConsoleUtility.YELLOW + transaction.getHistory() + ConsoleUtility.RESET);
                    transaction.addOthers(". Got Transaction History");
                    try {
                        Thread.sleep(6000);  // 2000 milliseconds, or 2 seconds
                    } catch (Exception e) {
                        System.out.println("error");
                    }
                    ConsoleUtility.clearScreen();
                } else if (choice == 6) {
                    System.out.print("Enter your new 4 digit pin: ");
                    int changePin = scan.nextInt();
                    scan.nextLine();
                    ConsoleUtility.clearScreen();
                    while (changePin < 1000 || changePin > 9999) {
                        System.out.println(ConsoleUtility.RED + "That is not 4 digit" + ConsoleUtility.RESET);
                        System.out.print("Enter your 4 digit pin number again: ");
                        changePin = scan.nextInt();
                        scan.nextLine();
                        ConsoleUtility.clearScreen();
                    }
                    customer.setPin(changePin);
                    System.out.println(ConsoleUtility.GREEN + "Pin Changed Successfully" + ConsoleUtility.RESET);
                    transaction.addOthers(". Pin Changed Successfully");
                    try {
                        Thread.sleep(2000);  // 2000 milliseconds, or 2 seconds
                    } catch (Exception e) {
                        System.out.println("error");
                    }
                    ConsoleUtility.clearScreen();
                }
            }
            count++;
        }
        System.out.println(ConsoleUtility.GREEN + "Goodbye" + ConsoleUtility.RESET);
    }

    private void printMenu() {
        System.out.println(ConsoleUtility.CYAN + "Main Menu:");
        System.out.println("1. Withdraw Money");
        System.out.println("2. Deposit money");
        System.out.println("3. Transfer money between accounts");
        System.out.println("4. Get account balances");
        System.out.println("5. Get  transaction history");
        System.out.println("6. Change PIN");
        System.out.println("7. Exit" + ConsoleUtility.RESET);
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
