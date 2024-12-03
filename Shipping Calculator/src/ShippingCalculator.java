import helpers.OptionMenu;
import java.util.Scanner;

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

        Shipment shipment = null;
        switch (menuChoice) {
            case "1":
                shipment = ShipmentBuilder.buildShipment();
                if (shipment != null) {
                    ShipmentBuilder.displayShipmentSummary(shipment);
                    System.out.println();
                }
                else {
                    System.out.println("There is no shipment to display!");
                }
                String modifyMenuChoice;
                do {
                    // Prompt the user if they would like to modify the shipment
                    System.out.println("Would you like to modify the shipment?");
                    OptionMenu modifyMenu = new OptionMenu();
                    String[][] modifyMenuOptions = {{"1", "Yes"}, {"2", "No"}};
                    modifyMenu.addAllMenuOptions(modifyMenuOptions);
                    System.out.printf("\n%s\n", modifyMenu.withTitle("Modify Shipment").menuAsString());
                    modifyMenuChoice = modifyMenu.promptForChoice();
                    modifyMenu.isValidOption(modifyMenuChoice);

                    // Proceed only if the user chooses "1" (Yes)
                    if (modifyMenuChoice.equals("1")) {
                        System.out.println("Which part of the shipment would you like to modify?");
                        OptionMenu shipModifyMenu = new OptionMenu();
                        String[][] shipModifyMenuOptions = {{"1", "Label"}, {"2", "Packages"}, {"3", "Destination"}, {"4", "Destination"}, {"5", "Priority"}};
                        shipModifyMenu.addAllMenuOptions(shipModifyMenuOptions);
                        System.out.printf("\n%s\n", shipModifyMenu.withTitle("Modification Choice").menuAsString());
                        String shipModifyMenuChoice = shipModifyMenu.promptForChoice();
                        shipModifyMenu.isValidOption(shipModifyMenuChoice);

                        // Perform the appropriate modification based on user's choice
                        switch (shipModifyMenuChoice) {
                            case "1":
                                System.out.print("Please enter a label for the shipment: ");
                                String shipLabel = keyboard.nextLine();
                                ShipmentBuilder.createShipment(shipment).setName(shipLabel);
                            case "2":
                                System.out.println("Please select the package you wish to modify");
                                OptionMenu shipModifyPackageMenu = new OptionMenu();
                                shipModifyPackageMenu.addAllMenuOptions(shipModifyMenuOptions);
                                // Call the method to modify the package
                                ShipmentBuilder.displayShipmentSummary(shipment);
                                break;
                            case "3":
                                ShipmentBuilder.assignShipmentOrigin(shipment);
                                ShipmentBuilder.displayShipmentSummary(shipment);
                                break;
                            case "4":
                                ShipmentBuilder.assignShipmentDestination(shipment);
                                ShipmentBuilder.displayShipmentSummary(shipment);
                                break;
                            case "5":
                                ShipmentBuilder.assignShipmentPriority(shipment);
                                ShipmentBuilder.displayShipmentSummary(shipment);
                                break;
                            default:
                                System.out.println("Invalid option selected.");
                                break;
                        }
                    }
                }
                while (modifyMenuChoice.equals("1"));  // Continue looping if user selects "1" (Yes)
                System.out.println("No modifications required.");
                ShipmentBuilder.displayShipmentCost(shipment);
                System.out.println();
                endProgram();
                break;
            case "2":
                endProgram();

        }

    }

    private static void endProgram() {
        System.out.println("Thank you for using SuperShipper! Have a great day!");
        System.exit(0);
    }

    //TODO Create Functionality for Retrieve+Update of a package...
    //TODO Create Functionality for Retrieve+Update of the shipment...
    //TODO Option to Delete Package
    //TODO Option to Delete Shipment (exit)


}
