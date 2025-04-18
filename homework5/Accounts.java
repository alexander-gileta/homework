import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Accounts {
    
    public static class Account {
        private int cacheBalance;
        private final Lock lock = new ReentrantLock();

        public Account(int cacheBalance) {
            this.cacheBalance = cacheBalance;
        }

        public void addMoney(int money) {
            this.cacheBalance += money;
        }

        public boolean takeOffMoney(int money) {
            if (this.cacheBalance < money) {
                return false;
            }
            this.cacheBalance -= money;
            return true;
        }

        public int getCacheBalance() {
            return cacheBalance;
        }

        public Lock getLock() {
            return lock;
        }
    }

    public static class AccountThread implements Runnable {
        private final Account accountFrom;
        private final Account accountTo;
        private final int money;

        public AccountThread(Account accountFrom, Account accountTo, int money) {
            this.accountFrom = accountFrom;
            this.accountTo = accountTo;
            this.money = money;
        }

        @Override
        public void run() {
            for (int i = 0; i < 4000; i++) {
                Lock lock1 = accountFrom.getLock();
                Lock lock2 = accountTo.getLock();

                if (lock1.hashCode() < lock2.hashCode()) {
                    lock1.lock();
                    lock2.lock();
                } else {
                    lock2.lock();
                    lock1.lock();
                }

                try {
                    if (accountFrom.takeOffMoney(money)) {
                        accountTo.addMoney(money);
                    }
                } finally {
                    lock1.unlock();
                    lock2.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        Account firstAccount = new Account(100_000);
        Account secondAccount = new Account(100_000);

        AccountThread firstThread = new AccountThread(firstAccount, secondAccount, 100);
        AccountThread secondThread = new AccountThread(secondAccount, firstAccount, 100);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(firstThread),
                CompletableFuture.runAsync(secondThread)
        ).join();

        System.out.println("Cash balance first: " + firstAccount.getCacheBalance());
        System.out.println("Cash balance second: " + secondAccount.getCacheBalance());
    }
}
