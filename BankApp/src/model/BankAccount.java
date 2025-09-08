package model;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private boolean frozen;
    private List<Transaction> transactionList;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.frozen = false;
        this.transactionList = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    // ✅ Thay vì setBalance, dùng deposit/withdraw
    public void deposit(double amount) {
        if (frozen) {
            System.out.println("Account is frozen. Cannot deposit.");
            return;
        }
        if (amount > 0) {
            balance += amount;
            transactionList.add(new Transaction(TransactionType.DEPOSIT, amount, "Deposited money"));
        }
    }

    public void withdraw(double amount) {
        if (frozen) {
            System.out.println("Account is frozen. Cannot withdraw.");
            return;
        }
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionList.add(new Transaction(TransactionType.WITHDRAWAL, amount, "Withdrawn money"));
        } else {
            System.out.println("Insufficient funds!");
        }
    }
}
