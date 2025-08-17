import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class FileHand {

    public static String readAll(String path) throws IOException {
        return Files.readString(Path.of(path), StandardCharsets.UTF_8);
    }

    public static void writeAll(String path, String content) throws IOException {
        Files.writeString(Path.of(path), content, StandardCharsets.UTF_8,
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void append(String path, String content) throws IOException {
        Files.writeString(Path.of(path), content, StandardCharsets.UTF_8,
            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static List<String> readLines(String path) throws IOException {
        return Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
    }

    public static void modifyLines(String path, LineModifier modifier) throws IOException {
        Path original = Path.of(path);
        Path temp = Files.createTempFile("temp-", ".tmp");
        try (BufferedReader br = Files.newBufferedReader(original, StandardCharsets.UTF_8);
             BufferedWriter bw = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(modifier.modify(line));
                bw.newLine();
            }
        }
        Files.move(temp, original, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void writeAt(String path, long position, byte[] data) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(path, "rw")) {
            raf.seek(position);
            raf.write(data);
        }
    }

    @FunctionalInterface
    public interface LineModifier {
        String modify(String originalLine);
    }
}