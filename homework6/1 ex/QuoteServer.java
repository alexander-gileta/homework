import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class QuoteServer {
    private static final int MAX_CONNECTIONS = 2; 
    private static final Map<String, String> quotes = new HashMap<>(); 
    private static final Semaphore connectionSemaphore = new Semaphore(MAX_CONNECTIONS);

    static {

        quotes.put("личности", "Не переходи на личности там, где их нет");
        quotes.put("оскорбления", "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами");
        quotes.put("глупый", "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.");
        quotes.put("интеллект", "Чем ниже интеллект, тем громче оскорбления");
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CONNECTIONS); 

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Сервер запущен, ожидание клиентов...");

            while (true) {
                connectionSemaphore.acquire(); 

                Socket clientSocket = serverSocket.accept();

                pool.submit(new ClientHandler(clientSocket)); 
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String message = reader.readLine(); 
                System.out.println("Получено от клиента: " + message);

                String response = quotes.getOrDefault(message, "Цитаты по этому запросу нет.");
                writer.println(response);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                connectionSemaphore.release();
                System.out.println("Обслуживание клиента завершено.");
            }
        }
    }
}
