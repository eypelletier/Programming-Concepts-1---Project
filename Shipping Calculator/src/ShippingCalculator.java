import helpers.OptionMenu;

import javax.swing.*;
import java.util.Scanner;

public class ShippingCalculator {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to SuperShipper - Steam Early Release - pre-v0.1\n" +
                "We will assist with calculating the cost of your shipment.");

        OptionMenu startMenu = new OptionMenu();
        startMenu.addMenuOption("1", "Create a new shipment")
                .addMenuOption("2", "Quit");
        System.out.printf("/---/\n%s\n",startMenu.menuAsString(true));
        String menuChoice = input.nextLine();
        startMenu.isValidOption(menuChoice);

        Shipment ship = new Shipment();
        System.out.print("Please enter a label for the shipment: ");
        String shipLabel = input.nextLine();
        ship.setName(shipLabel);

        System.out.print("How many packages would you like to ship: ");
        int numPackages = input.nextInt();
        input.nextLine();

        for (int i = 0; i < numPackages; i++) {
            Package pkg = new Package();

            System.out.println("Package" + (i + 1) + ": ");
            System.out.print("Enter package label:");
            String pkgLabel = input.nextLine();
            pkg.setLabel(pkgLabel);

            System.out.print("Enter package dimensions (Height x Width x Length in cm and Weight in kg):");
            double pkgHeight = input.nextDouble();
            double pkgWidth = input.nextDouble();
            double pkgLength = input.nextDouble();
            double pkgWeight = input.nextDouble();
            input.nextLine();
            pkg.setHeight(pkgHeight);
            pkg.setLength(pkgLength);
            pkg.setWidth(pkgWidth);
            pkg.setWeight(pkgWeight);

            //Create menu for type of goods
            GoodsCategory goodsCategory = null;
            OptionMenu goodsTypeMenu = new OptionMenu();
            goodsTypeMenu.addMenuOption("1", "Regular")
                    .addMenuOption("2", "Fragile")
                    .addMenuOption("3", "Hazardous")
                    .addMenuOption("4", "Explosive");
            System.out.printf("/---/\n%s\n",goodsTypeMenu.menuAsString(true));
            String pkgGoodsClassification = input.nextLine();
            goodsTypeMenu.isValidOption(pkgGoodsClassification);
            switch (pkgGoodsClassification){
                case "1":
                    goodsCategory = GoodsCategory.REGULAR;
                    break;
                case "2":
                    goodsCategory = GoodsCategory.FRAGILE;
                    break;
                case "3":
                    goodsCategory = GoodsCategory.HAZARDOUS;
                    break;
                case "4":
                    goodsCategory = GoodsCategory.EXPLOSIVE;
                    break;
            }
            pkg.setGoodsClassification(goodsCategory);

            ship.addToPackages(pkg);
        }

        System.out.println("Please select the origin of the shipment:");
        OptionMenu originMenu = new OptionMenu();
        originMenu.addMenuOption("1", "Montreal")
                .addMenuOption("2", "Toronto")
                .addMenuOption("3", "Vancouver");
        System.out.printf("/---/\n%s\n",originMenu.menuAsString(true));
        String originMenuChoice = input.nextLine();
        originMenu.isValidOption(originMenuChoice);
        switch (originMenuChoice){
            case "1":
                ship.setOrigin("Montreal");
                break;
            case "2":
                ship.setOrigin("Toronto");
                break;
            case "3":
                ship.setOrigin("Vancouver");
                break;
        }

        System.out.println("Please select the destination for the shipment:");
        OptionMenu destinationMenu = new OptionMenu();
        destinationMenu.addMenuOption("1", "Montreal")
                .addMenuOption("2", "Toronto")
                .addMenuOption("3", "Vancouver");
        System.out.printf("/---/\n%s\n",destinationMenu.menuAsString(true));
        String destinationMenuChoice = input.nextLine();
        destinationMenu.isValidOption(destinationMenuChoice);
        switch (destinationMenuChoice){
            case "1":
                ship.setDestination("Montreal");
                break;
            case "2":
                ship.setDestination("Toronto");
                break;
            case "3":
                ship.setDestination("Vancouver");
                break;
        }

        System.out.println("Please select the method of transportation:");
        DeliveryModality transportMethod = null;
        OptionMenu transportationMenu = new OptionMenu();
        transportationMenu.addMenuOption("1", "Truck")
                .addMenuOption("2", "Rail")
                .addMenuOption("3", "Sea")
                .addMenuOption("4", "Air");
        System.out.printf("/---/\n%s\n",transportationMenu.menuAsString(true));
        String transportationMenuChoice = input.nextLine();
        transportationMenu.isValidOption(transportationMenuChoice);
        switch (transportationMenuChoice){
            case "1":
                transportMethod = DeliveryModality.TRUCK;
                break;
            case "2":
                transportMethod = DeliveryModality.RAIL;
                break;
            case "3":
                transportMethod = DeliveryModality.SEA;
                break;
            case "4":
                transportMethod = DeliveryModality.AIR;
                break;
        }
        ship.setShippingMethod(transportMethod);


        System.out.println("Please select a priority for the shipment:");
        DeliveryStandard priority = null;
        OptionMenu priorityMenu = new OptionMenu();
        priorityMenu.addMenuOption("1", "Standard")
                .addMenuOption("2", "Express");
        System.out.printf("/---/\n%s\n",priorityMenu.menuAsString(true));
        String priorityMenuChoice = input.nextLine();
        priorityMenu.isValidOption(priorityMenuChoice);
        switch (priorityMenuChoice){
            case "1":
                priority = DeliveryStandard.STANDARD;
                break;
            case "2":
                priority = DeliveryStandard.EXPRESS;
                break;
        }
        ship.setShippingSpeed(priority);
    }
}
