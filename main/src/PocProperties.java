import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class PocProperties {

    private static final String CONFIGURATION_PROPERTIES = "configuration.properties";
    private static final String SYSTEM_PROPERTIES = "System";
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_HEADER = "Configuration Properties";
    private static final String KEY = "key";
    private static final String VALUE = "value";

    public static void main(String[] args) {


        Properties prop = new Properties();

        File file = new File(CONFIGURATION_PROPERTIES);

        if (!file.exists()) {
            PropertiesAction.storeProperties(prop, CONFIGURATION_PROPERTIES, "Configuration Properties");
        }

        while (true) {
            showMenu();
            System.out.print("Type an option: ");
            int option = -1;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Option. Inform a number");
                continue;
            }

            switch (option) {
                case 1:
                    PropertiesAction.createProperties(getKeyValue(KEY), getKeyValue(VALUE), CONFIGURATION_PROPERTIES, FILE_HEADER);
                    break;
                case 2:
                    PropertiesAction.createProperties(getKeyValue(KEY), getKeyValue(VALUE), SYSTEM_PROPERTIES, FILE_HEADER);
                    break;
                case 3:
                    PropertiesAction.deleteProperties(CONFIGURATION_PROPERTIES, getKeyValue(KEY));
                    break;
                case 4:
                    String keyProp = getKeyValue(KEY);
                    System.clearProperty(keyProp);
                    break;
                case 5:
                    PropertiesAction.printProperties(CONFIGURATION_PROPERTIES);
                    break;
                case 6:
                    System.getProperties().forEach((key, value) -> System.out.println(key + "=" + value));
                    break;
                case 0:
                    System.out.println("Exiting the program");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid Option");
            }
            System.out.println();
        }
    }

    private static String getKeyValue(String keyVal) {
        System.out.println("Inform a " + keyVal);
        return scanner.nextLine();
    }


    private static void showMenu() {
        System.out.println("----- Menu Properties -----");
        System.out.println("1 - POST Configuration Properties");
        System.out.println("2 - POST System Properties");
        System.out.println("3 - DEL Configuration Properties");
        System.out.println("4 - DEL System Properties");
        System.out.println("5 - GET List Configuration Properties");
        System.out.println("6 - GET List System Properties");
        System.out.println("0 - Exit");
        System.out.println("---------------------------");
    }

}
