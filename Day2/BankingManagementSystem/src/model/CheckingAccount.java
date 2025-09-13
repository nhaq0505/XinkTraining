package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public class CheckingAccount extends Account {
    private BigDecimal overdraftLimit;
    private static final BigDecimal OVERDRAFT_FEE = new BigDecimal("35");

    public CheckingAccount(String accountHolder, Currency currency, BigDecimal overdraftLimit) {
        super(accountHolder, currency);
        this.overdraftLimit = overdraftLimit;
    }

    // Override withdraw with overdraft protection
    @Override
    public void withdraw(BigDecimal amount) {
        if (getStatus() != AccountStatus.FROZEN) {
            BigDecimal newBalance = getBalance().subtract(amount);

            if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
                // Normal withdrawal
                setBalance(newBalance);
                recordTransaction(new Transaction(
                        amount,
                        TransactionType.WITHDRAW,
                        LocalDateTime.now(),
                        getBalance()
                ));
            } else if (newBalance.compareTo(overdraftLimit.negate()) >= 0) {
                // Allowed overdraft (apply overdraft fee)
                setBalance(newBalance.subtract(OVERDRAFT_FEE));
                recordTransaction(new Transaction(
                        amount,
                        TransactionType.WITHDRAW,
                        LocalDateTime.now(),
                        getBalance()
                ));
                recordTransaction(new Transaction(
                        OVERDRAFT_FEE,
                        TransactionType.FEE,
                        LocalDateTime.now(),
                        getBalance()
                ));
                System.out.println("Overdraft used. Fee applied: " + OVERDRAFT_FEE);
            } else {
                System.out.println("Withdrawal denied. Overdraft limit exceeded.");
            }
        } else {
            System.out.println("Withdrawal denied. Account is frozen.");
        }
    }

    // Implement check writing
    public void writeCheck(BigDecimal amount, String payee) {
        System.out.println("Writing check to " + payee + " for " + amount);
        withdraw(amount);
        recordTransaction(new Transaction(
                amount,
                TransactionType.CHECK,
                LocalDateTime.now(),
                getBalance()
        ));
    }

    // Implement fee calculation (simple demo: overdraft fee is fixed)
    public BigDecimal getOverdraftFee() {
        return OVERDRAFT_FEE;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(BigDecimal overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void calculateInterest() {
        // Checking accounts typically don't accrue interest
    }

    @Override
    public String getAccountType() {
        return "Checking Account";
    }
}
