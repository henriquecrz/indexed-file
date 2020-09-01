import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Converter.toBinaryFile(Path.INPUT_FILE, Path.BINARY_FILE);

        Scanner keyboard = new Scanner(System.in);

        try {
            RandomAccessFile binaryFile = new RandomAccessFile(new File(Path.BINARY_FILE), Constant.ACCESS_MODE);

            System.out.println("People amount: " + binaryFile.readInt());

            String input = Constant.EMPTY_STRING;

            do {
                menu();

                input = keyboard.nextLine().trim().toLowerCase();

                try {
                    Person person = Person.getPerson(binaryFile, Integer.parseInt(input));

                    System.out.println(person != null ? person : Constant.PERSON_NOT_FOUND_MESSAGE); // toString() ?
                } catch (NumberFormatException e) {
                    System.out.println(input.equals(Command.QUIT) ? "Exiting..." : e.getMessage());
                }
            } while (!input.equals(Command.QUIT));

            binaryFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        keyboard.close();
    }

    public static void menu() {
        System.out.println("Seek a person");
        System.out.println("-------------");
        System.out.print("Type a RG or \"quit\" to exit: ");
    }
}
