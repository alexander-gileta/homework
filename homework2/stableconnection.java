import java.util.Random;

public interface Connection extends AutoCloseable {
    void execute(String command);
}

public class StableConnection implements Connection {
    @Override
    public void execute(String command) {
        System.out.println("Executing: " + command);
    }

    @Override
    public void close() {
        System.out.println("StableConnection closed.");
    }
}

public class FaultyConnection implements Connection {
    private final Random random = new Random();

    @Override
    public void execute(String command) {
        if (random.nextBoolean()) {
            throw new ConnectionException("Connection failed while executing: " + command);
        }
        System.out.println("Executing: " + command);
    }

    @Override
    public void close() {
        System.out.println("FaultyConnection closed.");
    }
}

public interface ConnectionManager {
    Connection getConnection();
}

public class DefaultConnectionManager implements ConnectionManager {
    private final Random random = new Random();

    @Override
    public Connection getConnection() {
        if (random.nextInt(100) < 70) {
            return new StableConnection();
        } else {
            return new FaultyConnection();
        }
    }
}

public class FaultyConnectionManager implements ConnectionManager {
    @Override
    public Connection getConnection() {
        return new FaultyConnection();
    }
}

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    void tryExecute(String command) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try (Connection connection = manager.getConnection()) {
                connection.execute(command);
                return;
            } catch (ConnectionException e) {
                attempts++;
                System.err.println("Attempt " + attempts + " failed: " + e.getMessage());
                if (attempts == maxAttempts) {
                    throw new ConnectionException("Failed to execute command after " + maxAttempts + " attempts", e);
                }
            }
        }
    }
}