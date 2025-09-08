package model;

public class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolder, double balance, double overdraftLimit) {
        super(accountNumber, accountHolder, balance);
        this.overdraftLimit = overdraftLimit;
    }
    public double getOverdraftLimit() {
        return overdraftLimit;
    }
    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
    @Override
    public void withdraw(double amount) {
        if (isFrozen()) {
            System.out.println("Account is frozen. Cannot withdraw.");
            return;
        }
        if (amount > 0 && getBalance() + overdraftLimit >= amount) {
            // cho phép rút đến balance + overdraft
            double newBalance = getBalance() - amount;
            // hack nhỏ: gọi deposit(-amount) để ghi transaction (có thể tách hàm riêng)
            super.deposit(-amount);
            System.out.println("Withdraw successful with overdraft.");
        } else {
            System.out.println("Withdrawal exceeds overdraft limit!");
        }
    }

}
