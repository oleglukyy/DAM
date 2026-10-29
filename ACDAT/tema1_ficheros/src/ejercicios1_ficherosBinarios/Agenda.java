package ejercicios1_ficherosBinarios;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Agenda {
    public static void main(String[] args) {
        new Agenda().run();
    }

    public void run() {

        Path file = getBinaryFile();
        writeData(file);
        readData(file);
    }

    private void readData(Path file) {

        StringBuilder text = new StringBuilder();


        try (DataInputStream reader = new DataInputStream(new FileInputStream(file.toFile()))) {
            while (true) {
                System.out.println("-----------------------------------------------");
                System.out.println(reader.readUTF());
                System.out.println(reader.readUTF());
                System.out.println(reader.readUTF());
                System.out.println(reader.readInt());
                System.out.println(reader.readUTF());
                System.out.println(reader.readBoolean());
                System.out.println(reader.readDouble());
            }

        } catch (EOFException e) {
            System.out.println("No hay más contactos existentes");
        } catch (FileNotFoundException e) {
            System.err.printf("El fichero no existe: %s", e.getMessage());
        } catch (IOException e) {
            System.err.printf("Error de entrada/salida: %s", e.getMessage());
        }

    }

    private void writeData(Path file) {

        String[][] data = {
                {"Eva", "+33567765", "Mi corazon", "11200", "13-01-2007", "true", "200.0"},
                {"Adam", "+33567765", "Mi corazon", "11200", "13-01-2007", "true", "200.0"},
                {"Pepe", "+33567765", "Mi corazon", "11200", "13-01-2007", "true", "200.0"}
        };

        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(file.toFile()))) {
            for (String[] contact : data) {
                writer.writeUTF(contact[0]);
                writer.writeUTF(contact[1]);
                writer.writeUTF(contact[2]);
                writer.writeInt(Integer.parseInt(contact[3]));
                writer.writeUTF(contact[4]);
                writer.writeBoolean(Boolean.parseBoolean(contact[5]));
                writer.writeDouble(Double.parseDouble(contact[6]));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Path getBinaryFile() {

        return Paths.get("files", "binaryFile.dat").toAbsolutePath();


    }


}
