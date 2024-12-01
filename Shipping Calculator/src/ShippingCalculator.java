import helpers.OptionMenu;

import java.util.EnumSet;
import java.util.Map;
import java.util.Scanner;

//PackageBuilder
//ShipmentBuilder

public class ShippingCalculator {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Welcome to SuperShipper - Steam Early Release - pre-v0.1\n" +
                "We will assist with calculating the cost of your shipment.");

        OptionMenu startMenu = new OptionMenu();
        String[][] menuOptions = {{"1","Create New Shipment"},{"2","Quit"}};
        startMenu.addAllMenuOptions(menuOptions);

        System.out.printf("\n%s\n", startMenu.withTitle("Main Menu").withDefault("1").menuAsString());
        String menuChoice = startMenu.promptForChoice();
        startMenu.isValidOption(menuChoice);

        if (menuChoice.equals("1")) {
            ShipmentBuilder.buildShipment();
        } else {
            System.exit(0);
        }
    }

    //Create Functionality for Retrieve+Update of a package...
    //Create Functionality for Retrieve+Update of the shipment...
    //Option to Delete Package
    //Option to Delete Shipment (exit)


}
