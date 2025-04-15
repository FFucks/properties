import java.util.Scanner;

public class PocProperties {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String KEY = "key";
    private static final String VALUE = "value";

    public static void main(String[] args) {

        PropertiesAction propertiesAction = new PropertiesAction();
        propertiesAction.verifyProperties();

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
                    propertiesAction.createProperties(getKeyValue(KEY), getKeyValue(VALUE));
                    break;
                case 2:
                    System.setProperty(getKeyValue(KEY), getKeyValue(VALUE));
                    System.out.println("\nSystem property saved");
                    break;
                case 3:
                    propertiesAction.deleteProperties(getKeyValue(KEY));
                    break;
                case 4:
                    String keyProp = getKeyValue(KEY);
                    System.clearProperty(keyProp);
                    break;
                case 5:
                    propertiesAction.printProperties();
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
