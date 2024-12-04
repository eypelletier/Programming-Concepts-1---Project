import helpers.OptionMenu;

import java.util.EnumSet;
import java.util.Map;
import java.util.Scanner;

public class ShipmentBuilder {
    public static Shipment buildShipment() {
        Shipment ship = createShipment(new Shipment());
        System.out.println("---DEBUG---");
        dummyValidation(ship);

        return ship;
    }

    public static void dummyValidation(Shipment ship) {
        Map<Package, EnumSet<Shipment.PackageProblems>> rejectedPackages = ship.getRejectedPackages();
        System.out.println("Count of problematic packages: " + rejectedPackages.size());

        for (var entry : rejectedPackages.entrySet()) {

            EnumSet<Shipment.PackageProblems> packageProblems = entry.getValue();
            System.out.println("Package: " + entry.getKey().getLabel());
            if (packageProblems.contains(Shipment.PackageProblems.GOODS_RESTRICTED_FOR_MODALITY)) {
                System.out.println("Oh my god, we can't carry this package it is TOO dangerous!");
                //dummyModifyPackage(entry.getKey());
            }
        }
    }

    public static Shipment createShipment(Shipment ship) {
        configureShipment(ship);
        return ship;
    }

    public static void configureShipment(Shipment ship) {
        assignShipmentLabel(ship);
        createShipmentPackages(ship);
        assignShipmentOrigin(ship);
        assignShipmentDestination(ship);
        assignShipmentModality(ship);
        assignShipmentPriority(ship);
    }

