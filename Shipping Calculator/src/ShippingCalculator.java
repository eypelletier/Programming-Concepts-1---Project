import helpers.OptionMenu;
import java.util.Scanner;

public class ShippingCalculator {
    public static void main(String[] args) {
        System.out.println("Welcome to SuperShipper - Steam Early Release - pre-v0.1\n" +
                "We will assist with calculating the cost of your shipment.");

        OptionMenu startMenu = new OptionMenu();
        String startMenuChoice;
        String[][] startMenuOptions = {{"1", "Create New Shipment"}, {"2", "Quit"}};
        startMenu.addAllMenuOptions(startMenuOptions);
        boolean validChoice;
        do {

            System.out.printf("\n%s\n", startMenu.withTitle("Main Menu").withDefault("1").menuAsString());
            startMenuChoice = startMenu.promptForChoice();
            validChoice = startMenu.isValidOption(startMenuChoice);
        }
        while (!validChoice);

        Shipment shipment = null;
        switch (startMenuChoice) {
            case "1":
                shipment = ShipmentBuilder.buildShipment();
                ShipmentBuilder.modifyShipment(shipment);
                ShipmentBuilder.exportShipmentDetails(shipment);
                endProgram();
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
