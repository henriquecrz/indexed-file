import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class Person {
    private int _rg;
    private String _name;
    private String _birthday;
    private static Map<Integer, Long> _addresses = new HashMap<Integer, Long>();

    public Person(int rg, String name, String birthday) {
        this._rg = rg;
        this._name = name;
        this._birthday = birthday;
    }

    public Person(String[] value) throws NumberFormatException {
        this._rg = Integer.parseInt(value[Index.RG]);
        this._name = value[Index.NAME];
        this._birthday = value[Index.BIRTHDAY];
    }

    public int getRg() {
        return this._rg;
    }

    public String getName() {
        return this._name;
    }

    public String getBirthday() {
        return this._birthday;
    }

    public static void put(int rg, long address) {
        if (!_addresses.containsKey(rg)) {
            _addresses.put(rg, address);
        }
    }

    public static long get(int rg) {
        return _addresses.containsKey(rg) ? _addresses.get(rg) : null;
    }

    @Override
    public String toString() {
        return "\nPerson\n------\nRG: " + getRg() + "\nName: " + getName() + "\nBirthday: " + getBirthday() + "\n";
    }

    public void saveToFile(RandomAccessFile file) throws IOException {
        file.seek(file.length());
        file.writeInt(getRg());
        file.writeUTF(getName());
        file.writeUTF(getBirthday());
    }

    public static Person getPerson(RandomAccessFile file, int rgKey) {
        boolean isFileEnded = false;

        try {
            file.seek(Integer.BYTES);

            while (!isFileEnded) { // Improve flag
                int rg = file.readInt();
                String name = file.readUTF();
                String birthday = file.readUTF();

                if (rg == rgKey) {
                    return new Person(rg, name, birthday);
                }
            }
        } catch (IOException ex) {
            isFileEnded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Person getPerson(RandomAccessFile file, long pos) {
        try {
            file.seek(pos);

            int rg = file.readInt();
            String name = file.readUTF();
            String birthday = file.readUTF();

            return new Person(rg, name, birthday);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
