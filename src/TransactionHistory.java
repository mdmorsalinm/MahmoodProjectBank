public class TransactionHistory {
    private String history;
    private int idA;
    private int idS;

    public TransactionHistory() {
        idS = 0; //id for security transactions
        idA = 0; //id for account transactions
        history = "";
    }

    public String getHistory() {
        return history;
    }

    public String depositHistory(double money, boolean isChecking, double currentBalance) {
        String str;
        if (isChecking) {
            str = "ID: A" + displayID("a") + ". Successfully Deposited $" + money + " into checking. Current Balance $" + currentBalance;
            history += str + "\n";
        } else {
            str = "ID: A" + displayID("a") + ". Successfully Deposited $" + money + " into savings. Current Balance $" + currentBalance;
            history += str + "\n";
        }
        idA++;
        return str;
    }

    public String withdrawHistory(double money, boolean isChecking, double currentBalance) {
        String str;
        if (isChecking) {
            str = "ID: A" + displayID("a") + ". Successfully Withdrew $" + money + " from checking. Current Balance $" + currentBalance;
            history += str + "\n";
        } else {
            str = "ID: A" + displayID("a") + ". Successfully Withdrew $" + money + " from savings. Current Balance $" + currentBalance;
            history += str + "\n";
        }
        idA++;
        return str;
    }

    public String transferHistory(double money, boolean transferFromChecking, double savingBalance, double checkingBalance) {
        String str;
        if (transferFromChecking) {
            str = "ID: A" + displayID("a") + ". Successfully Transferred $" + money + " from checking to savings. Balance for savings: $" + savingBalance + ", checking: $" + checkingBalance;
            history += str + "\n";
        } else {
            str = "ID: A" + displayID("a") + ". Successfully Transferred $" + money + " from savings to checking. Balance for savings: $" + savingBalance + ", checking: $" + checkingBalance;
            history += str + "\n";
        }
        idA++;
        return str;
    }

    // security transactions
    public void addOthers(String str) {
        history += "ID: S" + displayID("s") + str;
        idS++;
    }

    // adds zeros in front of the id to make it 4-digit number
    private String displayID(String str) {
        String idStr = "";
        if (str.equals("s")) {
            idStr += idS;
            while (idStr.length() < 4) {
                idStr = "0" + idStr;
            }
        } else if (str.equals("a")) {
            idStr += idA;
            while (idStr.length() < 4) {
                idStr = "0" + idStr;
            }
        }
        return idStr;
    }
}
