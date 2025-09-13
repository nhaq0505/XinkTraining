package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;

public class SavingsAccount extends Account {
    private static final BigDecimal MINIMUM_BALANCE = new BigDecimal("500");
    private final BigDecimal interestRate; // e.g. 0.02 = 2% annual
    private LocalDate lastInterestCalculation;

    public SavingsAccount(String accountHolder, Currency currency, BigDecimal interestRate) {
        super(accountHolder, currency);
        this.interestRate = interestRate;
        this.lastInterestCalculation = LocalDate.now();
    }

    // Interest calculation (simple annual interest, applied monthly)
    @Override
    public void calculateInterest() {
        LocalDate now = LocalDate.now();
        if (now.isAfter(lastInterestCalculation.plusMonths(1))) {
            BigDecimal monthlyInterest = getBalance()
                    .multiply(interestRate)
                    .divide(new BigDecimal("12"), BigDecimal.ROUND_HALF_UP);

            depositInterest(monthlyInterest);
            lastInterestCalculation = now;
        }
    }

    private void depositInterest(BigDecimal interest) {
        setBalance(getBalance().add(interest));
        recordTransaction(new Transaction(
                interest,
                TransactionType.INTEREST,
                LocalDateTime.now(),
                getBalance()
        ));
    }

    // Override withdraw to enforce minimum balance
    @Override
    public void withdraw(BigDecimal amount) {
        if (getStatus() != AccountStatus.FROZEN &&
                getBalance().subtract(amount).compareTo(MINIMUM_BALANCE) >= 0) {
            setBalance(getBalance().subtract(amount));
            recordTransaction(new Transaction(
                    amount,
                    TransactionType.WITHDRAW,
                    LocalDateTime.now(),
                    getBalance()
            ));
        } else {
            System.out.println("Withdrawal failed. Minimum balance requirement not met.");
        }
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }
}
