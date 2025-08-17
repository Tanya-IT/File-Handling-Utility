import java.io.IOException;

public class File {
    
    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : "example.txt";
        try {
            System.out.println("=== Original Content ===");
            String content = FileHand.readAll(path);
            System.out.println(content);

            String extra = "\nMy name is Tanya.";
            System.out.println("\nAppending a line...");
            FileHand.append(path, extra);

        /*  System.out.println("Modifying lines to uppercase...");
            FileHand.modifyLines(path, line -> line.toUpperCase());  */

            System.out.println("\n=== Modified Content ===");
            System.out.println(FileHand.readAll(path));

            String replacement = "Hello!";
            long pos = 0;
            System.out.printf("\nWriting '%s' at byte offset %d...\n", replacement, pos);
            FileHand.writeAt(path, pos, replacement.getBytes());

            System.out.println("\n=== Final Content ===");
            System.out.println(FileHand.readAll(path));

        } catch (IOException e) {
            System.err.printf("I/O error processing '%s': %s%n", path, e);
        }
    }
}