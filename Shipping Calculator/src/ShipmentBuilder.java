import helpers.OptionMenu;
import helpers.FileUtils;

import java.util.*;
import java.time.LocalDate;


public class ShipmentBuilder {
    private static Map<Package,String> packageProblemNotes = new HashMap<>();

    public static Shipment buildShipment() {
        Shipment ship = createShipment(new Shipment());
        return ship;
    }

    public static boolean validateShipment(Shipment ship) {
        Scanner keyboard = new Scanner(System.in);

        Map<Package, EnumSet<Shipment.PackageProblems>> rejectedPackages = ship.getRejectedPackages();
        System.out.println("\nPerforming final shipment check...");
        boolean shipmentCleared = false;

        int problemPackagesCount = rejectedPackages.size();
        if (problemPackagesCount > 0) {
            System.out.println("\n\n\033[91m-=- WARNING! PROBLEMATIC PACKAGES DETECTED -=-\033[39m");
            System.out.println("Your shipment cannot be processed until all problems have been resolved.");
            System.out.printf("Count of problematic packages: %s\n",problemPackagesCount);

            //Keep track of general problems across problematic packages
            EnumSet<Shipment.PackageProblems> generalProblems = EnumSet.noneOf(Shipment.PackageProblems.class);

            for (var entry : rejectedPackages.entrySet()) {
                EnumSet<Shipment.PackageProblems> packageProblems = entry.getValue();
                System.out.println("[x] Package: " + entry.getKey().getLabel());
                reportPackageProblemDetails(packageProblems);

                if (packageProblems.contains(Shipment.PackageProblems.MISMATCH_MODALITY)) generalProblems.add(Shipment.PackageProblems.MISMATCH_MODALITY);
                if (packageProblems.contains(Shipment.PackageProblems.MISMATCH_STANDARD)) generalProblems.add(Shipment.PackageProblems.MISMATCH_STANDARD);

            }
            System.out.println("\n\033[91m Shipping errors must be corrected. \033[39m\n");
            System.out.println("<press enter to continue>");
            keyboard.nextLine();
        } else {
            System.out.println("Your shipment meets all requirements.");
            shipmentCleared = true;

        }
        return shipmentCleared;
    }

    private static void evaluatePackageConflicts(Shipment ship) {
        Map<Package, EnumSet<Shipment.PackageProblems>> rejectedPackages = ship.getRejectedPackages();

        //Clear any existing packageProblemNotes
        packageProblemNotes.clear();

        //Re-populate package problem notes
        for (var entry : rejectedPackages.entrySet()) {
            EnumSet<Shipment.PackageProblems> packageProblems = entry.getValue();
            Package problemPackage = entry.getKey();
            String problemNotes = itemizePackageProblems(packageProblems);
            packageProblemNotes.put(problemPackage, problemNotes);
        }
    }

    private static String itemizePackageProblems(EnumSet<Shipment.PackageProblems> packageProblems){
        StringBuilder problemListString = new StringBuilder();
        problemListString.append("\033[1m[Conflicts Detected]\033[22m\n");
        //Error messages for the given package
        if (packageProblems.contains(Shipment.PackageProblems.GOODS_RESTRICTED_FOR_MODALITY)) {
            problemListString.append("\033[1mTransport Method:\033[22m Restricted goods category.\n");
        }

        if (packageProblems.contains(Shipment.PackageProblems.TOO_HEAVY_FOR_MODALITY)){
            problemListString.append("\033[1mTransport Method:\033[22m Too heavy.\n");
        }

        if (packageProblems.contains(Shipment.PackageProblems.TOO_LARGE_FOR_MODALITY)){
            problemListString.append("\033[1mTransport Method:\033[22m Too large.\n");
        }

        //Error Messages related to shipment standard
        if (packageProblems.contains(Shipment.PackageProblems.TOO_HEAVY_FOR_STANDARD)){
            problemListString.append("\033[1mStandard:\033[22m Too heavy.\n");
        }

        if (packageProblems.contains(Shipment.PackageProblems.TOO_LARGE_FOR_STANDARD)){
            problemListString.append("\033[1mStandard:\033[22m Too large.\n");
        }

        return problemListString.toString();
    }

