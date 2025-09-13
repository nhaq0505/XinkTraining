import model.CheckingAccount;
import model.SavingsAccount;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class Main {
    public static void main(String[] args) {
        // 1. Create accounts with different currencies
        SavingsAccount savings = new SavingsAccount(
                "Alice",
                Currency.getInstance("USD"),
                new BigDecimal("0.02") // 2% annual interest
        );

        CheckingAccount checking = new CheckingAccount(
                "Bob",
                Currency.getInstance("EUR"),
                new BigDecimal("500") // overdraft limit
        );

        // Deposit initial funds
        savings.deposit(new BigDecimal("2000"));
        checking.deposit(new BigDecimal("1000"));

        System.out.println("Initial Balances:");
        System.out.println("Savings: " + savings.getBalance());
        System.out.println("Checking: " + checking.getBalance());
        System.out.println("-----------------------------------");

        // 2. Concurrent transactions
        Thread t1 = new Thread(() -> savings.deposit(new BigDecimal("500")));
        Thread t2 = new Thread(() -> savings.withdraw(new BigDecimal("300")));
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("After concurrent transactions, Savings balance: " + savings.getBalance());
        System.out.println("-----------------------------------");

        // 3. Interest calculation over time
        System.out.println("Savings before interest: " + savings.getBalance());
        savings.calculateInterest(); // simulate interest credit
        System.out.println("Savings after interest: " + savings.getBalance());
        System.out.println("-----------------------------------");

        // 4. Overdraft scenarios
        System.out.println("Checking balance before overdraft: " + checking.getBalance());
        checking.withdraw(new BigDecimal("1200")); // should trigger overdraft
        System.out.println("Checking balance after overdraft: " + checking.getBalance());
        checking.withdraw(new BigDecimal("5000")); // should be denied
        System.out.println("Checking balance after denied overdraft: " + checking.getBalance());
        System.out.println("-----------------------------------");

        // 5. Transaction history queries
        System.out.println("Transaction history for Savings:");
        savings.getTransactions().forEach(System.out::println);

        System.out.println("\nTransaction history for Checking:");
        checking.getTransactions().forEach(System.out::println);

        // Extra: write a check
        checking.writeCheck(new BigDecimal("200"), "Charlie");
        System.out.println("\nAfter writing a check, Checking balance: " + checking.getBalance());
    }
}
