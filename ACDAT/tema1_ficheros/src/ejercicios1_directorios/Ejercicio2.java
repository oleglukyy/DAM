package ejercicios1_directorios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Ejercicio2 {

    public static void main(String[] args) {
        new Ejercicio2().show();
    }

    public void show() {
        Path directoryPath = Paths.get("newDirectory");

        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);

                for (int i = 1; i <= 3; i++) {

                    Path filePath = directoryPath.resolve("file" + i + ".txt");
                    Files.createFile(filePath);

                    if (i == 1) {
                        Files.delete(filePath);
                    } else if (i == 2) {
                        Files.move(filePath, directoryPath.resolve("renombrado.txt"));
                    }

                }

                Path subDirectory = directoryPath.resolve("subDirectory");
                Files.createDirectory(subDirectory);
                Files.createFile(subDirectory.resolve("subFile.txt"));

                System.out.println(directoryPath.toAbsolutePath());
                System.out.println(subDirectory.toAbsolutePath());

                try (Stream<Path> stream = Files.list(directoryPath)) {
                    stream.forEach(System.out::println);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
