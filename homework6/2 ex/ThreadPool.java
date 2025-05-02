import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public interface ThreadPool extends AutoCloseable {
        void start();
        void execute(Runnable runnable);
    }

    public static class FixedThreadPool implements ThreadPool {
        private final int numThreads;
        private final Thread[] threads;
        private final Queue<Runnable> taskQueue;
        private volatile boolean isRunning;

        public FixedThreadPool(int numThreads) {
            this.numThreads = numThreads;
            this.threads = new Thread[numThreads];
            this.taskQueue = new LinkedList<>();
        }

        @Override
        public void start() {
            isRunning = true;
            for (int i = 0; i < numThreads; i++) {
                threads[i] = new Thread(() -> {
                    while (isRunning || !taskQueue.isEmpty()) {
                        Runnable task;
                        synchronized (taskQueue) {
                            while (taskQueue.isEmpty() && isRunning) {
                                try {
                                    taskQueue.wait();
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                            task = taskQueue.poll();
                        }
                        if (task != null) {
                            task.run();
                        }
                    }
                });
                threads[i].start();
            }
        }

        @Override
        public void execute(Runnable runnable) {
            synchronized (taskQueue) {
                taskQueue.offer(runnable);
                taskQueue.notify();
            }
        }

        @Override
        public void close() throws Exception {
            isRunning = false;
            synchronized (taskQueue) {
                taskQueue.notifyAll();
            }
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static class FibonacciTask implements Runnable {
        private final int n;

        public FibonacciTask(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            System.out.println("Fibonacci(" + n + ") = " + fibonacci(n));
        }

        private long fibonacci(int n) {
            if (n <= 1) return n;
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public static void main(String[] args) {
        try {
            int numThreads = 4;
            ThreadPool threadPool = new FixedThreadPool(numThreads);

            threadPool.start();

            for (int i = 0; i < 10; i++) {
                threadPool.execute(new FibonacciTask(i));
            }

            threadPool.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
