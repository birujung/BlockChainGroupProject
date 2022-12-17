/**
 * A class representing a simple bank account with a balance.
 * The account can be accessed by multiple threads concurrently,
 * and the balance is updated atomically.
 *
 * @author  Aldrian Raffi Wicaksono (2106653256),
 *          Amrita Deviayu Tunjungbiru (2106636584),
 *          Bisma Alif Alghifari (2106731402)
 */
public class SavingsAccount extends Thread
{
    /**
     * The current balance of the savings account.
     */
    private float balance;

    /**
     * Constructor for a SavingsAccount instance.
     * @param initialBalance the initial balance of the account
     */
    public SavingsAccount(float initialBalance)
    {
        this.balance = initialBalance;
        System.out.println("Initial Balance: " + initialBalance);
    }

    /**
     * Withdraws a given amount of money from the account.
     *
     * If the given amount is negative, an IllegalArgumentException will be thrown.
     * If the given amount is greater than the current balance, the withdrawal will not be processed.
     *
     * @param anAmount The amount of money to withdraw.
     * @throws IllegalArgumentException If the given amount is negative.
     */
    public void withdraw(float anAmount)
    {
        if (anAmount < 0.0)
        {
            throw new IllegalArgumentException("Withdraw anAmount negative");
        }
//        if (anAmount<=balance) {
//            System.out.println(Thread.currentThread().getName() + " Withdrew " + anAmount + " from account. New balance: " + balance);
//            synchronized(this){balance = balance - anAmount;}
//        }
         synchronized(this) {
             System.out.println(Thread.currentThread().getName() + " Withdrew " + anAmount + " from account. New balance: " + balance);
             if (anAmount<=balance){balance = balance - anAmount;}
         }
        System.out.println(balance);
    }

    /**
     * This method is used to deposit a certain amount into the SavingsAccount object.
     *
     * @param anAmount the amount to be deposited into the account
     * @throws IllegalArgumentException if the given amount is negative
     */
    public void deposit(float anAmount)
    {
        if (anAmount < 0.0)
        {
            throw new IllegalArgumentException("Deposit anAmount negative");
        }
        synchronized(this)
        {
            balance = balance + anAmount;
            System.out.println("Deposited " + anAmount + " into account. New balance: " + balance);
        }
        System.out.println(balance);
    }


    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 10; i++)
            withdraw(20);
    }

    public static void main(String[] args) {
        SavingsAccount account = new SavingsAccount(150);
        Thread thread3 = new Thread(() -> {
            account.deposit(50);
        });
        Thread thread1 = new Thread(account);
        Thread thread2 = new Thread(account);
        thread1.setName("Thread 1");
        thread2.setName("Thread 2");
        thread3.setName("Thread 3");
        thread3.start();
        thread1.start();
        thread2.start();
    }
}