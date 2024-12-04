import helpers.OptionMenu;
import java.util.Scanner;

public class ShippingCalculator {
    public static void main(String[] args) {
        System.out.println("Welcome to SuperShipper - Steam Early Release - pre-v0.1\n" +
                "We will assist with calculating the cost of your shipment.");

        OptionMenu startMenu = new OptionMenu();
        String[][] menuOptions = {{"1","Create New Shipment"},{"2","Quit"}};
        startMenu.addAllMenuOptions(menuOptions);
        System.out.printf("\n%s\n", startMenu.withTitle("Main Menu").withDefault("1").menuAsString());
        String menuChoice = startMenu.promptForChoice();
        startMenu.isValidOption(menuChoice);

        Shipment shipment = null;
        switch (menuChoice) {
            case "1":
                shipment = ShipmentBuilder.buildShipment();
                if (shipment != null) {
                    ShipmentBuilder.displayShipmentSummary(shipment);
                    ShipmentBuilder.modifyShipment(shipment);
                } else {
                    System.out.println("There is no shipment to display!");
                }
                break;

            case "2":
                endProgram();
                break;
        }
    }

    private static void endProgram() {
        System.out.println("Thank you for using SuperShipper! Have a great day!");
        System.exit(0);
    }
}
