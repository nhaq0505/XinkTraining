package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public abstract class Account {
    private static int nextAccountNumber = 1000;
    private final int accountNumber;
    private final String accountHolder;
    private BigDecimal balance;
    private final Currency currency;
    private final List<Transaction> transactions;
    private AccountStatus status;
    private final LocalDateTime openedDate;

    // Constructor
    public Account(String accountHolder, Currency currency) {
        this.accountNumber = nextAccountNumber++;
        this.accountHolder = accountHolder;
        this.currency = currency;
        this.transactions = new ArrayList<>();
        this.openedDate = LocalDateTime.now();
        this.balance = BigDecimal.ZERO;
        this.status = AccountStatus.ACTIVE;
    }

    // Deposit
    public void deposit(BigDecimal amount) {
        if (status != AccountStatus.FROZEN && amount.compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(amount);
            recordTransaction(new Transaction(amount, TransactionType.DEPOSIT, LocalDateTime.now(), balance));
        } else {
            System.out.println("Deposit failed. Account frozen or invalid amount.");
        }
    }

    // Withdraw (mặc định, subclass có thể override)
    public void withdraw(BigDecimal amount) {
        if (status != AccountStatus.FROZEN && balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
            recordTransaction(new Transaction(amount, TransactionType.WITHDRAW, LocalDateTime.now(), balance));
        } else {
            System.out.println("Withdrawal failed. Either account frozen or insufficient funds.");
        }
    }

    // Record transaction (protected để subclass có thể gọi)
    protected void recordTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // Abstract methods
    public abstract void calculateInterest();
    public abstract String getAccountType();

    // Getter & Setter
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    protected void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public LocalDateTime getOpenedDate() {
        return openedDate;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
