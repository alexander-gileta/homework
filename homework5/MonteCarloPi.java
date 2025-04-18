import java.util.*;
import java.util.concurrent.*;

public class MonteCarloPi {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long[] simulations = { 10_000_000L, 100_000_000L, 1_000_000_000L };
        
        System.out.println("Single-threaded version:");
        for (long simulationCount : simulations) {
            long startTime = System.nanoTime();
            singleThreadedPiCalculation(simulationCount);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("Time for " + simulationCount + " simulations: " + duration / 1_000_000 + " ms");
        }

        System.out.println("\nMulti-threaded version:");
        for (long simulationCount : simulations) {
            long startTime = System.nanoTime();
            multiThreadedPiCalculation(simulationCount);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("Time for " + simulationCount + " simulations: " + duration / 1_000_000 + " ms");
        }
    }

    public static void singleThreadedPiCalculation(long simulations) {
        long circleCount = 0;
        long totalCount = 0;

        Random random = new Random();

        for (long i = 0; i < simulations; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            if (x * x + y * y <= 1) {
                circleCount++;
            }
            totalCount++;
        }

        double pi = 4.0 * circleCount / totalCount;
        System.out.println("Estimated: " + pi);
    }

    public static void multiThreadedPiCalculation(long simulations) throws InterruptedException, ExecutionException {
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        int simulationsPerThread = (int) (simulations / threads);

        List<Callable<long[]>> tasks = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            final int threadId = i;
            tasks.add(() -> {
                long circleCount = 0;
                long totalCount = 0;
                ThreadLocalRandom random = ThreadLocalRandom.current();

                for (int j = 0; j < simulationsPerThread; j++) {
                    double x = random.nextDouble();
                    double y = random.nextDouble();

                    if (x * x + y * y <= 1) {
                        circleCount++;
                    }
                    totalCount++;
                }

                return new long[] { circleCount, totalCount };
            });
        }

        List<Future<long[]>> results = executor.invokeAll(tasks);

        long totalCircleCount = 0;
        long totalTotalCount = 0;

        for (Future<long[]> result : results) {
            long[] counts = result.get();
            totalCircleCount += counts[0];
            totalTotalCount += counts[1];
        }

        double pi = 4.0 * totalCircleCount / totalTotalCount;
        System.out.println("Estimated: " + pi);

        executor.shutdown();
    }
}