    public static void assignShipmentLabel(Shipment ship) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Please enter a label for the shipment: ");
        String shipLabel = keyboard.nextLine();
        ship.setName(shipLabel);
    }

    public static void createShipmentPackages(Shipment ship) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("How many packages would you like to ship: ");
        int numPackages = keyboard.nextInt();
        keyboard.nextLine();

        for (int i = 0; i < numPackages; i++) {
            System.out.println("---- Package #" + (i + 1) + " ----");
            Package newPackage = PackageBuilder.createPackage();
            ship.addToPackages(newPackage);
        }
    }

    public static void assignShipmentOrigin(Shipment ship) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please select the origin of the shipment:");
        OptionMenu originMenu = new OptionMenu();
        originMenu.addMenuOption("1", "Montreal")
                .addMenuOption("2", "Toronto")
                .addMenuOption("3", "Vancouver");
        System.out.printf("\n%s\n", originMenu.menuAsString(true));
        System.out.print("Choice: ");
        String originMenuChoice = keyboard.nextLine();
        originMenu.isValidOption(originMenuChoice);
        switch (originMenuChoice) {
            case "1":
                ship.setOrigin(City.MONTREAL);
                break;
            case "2":
                ship.setOrigin(City.TORONTO);
                break;
            case "3":
                ship.setOrigin(City.VANCOUVER);
                break;
        }
    }

    public static void assignShipmentDestination(Shipment ship) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please select the destination for the shipment:");
        OptionMenu destinationMenu = new OptionMenu();
        destinationMenu.addMenuOption("1", "Montreal")
                .addMenuOption("2", "Toronto")
                .addMenuOption("3", "Vancouver");
        System.out.printf("\n%s\n", destinationMenu.menuAsString(true));
        System.out.print("Choice: ");
        String destinationMenuChoice = keyboard.nextLine();
        destinationMenu.isValidOption(destinationMenuChoice);
        switch (destinationMenuChoice) {
            case "1":
                ship.setDestination(City.MONTREAL);
                break;
            case "2":
                ship.setDestination(City.TORONTO);
                break;
            case "3":
                ship.setDestination(City.VANCOUVER);
                break;
        }
    }

    public static void assignShipmentModality(Shipment ship) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please select the method of transportation:");

        OptionMenu transportationMenu = new OptionMenu();
        transportationMenu.addMenuOption("1", "Truck")
                .addMenuOption("2", "Rail")
                .addMenuOption("3", "Sea")
                .addMenuOption("4", "Air");

        System.out.printf("\n%s\n", transportationMenu.menuAsString(true));
        System.out.print("Choice: ");
        String transportationMenuChoice = keyboard.nextLine();
        transportationMenu.isValidOption(transportationMenuChoice);
        switch (transportationMenuChoice) {
            case "1":
                ship.setShippingMethod(DeliveryModality.TRUCK);
                break;
            case "2":
                ship.setShippingMethod(DeliveryModality.RAIL);
                break;
            case "3":
                ship.setShippingMethod(DeliveryModality.SEA);
                break;
            case "4":
                ship.setShippingMethod(DeliveryModality.AIR);
                break;
        }
    }

    static public void assignShipmentPriority(Shipment ship) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please select a priority for the shipment:");
        OptionMenu priorityMenu = new OptionMenu();
        priorityMenu.addMenuOption("1", "Standard")
                .addMenuOption("2", "Express");
        System.out.printf("\n%s\n", priorityMenu.menuAsString(true));
        System.out.print("Choice: ");
        String priorityMenuChoice = keyboard.nextLine();
        priorityMenu.isValidOption(priorityMenuChoice);
        switch (priorityMenuChoice) {
            case "1":
                ship.setShippingSpeed(DeliveryStandard.STANDARD);
                break;
            case "2":
                ship.setShippingSpeed(DeliveryStandard.EXPRESS);
                break;
        }
    }

    public static void displayShipmentSummary(Shipment ship) {
        // Display shipment summary
        System.out.println("\n--- Shipment Summary ---");
        System.out.println("Shipment Label: " + ship.getName());
        System.out.println("Origin: " + ship.getOrigin());
        System.out.println("Destination: " + ship.getDestination());
        System.out.println("Shipping Method: " + ship.getShippingMethod());
        System.out.println("Shipping Speed: " + ship.getShippingSpeed());

        // Display package information
        System.out.println("\n--- Packages ---");
        for (Package pkg : ship.getPackages()) {
            System.out.println("Package Label: " + pkg.getLabel());
            System.out.println("Goods Category: " + pkg.getGoodsClassification());
            System.out.println("Dimensions (HxWxL): " + pkg.getHeight() + " x " + pkg.getWidth() + " x " + pkg.getLength() + " cm");
            System.out.println("Weight: " + pkg.getWeight() + " kg");
            System.out.println("-------------------");
        }
        //TODO Generate and store the tracking number for the shipment.
        System.out.println("Tracking number: TRK1234567890");
    }

    public static void displayShipmentCost(Shipment ship) {
        System.out.println("\n--- Shipment Cost ---");
        System.out.println("Base Shipping Rate: " + ship.getBaseRate());
        System.out.println("Shipment Weight Surcharge: " + ship.getWeightSurcharge());
        System.out.println("Shipment Method Surcharge: " + ship.getModalitySurcharge());
        System.out.println("Shipment Priority Surcharge:  " + ship.getPrioritySurcharge());
        System.out.println("-------------------");
        System.out.println("Total cost for shipment: " + ship.calculateShippingCost());

    }

    public static void modifyShipmentPackages(Shipment ship) {
        // Display all packages in the shipment
        System.out.println("--- Packages in Shipment ---");
        int packageIndex = 1;
        for (Package pkg : ship.getPackages()) {
            System.out.println(packageIndex + ". Package Label: " + pkg.getLabel());
            System.out.println("   Goods Category: " + pkg.getGoodsClassification());
            System.out.println("   Dimensions (HxWxL): " + pkg.getHeight() + " x " + pkg.getWidth() + " x " + pkg.getLength() + " cm");
            System.out.println("   Weight: " + pkg.getWeight() + " kg");
            packageIndex++;
        }

        // Get the package the user wishes to modify
        Scanner keyboard = new Scanner(System.in);
        int packageCount = ship.getPackages().size();
        int packageChoice = 0;
        boolean validUserChoice = false;

        while (!validUserChoice) {
            System.out.print("Please enter the number of the package you would like to modify: ");
            packageChoice = Integer.parseInt(keyboard.nextLine());

            validUserChoice = (packageChoice >= 1) && (packageChoice <= packageCount);
            if (!validUserChoice) System.out.println("Invalid package choice. Please try again.");
        }

        // Retrieve the selected package
        Package selectedPackage = ship.getPackageByIndex(packageChoice - 1);

        // Allow user to modify package attributes
        PackageBuilder.modifyPackage(selectedPackage);
    }

    public static void deleteShipment(Shipment ship) {
        Scanner input = new Scanner(System.in);
        System.out.print("Would you like to delete this shipment? (y/n): ");
        OptionMenu deleteMenu = new OptionMenu();
        String[][] menuOptions = {{"1", "Yes"}, {"2", "No"}};
        deleteMenu.addAllMenuOptions(menuOptions);

        System.out.printf("\n%s\n", deleteMenu.withTitle("Delete Shipment?").withDefault("1").menuAsString());
        String menuChoice = deleteMenu.promptForChoice();
        deleteMenu.isValidOption(menuChoice);

        if (menuChoice.equals("1")) {
            ship = null;
        } else {
            System.exit(0);
        }
    }

    // Method to handle modifying the shipment
    public static void modifyShipment(Shipment shipment) {
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
        displayShipmentCost(shipment);
        System.out.println();
        //endProgram();
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
            case "1":
                assignShipmentLabel(shipment);
                break;
            case "2":
                modifyShipmentPackages(shipment);
                break;
            case "3":
                assignShipmentOrigin(shipment);
                break;
            case "4":
                assignShipmentDestination(shipment);
                break;
            case "5":
                assignShipmentPriority(shipment);
                break;
            default:
                System.out.println("Invalid option selected.");
                break;
        }

        // Re-display the shipment summary after modification
        displayShipmentSummary(shipment);
    }
}
