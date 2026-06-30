package atm;

import java.util.Scanner;

public class ATMInterface {

    private BankAccount account;
    private Scanner scanner;
    private boolean authenticated;

    public ATMInterface(BankAccount account, Scanner scanner) {
        this.account = account;
        this.scanner = scanner;
        this.authenticated = false;
    }

    public void start() {
        if (!authenticateUser()) {
            System.out.println("Too many incorrect attempts. Card retained. Goodbye.");
            return;
        }
        runMenuLoop();
    }

    private boolean authenticateUser() {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println();
            System.out.print("Enter your 4-digit PIN: ");
            int enteredPin = readValidInt();
            if (account.validatePin(enteredPin)) {
                authenticated = true;
                System.out.println("Authentication successful. Welcome, " + account.getAccountHolderName() + "!");
                return true;
            } else {
                attempts = attempts + 1;
                System.out.println("Incorrect PIN. Attempts remaining: " + (3 - attempts));
            }
        }
        return false;
    }

    private void runMenuLoop() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = readValidInt();
            switch (choice) {
                case 1:
                    processCheckBalance();
                    break;
                case 2:
                    processDeposit();
                    break;
                case 3:
                    processWithdraw();
                    break;
                case 4:
                    displayTransactionHistory();
                    break;
                case 5:
                    running = false;
                    System.out.println();
                    System.out.println("Thank you for using DecodeLabs ATM. Please take your card.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number between 1 and 5.");
            }
        }
    }

    private void displayMenu() {
        System.out.println();
        System.out.println("===== DecodeLabs ATM Menu =====");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. View Transaction History");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private void processCheckBalance() {
        System.out.println();
        System.out.println("Your current balance is: " + account.checkBalance());
    }

    private void processDeposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = readValidDouble();
        try {
            account.deposit(amount);
            System.out.println("Deposit successful. New balance: " + account.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }

    private void processWithdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = readValidDouble();
        try {
            account.withdraw(amount);
            System.out.println("Withdrawal successful. Please collect your cash.");
            System.out.println("New balance: " + account.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }

    private void displayTransactionHistory() {
        System.out.println();
        System.out.println("===== Transaction History =====");
        if (account.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        for (int i = 0; i < account.getTransactionHistory().size(); i++) {
            System.out.println((i + 1) + ". " + account.getTransactionHistory().get(i));
        }
    }

    private int readValidInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a whole number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        return value;
    }

    private double readValidDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid amount: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        return value;
    }
}
