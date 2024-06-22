import java.util.*;



class Bank {

    private static Map<String, Account> accounts = new HashMap<>();



    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n1. Create Account");

            System.out.println("2. Transaction History");

            System.out.println("3. Transfer Amount");

            System.out.println("4. Check Balance");

            System.out.println("5. Deposit");

            System.out.println("6. Withdraw");

            System.out.println("7. Account Info");

            System.out.println("8. Exit");

            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            scanner.nextLine(); // Consume newline



            switch (option) {

                case 1:

                    createAccount(scanner);

                    break;

                case 2:

                    transactionHistory(scanner);

                    break;

                case 3:

                    transferAmount(scanner);

                    break;

                case 4:

                    checkBalance(scanner);

                    break;

                case 5:

                    deposit(scanner);

                    break;

                case 6:

                    withdraw(scanner);

                    break;

                case 7:

                    accountInfo(scanner);

                    break;

                case 8:

                    System.out.println("Exiting...");

                    return;

                default:

                    System.out.println("Invalid option Selected. Please try again.");

            }

        }

    }



    private static void createAccount(Scanner scanner) {

        System.out.print("Enter account holder name: ");

        String holderName = scanner.nextLine();

        System.out.print("Enter account number: ");

        String accountNumber = scanner.nextLine();

        System.out.print("Enter PIN: ");

        String pin = scanner.nextLine();

        System.out.print("Enter account holder age: ");

        int age = scanner.nextInt();

        scanner.nextLine(); // Consume newline

        System.out.print("Enter account type (saving/business): ");

        String accountType = scanner.nextLine().toLowerCase();

        if (!accountType.equals("saving") && !accountType.equals("business")) {

            System.out.println("Invalid account type. It should be either 'saving' or 'business'.");

            return;

        }



        if (accounts.containsKey(accountNumber)) {

            System.out.println("Account already exists.");

            return;

        }



        Account account = new Account(accountNumber, pin, holderName, age, accountType);

        accounts.put(accountNumber, account);

        System.out.println("Account created successfully.");

    }



    private static void transactionHistory(Scanner scanner) {

        Account account = authenticate(scanner);

        if (account == null) return;



        account.displayTransactionHistory();

    }



    private static void transferAmount(Scanner scanner) {

        Account account = authenticate(scanner);

        if (account == null) return;



        System.out.print("Enter recipient account number: ");

        String recipientAccountNumber = scanner.nextLine();

        if (!accounts.containsKey(recipientAccountNumber)) {

            System.out.println("Recipient account not found.");

            return;

        }



        System.out.print("Enter amount to transfer: ");

        double amount = scanner.nextDouble();

        scanner.nextLine(); // Consume newline



        Account recipientAccount = accounts.get(recipientAccountNumber);

        if (account.transfer(recipientAccount, amount)) {

            System.out.println("Transfer successful.");

        } else {

            System.out.println("Transfer failed. Insufficient balance.");

        }

    }



    private static void checkBalance(Scanner scanner) {

        Account account = authenticate(scanner);

        if (account == null) return;



        System.out.println("Current balance: " + account.getBalance());

    }



    private static void deposit(Scanner scanner) {

        Account account = authenticate(scanner);

        if (account == null) return;



        System.out.print("Enter amount to deposit: ");

        double amount = scanner.nextDouble();

        scanner.nextLine(); // Consume newline



        account.deposit(amount);

        System.out.println("Deposit successful.");

    }



    private static void withdraw(Scanner scanner) {

        Account account = authenticate(scanner);

        if (account == null) return;



        System.out.print("Enter amount to withdraw: ");

        double amount = scanner.nextDouble();

        scanner.nextLine(); // Consume newline



        if (account.withdraw(amount)) {

            System.out.println("Withdrawal successful.");

        } else {

            System.out.println("Withdrawal failed. Insufficient balance.");

        }

    }



    private static void accountInfo(Scanner scanner) {

        Account account = authenticate(scanner);

        if (account == null) return;



        account.displayAccountInfo();

    }



    private static Account authenticate(Scanner scanner) {

        System.out.print("Enter account number: ");

        String accountNumber = scanner.nextLine();

        System.out.print("Enter PIN: ");

        String pin = scanner.nextLine();



        if (accounts.containsKey(accountNumber)) {

            Account account = accounts.get(accountNumber);

            if (account.authenticate(pin)) {

                return account;

            } else {

                System.out.println("Invalid PIN.");

            }

        } else {

            System.out.println("Account not found.");

        }

        return null;

    }

}



class Account {

    private String accountNumber;

    private String pin;

    private double balance;

    private String holderName;

    private int age;

    private String accountType;

    private List<String> transactionHistory;



    public Account(String accountNumber, String pin, String holderName, int age, String accountType) {

        this.accountNumber = accountNumber;

        this.pin = pin;

        this.holderName = holderName;

        this.age = age;

        this.accountType = accountType;

        this.balance = 0;

        this.transactionHistory = new ArrayList<>();

    }



    public boolean authenticate(String pin) {

        return this.pin.equals(pin);

    }



    public void displayTransactionHistory() {

        if (transactionHistory.isEmpty()) {

            System.out.println("No transactions found.");

        } else {

            for (String transaction : transactionHistory) {

                System.out.println(transaction);

            }

        }

    }



    public boolean transfer(Account recipient, double amount) {

        if (amount > balance) {

            return false;

        }

        this.balance -= amount;

        recipient.balance += amount;

        this.transactionHistory.add("Transferred " + amount + " to " + recipient.accountNumber);

        recipient.transactionHistory.add("Received " + amount + " from " + this.accountNumber);

        return true;

    }



    public double getBalance() {

        return balance;

    }



    public void deposit(double amount) {

        this.balance += amount;

        this.transactionHistory.add("Deposited " + amount);

    }



    public boolean withdraw(double amount) {

        if (amount <= balance) {

            this.balance -= amount;

            this.transactionHistory.add("Withdrew " + amount);

            return true;

        } else {

            return false;

        }

    }



    public void displayAccountInfo() {

        System.out.println("Account Number: " + accountNumber);

        System.out.println("Account Holder Name: " + holderName);

        System.out.println("Account Holder Age: " + age);

        System.out.println("Account Type: " + accountType);

        System.out.println("Current Balance: " + balance);

    }

}


