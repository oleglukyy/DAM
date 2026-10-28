package ejercicios1_directorios;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Ejercicio4 {
    // Muestra todos los archivos con extensión .java que se encuentren en un directorio y sus subdirectorios.

    public static void main(String[] args) {
        Path reqDirectory = Paths.get("C:\\Users\\lukia\\IdeaProjects\\tema1_ficheros");
        new Ejercicio4().show(reqDirectory);
    }

    public void show(Path dir) {

        try (Stream<Path> content = Files.walk(dir)) {

            content.forEach(file -> {
                if (Files.isRegularFile(file) && file.toString().endsWith(".java")) {
                    System.out.println(file);
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
