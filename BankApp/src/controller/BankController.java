package controller;

import model.BankAccount;
import model.SavingsAccount;
import model.CheckingAccount;

public class BankController {

    public void transfer(BankAccount from, BankAccount to, double amount) {
        if (from.isFrozen() || to.isFrozen()) {
            System.out.println("Account is frozen. Cannot transfer.");
            return;
        }
        if (from.getBalance() >= amount) {
            from.withdraw(amount);
            to.deposit(amount);
            System.out.println("Transfer successful.");
        }
    }

    public void viewBalance(BankAccount account) {
        System.out.println("Balance: " + account.getBalance());
    }

    public void freezeAccount(BankAccount account){
        account.setFrozen(true);
    }
    public void unfreezeAccount(BankAccount account){
        account.setFrozen(false);
    }
    public void viewTransactionList(BankAccount account){
        account.getTransactionList().forEach(System.out::println);
    }

    public void viewInterest(SavingsAccount account){
        account.applyMonthlyInterest();
    }

}
