import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

public class PropertiesAction {

    private static final String FILE_HEADER = "Configuration Properties";
    private static final String CONFIGURATION_PROPERTIES = "configuration.properties";
    private final ReentrantLock lock = new ReentrantLock();

    public void verifyProperties() {
        File file = new File(CONFIGURATION_PROPERTIES);

        if (!file.exists()) {
            this.storeProperties(new Properties());
        }
    }

    public void createProperties(String key, String value) {
        lock.lock();
        try {
            Properties prop = new Properties();
            prop.setProperty(key, value);
            this.storeProperties(prop);
            System.out.println("User information loaded");
        } finally {
            lock.unlock();
        }
    }

    public void storeProperties(Properties prop) {
        try {
            FileOutputStream fos = new FileOutputStream(CONFIGURATION_PROPERTIES);
            prop.store(fos, FILE_HEADER);

            System.out.println("\nProperties stored on: " + CONFIGURATION_PROPERTIES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties loadProperties(String fileName) {
        try {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream(fileName);
            prop.load(fis);

            return prop;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void deleteProperties(String key) {
        lock.lock();
        try {
            Properties prop = loadProperties(CONFIGURATION_PROPERTIES);
            prop.remove(key);

            this.storeProperties(prop);
        } finally {
            lock.unlock();
        }
    }

    public void printProperties() {
        lock.lock();
        try {
            Properties prop = loadProperties(CONFIGURATION_PROPERTIES);

            System.out.println("--- Configuration Properties ---");
            prop.forEach((key, value) -> System.out.println(key + "=" + value));

        } finally {
            lock.unlock();
        }
    }

}
