package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class Transaction {
    private final BigDecimal amount;
    private final TransactionType type; // Dùng enum thay cho String
    private final LocalDateTime date;
    private final BigDecimal balanceAfter;

    public Transaction(BigDecimal amount, TransactionType type, LocalDateTime date, BigDecimal balanceAfter) {
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.balanceAfter = balanceAfter;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    @Override
    public String toString() {
        return type + " | " + amount + " | " + date + " | Balance after: " + balanceAfter;
    }
}
