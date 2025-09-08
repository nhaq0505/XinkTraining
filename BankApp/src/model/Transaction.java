package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private String transactionId;
    private LocalDateTime date;
    private TransactionType type;
    private double amount;
    private String description;
    private double balanceAfterTransaction;

    public Transaction(TransactionType type, double amount, String description, double balanceAfterTransaction) {
        this.transactionId = UUID.randomUUID().toString(); // tự sinh mã giao dịch
        this.date = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
    public Transaction(TransactionType type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
}
