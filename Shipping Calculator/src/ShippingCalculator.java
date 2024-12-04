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
                    modifyShipment(shipment);
                } else {
                    System.out.println("There is no shipment to display!");
                }
                break;

            case "2":
                endProgram();
                break;
        }
    }

    // Method to handle modifying the shipment
    private static void modifyShipment(Shipment shipment) {
        String modifyMenuChoice;
        do {
            System.out.println("Would you like to modify the shipment?");
            OptionMenu modifyMenu = new OptionMenu();
            String[][] modifyMenuOptions = {{"1", "Yes"}, {"2", "No"}};
            modifyMenu.addAllMenuOptions(modifyMenuOptions);
            System.out.printf("\n%s\n", modifyMenu.withTitle("Modify Shipment").menuAsString());
            modifyMenuChoice = modifyMenu.promptForChoice();
            modifyMenu.isValidOption(modifyMenuChoice);

            if (modifyMenuChoice.equals("1")) {
                modifyShipmentDetails(shipment);
            }
        } while (modifyMenuChoice.equals("1"));  // Continue looping if user selects "1" (Yes)

        System.out.println("No modifications requested.");
        ShipmentBuilder.displayShipmentCost(shipment);
        System.out.println();
        endProgram();
    }

    // Method to handle modifying specific shipment details
    private static void modifyShipmentDetails(Shipment shipment) {
        System.out.println("Which part of the shipment would you like to modify?");
        OptionMenu shipModifyMenu = new OptionMenu();
        String[][] shipModifyMenuOptions = {{"1", "Label"}, {"2", "Packages"}, {"3", "Origin"},
                {"4", "Destination"}, {"5", "Priority"}};
        shipModifyMenu.addAllMenuOptions(shipModifyMenuOptions);
        System.out.printf("\n%s\n", shipModifyMenu.withTitle("Modification Choice").menuAsString());
        String shipModifyMenuChoice = shipModifyMenu.promptForChoice();
        shipModifyMenu.isValidOption(shipModifyMenuChoice);

        switch (shipModifyMenuChoice) {
            case "1": ShipmentBuilder.assignShipmentLabel(shipment);
            break;
            case "2": ShipmentBuilder.modifyShipmentPackages(shipment);
            break;
            case "3": ShipmentBuilder.assignShipmentOrigin(shipment);
            break;
            case "4": ShipmentBuilder.assignShipmentDestination(shipment);
            break;
            case "5": ShipmentBuilder.assignShipmentPriority(shipment);
            break;
            default: System.out.println("Invalid option selected.");
            break;
        }

        // Re-display the shipment summary after modification
        ShipmentBuilder.displayShipmentSummary(shipment);
    }

    private static void endProgram() {
        System.out.println("Thank you for using SuperShipper! Have a great day!");
        System.exit(0);
    }
}
