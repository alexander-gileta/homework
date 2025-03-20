import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCloner {

    public static void cloneFile(Path path) {
        String originalFileName = path.getFileName().toString();
        Path parentDirectory = path.getParent();

        if (parentDirectory == null) {
            System.err.println("Ошибка: Указанный файл не имеет родительского каталога.");
            return;
        }

        String directory = parentDirectory.toString();
        String newFileName = originalFileName;
        int copyNumber = 1;

        Path newPath = Paths.get(directory, newFileName);

        while (Files.exists(newPath)) {
            if (copyNumber == 1) {
                newFileName = originalFileName.replaceFirst("(\\.[^.]+)?$", " — копия$1");
            } else {
                newFileName = originalFileName.replaceFirst("(\\.[^.]+)?$", " — копия (" + copyNumber + ")$1");
            }
            newPath = Paths.get(directory, newFileName);
            copyNumber++;
        }

        try {
            Files.copy(path, newPath);
            System.out.println("Файл скопирован: " + newPath);
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Path filePath = Paths.get("Tinkoff Bank Biggest Secret.txt");
        cloneFile(filePath);
    }
}