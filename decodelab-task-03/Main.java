package atm;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        BankAccount account = new BankAccount("ACC10234", "Bob Smith", 1234, 5000.0);

        System.out.println("Welcome to DecodeLabs ATM Services");
        System.out.println("Card inserted for account holder: " + account.getAccountHolderName());

        ATMInterface atm = new ATMInterface(account, scanner);
        atm.start();

        scanner.close();
    }
}
