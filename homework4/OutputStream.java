import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.zip.CheckedOutputStream;
import java.util.zip.CRC32;

public class Main {
    public static void main(String[] args) {
        Path path = Path.of("output.txt");

        try (
            FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
            CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStream, new CRC32());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
            PrintWriter printWriter = new PrintWriter(outputStreamWriter)
        ) {
            printWriter.println("Programming is learned by writing programs. ― Brian Kernighan");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}