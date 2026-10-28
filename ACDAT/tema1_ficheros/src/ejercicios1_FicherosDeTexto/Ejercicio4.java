package ejercicios1_FicherosDeTexto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Ejercicio4 {

    /*
    Realiza un programa que dado un fichero de texto, se copie en tres ficheros diferentes de tal manera que
    copie en el primer fichero los primeros 5 caracteres, en el segundo, los 5 siguientes y en el tercero los
    5 siguientes, y así sucesivamente hasta copiar todo el fichero. Utilizar lectura sin desplazamiento y
    escrituras con desplazamiento de un buffer de 15 caracteres. Después, hacer justamente lo contrario.
    Dados los 3 ficheros, construir uno como el fichero original. Utilizar ahora lecturas con desplazamiento
    y escritura sin desplazamiento de un buffer de 15 caracteres. Comprobar por código que ambos son iguales.
    En el caso de la última lectura/escritura, si no se llena el buffer, utilizar el desplazamiento para no dejar basura.
     */


    public static void main(String[] args) {

        new Ejercicio4().show();
    }

    public void show() {
        System.out.println(" ");
        encode(Paths.get("C:\\Users\\oleg\\Desktop\\ACCDAT-main\\ACCDAT-main\\tema1_ficheros\\src\\ejercicios1_FicherosDeTexto\\Ejercicio2.java"));
        deencode(Paths.get("C:\\Users\\oleg\\Desktop\\file1.txt"),
                Paths.get("C:\\Users\\oleg\\Desktop\\file2.txt"),
                Paths.get("C:\\Users\\oleg\\Desktop\\file3.txt")
        );
    }

    public void encode(Path source) {


        char[] cbuf = new char[15];

        Path desktopPath = Paths.get(System.getProperty("user.home") + File.separator + "Desktop");
        try (BufferedReader reader = Files.newBufferedReader(source);
             BufferedWriter writerF1 = Files.newBufferedWriter(
                     desktopPath.resolve("file1.txt"),
                     StandardOpenOption.APPEND,
                     StandardOpenOption.CREATE
             );
             BufferedWriter writerF2 = Files.newBufferedWriter(
                     desktopPath.resolve("file2.txt"),
                     StandardOpenOption.APPEND,
                     StandardOpenOption.CREATE
             );
             BufferedWriter writerF3 = Files.newBufferedWriter(
                     desktopPath.resolve("file3.txt"),
                     StandardOpenOption.APPEND,
                     StandardOpenOption.CREATE
             );
        ) {


            int charInt;

            int countIndex = 0;
            while ((charInt = reader.read()) != -1) {

                cbuf[countIndex] = (char) charInt;
                countIndex++;
                if (countIndex == 15) {

                    writerF1.write(cbuf, 0, 5);
                    writerF2.write(cbuf, 5, 5);
                    writerF3.write(cbuf, 10, 5);

                    countIndex = 0;

                }
            }


        } catch (IOException e) {

            System.out.println(e);


        }
    }

    public void deencode(Path file1, Path file2, Path file3) {
        char[] cbuf = new char[15];

        Path desktopPath = Paths.get(System.getProperty("user.home") + File.separator + "Desktop");
        try (BufferedWriter writer = Files.newBufferedWriter(
                desktopPath.resolve("joinedFile.txt"),
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE
        );
             BufferedReader readerF1 = Files.newBufferedReader(file1);
             BufferedReader readerF2 = Files.newBufferedReader(file2);
             BufferedReader readerF3 = Files.newBufferedReader(file3);
        ) {


            int n1, n2, n3;
            BufferedReader[] readers = {readerF1, readerF2, readerF3};
            boolean finished = false;
            while (!finished) {

                n1 = readerF1.read(cbuf, 0, 5);
                n2 = readerF2.read(cbuf, 5, 5);
                n3 = readerF3.read(cbuf, 10, 5);
                if (n1 == -1 && n2 == -1 && n3 == -1) {
                    finished = true;
                }
                writer.write(cbuf);

            }


        } catch (IOException e) {

            System.out.println(e);


        }
    }

}
