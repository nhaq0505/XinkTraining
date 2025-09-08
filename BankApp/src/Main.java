import controller.BankController;
import model.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankController controller = new BankController();

        // Demo tài khoản
        BankAccount acc1 = new SavingsAccount("1001", "Nguyen Van A", 5000, 0.02);
        BankAccount acc2 = new CheckingAccount("1002", "Tran Thi B", 2000, 1000);

        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(acc1);
        accounts.add(acc2);

        while (true) {
            System.out.println("\n=== BANKING SYSTEM MENU ===");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Freeze Account");
            System.out.println("6. Unfreeze Account");
            System.out.println("7. View Transactions");
            System.out.println("8. Apply Interest (Savings Only)");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: // View balance
                    BankAccount accView = selectAccount(accounts, scanner);
                    controller.viewBalance(accView);
                    break;

                case 2: // Deposit
                    BankAccount accDeposit = selectAccount(accounts, scanner);
                    System.out.print("Enter amount: ");
                    double depAmount = scanner.nextDouble();
                    accDeposit.deposit(depAmount);
                    break;

                case 3: // Withdraw
                    BankAccount accWithdraw = selectAccount(accounts, scanner);
                    System.out.print("Enter amount: ");
                    double withAmount = scanner.nextDouble();
                    accWithdraw.withdraw(withAmount);
                    break;

                case 4: // Transfer
                    System.out.println("Select FROM account:");
                    BankAccount from = selectAccount(accounts, scanner);
                    System.out.println("Select TO account:");
                    BankAccount to = selectAccount(accounts, scanner);
                    System.out.print("Enter amount: ");
                    double transferAmount = scanner.nextDouble();
                    controller.transfer(from, to, transferAmount);
                    break;

                case 5: // Freeze
                    BankAccount accFreeze = selectAccount(accounts, scanner);
                    controller.freezeAccount(accFreeze);
                    break;

                case 6: // Unfreeze
                    BankAccount accUnfreeze = selectAccount(accounts, scanner);
                    controller.unfreezeAccount(accUnfreeze);
                    break;

                case 7: // Transactions
                    BankAccount accHistory = selectAccount(accounts, scanner);
                    controller.viewTransactionList(accHistory);
                    break;

                case 8: // Interest
                    BankAccount accInterest = selectAccount(accounts, scanner);
                    if (accInterest instanceof SavingsAccount) {
                        controller.viewInterest((SavingsAccount) accInterest);
                    } else {
                        System.out.println("Interest only applies to Savings Account!");
                    }
                    break;

                case 9: // Exit
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Helper method: chọn tài khoản từ danh sách
    private static BankAccount selectAccount(List<BankAccount> accounts, Scanner scanner) {
        System.out.println("Select account:");
        for (int i = 0; i < accounts.size(); i++) {
            BankAccount acc = accounts.get(i);
            System.out.println((i + 1) + ". " + acc.getAccountNumber() + " - " + acc.getAccountHolder());
        }
        int idx = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return accounts.get(idx - 1);
    }
}