    private static void reportPackageProblemDetails(EnumSet<Shipment.PackageProblems> packageProblems) {
        //Detailed error messages for the designated modality
        if (packageProblems.contains(Shipment.PackageProblems.GOODS_RESTRICTED_FOR_MODALITY)) {
            System.out.println("\tThe package cannot be carried via the designated method as it is TOO dangerous.");
        }

        if (packageProblems.contains(Shipment.PackageProblems.TOO_HEAVY_FOR_MODALITY)){
            System.out.println("\tThe package cannot be carried by the designated method as it is TOO heavy.");
        }

        if (packageProblems.contains(Shipment.PackageProblems.TOO_LARGE_FOR_MODALITY)){
           System.out.println("\tThe package is too large to be carried by the designated method.");
        }

        //Detailed Error Messages related to shipment standard
        if (packageProblems.contains(Shipment.PackageProblems.TOO_HEAVY_FOR_STANDARD)){
            System.out.println("\tThe package is too heavy to be carried at the given shipment standard.");
        }

        if (packageProblems.contains(Shipment.PackageProblems.TOO_LARGE_FOR_STANDARD)){
            System.out.println("\tThe package is too large to be carried at the given shipment standard.");
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
        assignShipmentTrackingNumber(ship);
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
        OptionMenu originMenu = new OptionMenu();
        String originMenuChoice;
        originMenu.addMenuOption("1", "Montreal")
                .addMenuOption("2", "Toronto")
                .addMenuOption("3", "Vancouver");
        boolean validChoice;
        do {
            System.out.print("\nPlease select the origin of the shipment:");
            System.out.printf("\n%s", originMenu.menuAsString(true));
            originMenuChoice = originMenu.promptForChoice();
            validChoice = originMenu.isValidOption(originMenuChoice);
        }
        while (!validChoice);

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
        OptionMenu destinationMenu = new OptionMenu();
        String destinationMenuChoice;
        destinationMenu.addMenuOption("1", "Montreal")
                .addMenuOption("2", "Toronto")
                .addMenuOption("3", "Vancouver");
        boolean validChoice;
        do {
            System.out.print("\nPlease select the destination for the shipment:");
            System.out.printf("\n%s", destinationMenu.menuAsString(true));
            destinationMenuChoice = destinationMenu.promptForChoice();
            validChoice = destinationMenu.isValidOption(destinationMenuChoice);
        }
        while (!validChoice);
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
        OptionMenu transportationMenu = new OptionMenu();
        String transportationMenuChoice;
        transportationMenu.addMenuOption("1", "Truck")
                .addMenuOption("2", "Rail")
                .addMenuOption("3", "Sea")
                .addMenuOption("4", "Air");
        boolean validChoice;
        do {
            System.out.print("\nPlease select the method of transportation:");
            System.out.printf("\n%s", transportationMenu.menuAsString(true));
            transportationMenuChoice = transportationMenu.promptForChoice();
            validChoice = transportationMenu.isValidOption(transportationMenuChoice);
        }
        while (!validChoice);
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
        OptionMenu priorityMenu = new OptionMenu();
        String priorityMenuChoice;
        priorityMenu.addMenuOption("1", "Standard")
                .addMenuOption("2", "Express");
        boolean validChoice;
        do {
            System.out.print("\nPlease select a priority for the shipment:");
            System.out.printf("\n%s", priorityMenu.menuAsString(true));
            priorityMenuChoice = priorityMenu.promptForChoice();
            validChoice = priorityMenu.isValidOption(priorityMenuChoice);
        }
        while (!validChoice);
        switch (priorityMenuChoice) {
            case "1":
                ship.setShippingSpeed(DeliveryStandard.STANDARD);
                break;
            case "2":
                ship.setShippingSpeed(DeliveryStandard.EXPRESS);
                break;
        }
    }

    public static void assignShipmentTrackingNumber(Shipment ship) {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear(), month = currentDate.getMonthValue(), day = currentDate.getDayOfMonth(),
                originCode = ship.getOrigin().getCityCode(), destinationCode = ship.getDestination().getCityCode();

        // Create a random number generator
        Random random = new Random();
        int randomNumber = random.nextInt(99) + 1; // Generate a random number between 1 and 99

        // Format the tracking number as: TRK+YYYYMMDD+originCode+destinationCode+random
        String trackingNumber = String.format("TRK%04d%02d%02d%02d%02d%02d",
                year, month, day, originCode, destinationCode, randomNumber);
        ship.setShipmentTrackingNumber(trackingNumber);
    }

    public static void displayShipmentSummary(Shipment ship) {
        evaluatePackageConflicts(ship);
        // Display shipment summary
        System.out.println("\n--- Shipment Summary ---");
        System.out.println("Shipment Label: " + ship.getName());
        System.out.println("Origin: " + ship.getOrigin());
        System.out.println("Destination: " + ship.getDestination());
        System.out.println("Shipping Method: " + ship.getShippingMethod());
        System.out.println("Shipping Speed: " + ship.getShippingSpeed());
        System.out.println("Tracking number: " + ship.getShipmentTrackingNumber());

        // Display package information
        System.out.println("\n--- Packages ---");
        for (Package pkg : ship.getPackages()) {
            System.out.println(createPackageSummary(pkg));
        }
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
            System.out.printf("[Package #%s]\n",packageIndex);
            System.out.println(createPackageSummary(pkg));
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

    // Method to handle modifying the shipment
    public static void modifyShipment(Shipment shipment) {
        // Call method to display the configured shipment
        displayShipmentSummary(shipment);

        String modifyMenuChoice;
        OptionMenu modifyMenu = new OptionMenu();
        String[][] modifyMenuOptions = {{"1", "Yes"}, {"2", "No"}};
        modifyMenu.addAllMenuOptions(modifyMenuOptions);

        boolean willModifyShipment = true;
        while (willModifyShipment) {
            System.out.println("Would you like to modify the shipment?");
            System.out.printf("\n%s\n", modifyMenu.withTitle("Modify Shipment").menuAsString());
            modifyMenuChoice = modifyMenu.promptForChoice();

            willModifyShipment = modifyMenuChoice.equals("1");
            if (willModifyShipment) {
                modifyShipmentDetails(shipment);
            }
        }
        System.out.println("No modifications requested.");

        finalizeShipment(shipment);
    }

    // Method to handle modifying specific shipment details
    private static void modifyShipmentDetails(Shipment shipment) {
        System.out.println("Which part of the shipment would you like to modify?");
        OptionMenu shipModifyMenu = new OptionMenu();
        String[][] shipModifyMenuOptions = {{"1", "Label"}, {"2", "Packages"}, {"3", "Origin"},
                {"4", "Destination"}, {"5", "Priority"},{"6","Method"}};
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
            case "6":
                assignShipmentModality(shipment);
                break;
            default:
                System.out.println("Invalid option selected.");
                break;
        }

        // Re-display the shipment summary after modification
        displayShipmentSummary(shipment);
    }

    public static void finalizeShipment(Shipment shipment) {
        boolean didValidate = validateShipment(shipment);

        if (didValidate) {
        displayShipmentSummary(shipment);
        displayShipmentCost(shipment);
        System.out.println();
        } else {
            modifyShipment(shipment);
        }
    }

    //Method to print the summary and costs to a file
    public static void exportShipmentDetails(Shipment ship) {
        // File path location
        String filePath = "Manifest.txt";

        // Create a StringBuilder to build the content
        StringBuilder sbTemp = new StringBuilder();

        // Append ship summary to the StringBuilder
        sbTemp.append("--- Shipment Summary ---\n");
        sbTemp.append("Shipment Label: ").append(ship.getName()).append("\n");
        sbTemp.append("Origin: ").append(ship.getOrigin()).append("\n");
        sbTemp.append("Destination: ").append(ship.getDestination()).append("\n");
        sbTemp.append("Shipping Method: ").append(ship.getShippingMethod()).append("\n");
        sbTemp.append("Shipping Speed: ").append(ship.getShippingSpeed()).append("\n");
        sbTemp.append("Tracking number: ").append(ship.getShipmentTrackingNumber()).append("\n");

        // Append package information to the StringBuilder
        sbTemp.append("\n--- Packages ---\n");
        for (Package pkg : ship.getPackages()) {
            sbTemp.append(createPackageSummary(pkg));
        }

        // Append ship cost to the StringBuilder
        sbTemp.append("\n--- Shipment Cost ---\n");
        sbTemp.append("Base Shipping Rate: ").append(ship.getBaseRate()).append("\n");
        sbTemp.append("Shipment Weight Surcharge: ").append(ship.getWeightSurcharge()).append("\n");
        sbTemp.append("Shipment Method Surcharge: ").append(ship.getModalitySurcharge()).append("\n");
        sbTemp.append("Shipment Priority Surcharge: ").append(ship.getPrioritySurcharge()).append("\n");
        sbTemp.append("-------------------\n");
        sbTemp.append("Total cost for ship: ").append(ship.calculateShippingCost()).append("\n");

        // Save the string builder as a string
        String shipmentDetails = sbTemp.toString();

        // Pass the string to the writeManifest method to save the file and return a message if successful
        boolean isSaved = FileUtils.writeManifest(shipmentDetails, filePath);
        if (isSaved) {
            System.out.printf("Your shipment was saved successfully to %s.%n", filePath);
        } else {
            System.out.println("There was an error saving the shipment details.");
        }

    }

    public static String createPackageSummary(Package pkg) {
        StringBuilder sbTemp = new StringBuilder();
        String DIVIDER = "-------------------";
        sbTemp.append("Package Label: ").append(pkg.getLabel()).append("\n");
        sbTemp.append("Goods Category: ").append(pkg.getGoodsClassification()).append("\n");
        sbTemp.append("Dimensions (LxWxH): ").append(pkg.getLength()).append(" x ")
                .append(pkg.getWidth()).append(" x ").append(pkg.getHeight()).append(" cm\n");
        sbTemp.append("Weight: ").append(pkg.getWeight()).append(" kg\n");
        if (packageProblemNotes.containsKey(pkg)){
            sbTemp.append("\033[33m"+packageProblemNotes.get(pkg)+"\033[0m");
        }

        sbTemp.append(DIVIDER);

        return sbTemp.toString();
    }
}
