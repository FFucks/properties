import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class PropertiesAction {

    private static final String SYSTEM_PROPERTIES = "System";

    public static Properties getSystemProperties() {
        Properties prop = System.getProperties();

        System.out.println(prop.stringPropertyNames() + "\n");

        System.out.println("--- System Properties ---");

        for (Map.Entry<Object, Object> entry : prop.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        return System.getProperties();
    }

    public static void createProperties(String key, String value, String fileName, String fileHeader) {
        if (fileName.equals(SYSTEM_PROPERTIES)) {
            System.setProperty(key, value);
            System.out.println("\nSystem property saved");
            return;
        }
        Properties prop = new Properties();
        prop.setProperty(key, value);
        storeProperties(prop, fileName, fileHeader);

        System.out.println("User information loaded");
    }

    public static void storeProperties(Properties prop, String fileName, String fileHeader) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            prop.store(fos, fileHeader);

            System.out.println("\nProperties stored on: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void updateProperties(String key, String value, String fileName, String fileHeader) {
        try {
            Properties prop = new Properties();
            loadProperties(prop, fileName);

            prop.setProperty(key, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties loadProperties(Properties prop, String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            prop.load(fis);

            return prop;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void deleteProperties(String fileName, String key) {
        Properties prop = new Properties();
        loadProperties(prop, fileName);
        prop.remove(key);
    }

    public static void printProperties(String fileName) {
        Properties prop = new Properties();
        loadProperties(prop, fileName);

        System.out.println("--- Configuration Properties ---");
        prop.forEach((key, value) -> System.out.println(key + "=" + value));
    }
}
