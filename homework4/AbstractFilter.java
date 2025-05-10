import java.io.IOException;
import java.nio.file.*;
import java.util.regex.Pattern;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    default AbstractFilter and(AbstractFilter other) {
        return path -> this.accept(path) && other.accept(path);
    }

    static AbstractFilter regularFile() {
        return Files::isRegularFile;
    }

    static AbstractFilter readable() {
        return Files::isReadable;
    }

    static AbstractFilter writable() {
        return Files::isWritable;
    }

    static AbstractFilter largerThan(long size) {
        return path -> {
            try {
                return Files.size(path) > size;
            } catch (IOException e) {
                return false;
            }
        };
    }

    static AbstractFilter hasExtension(String extension) {
        return path -> path.toString().endsWith(extension);
    }

    static AbstractFilter regexContains(String regex) {
        Pattern pattern = Pattern.compile(regex);
        return path -> pattern.matcher(path.getFileName().toString()).find();
    }

    static AbstractFilter magicNumber(int... magicNumbers) {
        return path -> {
            try {
                byte[] fileHeader = new byte[magicNumbers.length];
                try (var inputStream = Files.newInputStream(path)) {
                    inputStream.read(fileHeader);
                }
                for (int i = 0; i < magicNumbers.length; i++) {
                    if (fileHeader[i] != magicNumbers[i]) {
                        return false;
                    }
                }
                return true;
            } catch (IOException e) {
                return false;
            }
        };
    }

    static AbstractFilter globMatches(String glob) {
        return path -> {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
            return matcher.matches(path.getFileName());
        };
    }
}