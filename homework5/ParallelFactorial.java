import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class ParallelFactorial {

    public static class FactorialTask implements Callable<Long> {
        private final long start;
        private final long end;

        public FactorialTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Long call() {
            long result = 1;
            for (long i = start; i <= end; i++) {
                result *= i;
            }
            return result;
        }
    }

    public static long calculateFactorial(long n, int numThreads) throws InterruptedException, ExecutionException {
        if (n == 0 || n == 1) {
            return 1;
        }

        long chunkSize = n / numThreads;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Long>> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            long start = i * chunkSize + 1;
            long end = (i == numThreads - 1) ? n : (i + 1) * chunkSize;
            futures.add(executor.submit(new FactorialTask(start, end)));
        }

        long result = 1;
        for (Future<Long> future : futures) {
            result *= future.get();
        }

        executor.shutdown();
        return result;
    }

    public static void main(String[] args) {
        try {
            long number = 20;
            int numThreads = 4;

            long factorial = calculateFactorial(number, numThreads);
            System.out.println(number + " : " + factorial);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
