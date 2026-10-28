package ejercicios1_FicherosDeTexto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Ejercicio2 {
    public static void main(String[] args) {
        new Ejercicio2().show();
    }

    public void show() {
        Path source = Paths.get("C:\\Users\\lukia\\IdeaProjects\\tema1_ficheros\\src\\ejercicios1_FicherosDeTexto\\Ejercicio2.java");
        Path destination = Paths.get("C:\\Users\\lukia\\Desktop");

        encrypt(source, destination, "abc");
        decrypt(destination.resolve("Encrypted" + source.getFileName()), destination, "abc");

    }

    public void encrypt(Path source, Path destination, String passKey) {
        if (passKey == null || passKey.isEmpty()) {
            throw new IllegalArgumentException("La clave no puede ser nula ni vacía.");
        }

        try {
            char[] key = passKey.toCharArray();
            char[] text = Files.readString(source).toCharArray();

            for (int i = 0, j = 0; i < text.length; i++, j++) {
                text[i] += key[j];
                if (j == key.length - 1) {
                    j = -1;
                }
            }
            Files.writeString(
                    destination.resolve("Encrypted" + source.getFileName()),
                    new String(text),
                    StandardOpenOption.CREATE
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void decrypt(Path source, Path destination, String passKey) {
        if (passKey == null || passKey.isEmpty()) {
            throw new IllegalArgumentException("La clave no puede ser nula ni vacía.");
        }

        try {
            char[] key = passKey.toCharArray();
            char[] text = Files.readString(source).toCharArray();

            for (int i = 0, j = 0; i < text.length; i++, j++) {
                text[i] -= key[j];
                if (j == key.length - 1) {
                    j = -1;
                }
            }
            Files.writeString(
                    destination.resolve("Un" + source.getFileName()),
                    new String(text),
                    StandardOpenOption.CREATE
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
