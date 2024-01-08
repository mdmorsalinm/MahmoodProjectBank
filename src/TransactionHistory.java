public class TransactionHistory {
    private String history;

    public TransactionHistory() {
        history = "";
    }

    public String getHistory() {
        return history;
    }

    public void depositHistory(double money, boolean isChecking) {
        if (isChecking) {
            history += "Deposited $" + money + " into checking\n";
        } else {
            history += "Deposited $" + money + " into savings\n";
        }
    }

    public void withdrawHistory(double money, boolean isChecking) {
        if (isChecking) {
            history += "Withdrew $" + money + " from checking\n";
        } else {
            history += "Withdrew $" + money + " from savings\n";
        }
    }

    public void transferHistory(double money, boolean transferFromChecking) {
        if (transferFromChecking) {
            history += "Transferred $" + money + " from checking to savings\n";
        } else {
            history += "Transferred $" + money + " from savings to checking\n";
        }
    }
}
