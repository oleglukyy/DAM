package ejercicios1_FicherosDeTexto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Ejercicio3 {
    /*
Realiza este ejercicio utilizando las clases BufferedReader y BufferedWriter: concatena todos tus ficheros
.java de este boletín en uno solo utilizando código de programación.
A continuación, lee dicho fichero y genera dos ficheros:

Fichero donde estén las declaraciones realizadas en los paréntesis del try-with-resources, cada una en una línea y terminadas con ;. Ejemplo:


Stream<Path> directoryContents;
BufferedReader reader;
BufferedWriter writer;
Fichero donde estén las declaraciones de las distintas excepciones tratadas, cada una en una línea, terminadas con ; y sin repeticiones, es decir, la excepción que aparezca varias veces que solamente aparezca una vez en el fichero:


FileNotFoundException e;
IOException e;
FileAlreadyExistsException e;
NoSuchFileException e;
NotDirectoryException e;
     */

    public static void main(String[] args) {
        new Ejercicio3().show();
    }

    public void show() {
        Path desktopPath = Paths.get(System.getProperty("user.home") + File.separator+"Desktop");
        Path combined = desktopPath.resolve("combined.txt");
        joinJavaFiles(combined);
        filterJavaFile(combined, desktopPath);
    }

    public List<Path> getJavaFiles() {
        Path currentDir = Paths.get("src");

        try (Stream<Path> directoryContents = Files.walk(currentDir)) {
            return directoryContents
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".java"))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void joinJavaFiles(Path destination) {

        List<Path> toJoin = getJavaFiles();
        try (BufferedWriter writer = Files.newBufferedWriter(
                destination,
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE
        );) {
            for (Path source : toJoin) {
                try (BufferedReader reader = Files.newBufferedReader(source);) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    System.err.printf("Error de entrada/salida: %s", e.getMessage());
                }

            }
        } catch (IOException e) {
            System.err.printf("El fichero no existe: %s", e.getMessage());
        }

    }

    public void filterJavaFile(Path source, Path destination) {

        Pattern tryPattern=Pattern.compile("\\btry\\s*\\(([^)]+)\\)");
        Pattern catchPattern=Pattern.compile("\\bcatch\\s*\\(([^)]+)\\)");
        Matcher tryMatcher;
        Matcher catchMatcher;
        Set<String> catchExceptions= new LinkedHashSet<>();

        try (BufferedReader reader = Files.newBufferedReader(source);
             BufferedWriter writerTry = Files.newBufferedWriter(
                     destination.resolve("try.txt"),
                     StandardOpenOption.APPEND,
                     StandardOpenOption.CREATE
             );
             BufferedWriter writerCatch = Files.newBufferedWriter(
                     destination.resolve("catch.txt"),
                     StandardOpenOption.APPEND,
                     StandardOpenOption.CREATE
             );
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                 tryMatcher=tryPattern.matcher(line);

                while (tryMatcher.find()){
                    String declarations =tryMatcher.group(1).trim();
                    // Pueden existir varios recursos separados por ';'
                    for (String resource : declarations.split(";")) {
                        String clean = resource.trim();
                        if (clean.isEmpty()) continue;

                        // Quedarse con "Tipo variable" antes del '='
                        String typeAndName = clean.split("=")[0].trim();
                        writerTry.write(typeAndName + ";");
                        writerTry.newLine();
                    }
                }

                catchMatcher=catchPattern.matcher(line);

                while (catchMatcher.find()){
                    String catchContent = catchMatcher.group(1).trim();
                    // Extraer el nombre de la variable (último token)
                    int lastSpace = catchContent.lastIndexOf(' ');
                    String varName = (lastSpace != -1) ? catchContent.substring(lastSpace + 1).trim() : "e";
                    String typesPart = (lastSpace != -1) ? catchContent.substring(0, lastSpace).trim() : catchContent;

                    // Manejar multicatch (ej. "IOException | SQLException")
                    for (String exType : typesPart.split("\\|")) {
                        String cleanType = exType.trim();
                        if (!cleanType.isEmpty()) {
                            catchExceptions.add(cleanType + " " + varName + ";");
                        }
                    }
                }


            }
            for (String exDecl : catchExceptions) {
                writerCatch.write(exDecl);
                writerCatch.newLine();
            }
        } catch (FileNotFoundException e) {
            System.err.printf("El fichero no existe: %s", e.getMessage());
        } catch (Exception e) {
            System.err.printf("Error de entrada/salida: %s", e.getMessage());
        }

    }

}
