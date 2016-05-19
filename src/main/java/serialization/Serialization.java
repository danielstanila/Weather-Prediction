package serialization;

import java.io.*;

public class Serialization {

    private File file;

    public Serialization(File file) {
        this.file = file;
    }

    public <T> void write(T object) {
        try(FileOutputStream fo = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fo)) {
            os.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T read() {
        T object = null;
        try(FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi)) {
            object = (T) oi.readObject();
        }
        catch (FileNotFoundException e) {
            return null;
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
