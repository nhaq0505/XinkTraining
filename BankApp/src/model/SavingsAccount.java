package model;

public class SavingsAccount extends BankAccount {
    private  double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double balance, double interestRate) {
        super(accountNumber, accountHolder, balance);
        this.interestRate = interestRate;

    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // Tính lãi kép mỗi tháng
    public void applyMonthlyInterest() {
        double interest = getBalance() * interestRate;
        super.deposit(interest);
        System.out.println("Interest applied: " + interest);
    }
}
