package ejercicios1_FicherosDeTexto;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class Ejercicio1 {

    public static void main(String[] args) {
        new Ejercicio1().show();
    }

    public void show() {
        Path source = Paths.get("C:\\Users\\lukia\\Desktop\\test\\Ejercicio2000.java");
        Path destination = Paths.get("C:\\Users\\lukia\\Desktop\\Ejercicio10.java");
        cp(source, destination, true);
    }

    public void cp(Path source, Path destination, boolean appended) {
        try {
            //DESTINO -> DIRECTORIO
            if (Files.isDirectory(destination)) {
                if (Files.isDirectory(source)) {
                    throw new IllegalArgumentException("El origen no puede ser un directorio si el destino es un directorio.");
                }

                //Copia el contenido del archivo origen al directorio destino y lee ARCHIVO destino
                Files.copy(source, destination.resolve(source.getFileName()));
                Files.readAllLines(destination.resolve(source.getFileName()))
                        .forEach(System.out::println);

            } else {
                //DESTINO -> ARCHIVO
                if (Files.isDirectory(source)) {
                    throw new IllegalArgumentException("El origen no puede ser un directorio si el destino es un fichero");
                } else if (!Files.exists(destination)) {
                    Files.readAllLines(source)
                            .forEach(line -> {
                                try {
                                    Files.writeString(
                                            destination,
                                            line + System.lineSeparator(),
                                            StandardOpenOption.APPEND,
                                            StandardOpenOption.CREATE
                                    );
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                } else {
                    if (appended) {
                        try {
                            Files.write(
                                    destination,
                                    Files.readString(source).getBytes(),
                                    StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        try {
                            Files.write(
                                    destination,
                                    Files.readString(source).getBytes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
