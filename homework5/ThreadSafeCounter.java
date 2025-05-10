import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeCounter {

    private static AtomicInteger atomicCounter = new AtomicInteger(0);
    private static final Lock reentrantLock = new ReentrantLock();
    private static final Semaphore semaphore = new Semaphore(1);

    public synchronized static void synchronizedIncrement() {
        atomicCounter.incrementAndGet();
    }

    public static void atomicIncrement() {
        atomicCounter.incrementAndGet();
    }

    public static void reentrantLockIncrement() {
        reentrantLock.lock();
        try {
            atomicCounter.incrementAndGet();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void semaphoreIncrement() {
        try {
            semaphore.acquire();
            atomicCounter.incrementAndGet();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    public static void testCounter(Runnable incrementMethod, String methodName) throws InterruptedException {
        int threadsCount = 10;
        int incrementTimes = 10;
        atomicCounter.set(0);

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < incrementTimes; j++) {
                    incrementMethod.run();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println(methodName + " - Final result: " + atomicCounter.get());
        if (atomicCounter.get() != threadsCount * incrementTimes) {
            System.out.println("Error in " + methodName);
        } else {
            System.out.println("Test for " + methodName + " is successful!");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        testCounter(() -> synchronizedIncrement(), "Synchronized");

        testCounter(() -> atomicIncrement(), "AtomicInteger");

        testCounter(() -> reentrantLockIncrement(), "ReentrantLock");

        testCounter(() -> semaphoreIncrement(), "Semaphore");
    }
}
