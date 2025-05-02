import java.io.*;
import java.net.*;

public class QuoteClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345); 
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Подключено к серверу. Введите запрос:");

            String input = userInput.readLine();
            writer.println(input);

            String response = reader.readLine();
            System.out.println("Сервер: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}