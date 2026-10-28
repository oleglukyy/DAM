package ejercicios1_directorios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ejercicio1 {
    public static void main(String[] args) {
        new Ejercicio1().show();
    }

    public void show() {

        Path relativePath = Paths.get("subdirectory", "file.txt");

        // Comprobamos si el archivo existe antes de leer atributos
        if (!Files.exists(relativePath)) {
            System.out.println("El archivo no existe en: " + relativePath);
        }

        try {

            String name = relativePath.getFileName().toString();

            boolean isExecutable = Files.isExecutable(relativePath);

            boolean isHidden = Files.isHidden(relativePath);

            String relativePathString = relativePath.toString();

            String absolutePath = relativePath.toAbsolutePath().normalize().toString();

            long size = Files.size(relativePath);

            // Mostrar resultados
            System.out.println("Nombre:         " + name);
            System.out.println("Ejecutable:     " + (isExecutable ? "Sí" : "No"));
            System.out.println("Oculto:         " + (isHidden ? "Sí" : "No"));
            System.out.println("Ruta relativa:  " + relativePathString);
            System.out.println("Ruta absoluta:  " + absolutePath);
            System.out.println("Tamaño:         " + size + " bytes");

        } catch (IOException e) {
            System.err.println("Error al acceder a los atributos del archivo: " + e.getMessage());
        }
    }


}
