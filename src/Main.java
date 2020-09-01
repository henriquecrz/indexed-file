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
            String input = Constant.EMPTY_STRING;

            System.out.println("People amount: " + binaryFile.readInt() + "\n");

            do {
                menu();

                input = keyboard.nextLine().trim().toLowerCase();

                try {
                    long start = System.currentTimeMillis();
                    Person person = Person.getPerson(binaryFile, Integer.parseInt(input));
                    long end = System.currentTimeMillis();

                    System.out.println(person != null ? person : Constant.PERSON_NOT_FOUND_MESSAGE);
                    System.out.println(String.format("Search time: %.3f seconds.", (end - start) / 1000.0));

                    start = System.currentTimeMillis();
                    person = Person.getPerson(binaryFile, Person.get(Integer.parseInt(input)));
                    end = System.currentTimeMillis();

                    System.out.println(person != null ? person : Constant.PERSON_NOT_FOUND_MESSAGE);
                    System.out.println(String.format("Search time: %.3f seconds.", (end - start) / 1000.0));
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
