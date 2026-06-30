package atm;

import java.util.ArrayList;

public class BankAccount {

    private String accountNumber;
    private String accountHolderName;
    private int pin;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public BankAccount(String accountNumber, String accountHolderName, int pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<Transaction>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public boolean validatePin(int enteredPin) {
        return this.pin == enteredPin;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero");
        }
        balance = balance + amount;
        Transaction record = new Transaction("DEPOSIT", amount, balance);
        transactionHistory.add(record);
    }

    public void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds. Current balance is " + balance);
        }
        balance = balance - amount;
        Transaction record = new Transaction("WITHDRAW", amount, balance);
        transactionHistory.add(record);
    }

    public double checkBalance() {
        return balance;
    }
}
