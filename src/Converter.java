import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Converter {
    public static void toBinaryFile(String inputFileName, String binaryFileName) {
        try {
            int counter = 0;
            File inputFile = new File(inputFileName);
            Scanner inputFileScanner = new Scanner(inputFile);
            File binaryFile = new File(binaryFileName);
            RandomAccessFile binaryFileScanner = new RandomAccessFile(binaryFile, Constant.ACCESS_MODE);
            binaryFileScanner.setLength(0);
            binaryFileScanner.writeInt(0);

            while (inputFileScanner.hasNextLine()) {
                String line = inputFileScanner.nextLine();
                String[] lineSplit = line.split(Constant.SEMICOLON);
                Person person = new Person(lineSplit);

                System.out.print(person.getRg() + " inserted in " + binaryFileScanner.getFilePointer() + " position.");
                person.saveToFile(binaryFileScanner);

                counter++;
            }

            inputFileScanner.close();
            binaryFileScanner.seek(0);
            binaryFileScanner.writeInt(counter);
            binaryFileScanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
