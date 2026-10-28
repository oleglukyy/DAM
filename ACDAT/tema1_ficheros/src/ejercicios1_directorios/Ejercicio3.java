package ejercicios1_directorios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Ejercicio3 {
    public static void main(String[] args) {
        Path reqDirectory = Paths.get("newDirectory");
        new Ejercicio3().show(reqDirectory);
    }

    public void show(Path dir) {
        show(dir, 0);
    }

    public void show(Path dir, int numOfCalls) {
        String tab = "\t".repeat(numOfCalls);
        if (Files.isDirectory(dir)) {

            try (Stream<Path> dirContents = Files.list(dir)) {
                dirContents.forEach(subdir -> {
                    Boolean isDir = Files.isDirectory(subdir);
                    System.out.printf("%s> %s es un %s \n", tab, subdir.getFileName(), isDir ? "directorio" : "fichero");

                    show(subdir, numOfCalls + 1);
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
