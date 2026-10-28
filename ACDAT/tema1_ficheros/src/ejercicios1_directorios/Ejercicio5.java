package ejercicios1_directorios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class Ejercicio5 {
    //Borra un directorio que contenga ficheros y subdirectorios (que también contengan ficheros y subdirectorios).

    public static void main(String[] args) {

        new Ejercicio5().show(Paths.get("newDirectory"));
    }

    public void show(Path dir) {
        try (Stream<Path> content = Files.walk(dir)) {
            content.sorted(Comparator.reverseOrder())
                    .forEach(element -> {
                        try {
                            Files.delete(element);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {

        }
    }

}
