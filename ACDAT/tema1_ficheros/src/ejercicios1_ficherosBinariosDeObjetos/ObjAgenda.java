package ejercicios1_ficherosBinariosDeObjetos;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ObjAgenda {

    Scanner sc = new Scanner(System.in);
    Path file = getFile();

    public static void main(String[] args) {
        new ObjAgenda().show();
    }

    public void show() {
        int option = getOption();
        switch (option) {
            case 1 -> readData();
            case 2 -> writeData(getContact(), false);
            case 3 -> writeData(getContact(), true);
        }
    }

    private Contact getContact() {
        System.out.println("Introduce calle");
        String name = sc.nextLine();

        System.out.println("Introduce numero tlf");
        String phoneNumber = sc.nextLine();

        System.out.println("Introduce calle");
        String adress = sc.nextLine();

        System.out.println("Introduce codigo postal");
        int pCode = sc.nextInt();

        System.out.println("Introduce cumpleaños");
        String birthDate = sc.nextLine();

        System.out.println("Introduce si debe deuda (true/false)");
        boolean hasDebt = sc.nextBoolean();

        double amount;
        if (hasDebt) {
            System.out.println("Introduce la cantidad");
            amount = sc.nextDouble();
        } else {
            amount = 0.0;
        }

        return new Contact(
                name, phoneNumber, adress, pCode, birthDate, hasDebt, amount
        );
    }

    private void writeData(Contact contact, boolean append) {
        try (FileOutputStream fos = new FileOutputStream(file.toFile(), append);
             ObjectOutputStream writer = new ObjectOutputStream(fos);) {
            writer.writeObject(contact);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readData() {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file.toFile()))) {

            while (true) {
                reader.readObject();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path getFile() {
        try {
            return Files.createFile(Paths.get("files", "binaryObject.dat").toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getOption() {
        return sc.nextInt();
    }
}
